import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_GA {

    // �s�s�̐�
    private static final int NUM_CITIES = 29;
    // �̐�
    private static final int POPULATION_SIZE = 20;
    // ���񐔂̌�����ۏ؂����
    private static final int CROSSOVER_GUARANTEED_COUNT = 500;
    // �ˑR�ψٗ�
    private static final double MUTATION_RATE = 0.1;
    // ���㐔
    private static final int NUM_GENERATIONS = 5;

    private static ArrayList<City> readCities(String filePath) {
        ArrayList<City> cities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split("\\s+");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                cities.add(new City(index++,x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    private static void writeFitnessValuesToCSV(String fileName, Population population) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // CSV�w�b�_�[����������
            writer.append("Generation,Fitness\n");

            // �e����̓K���x����������
            for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
                population.evaluate();
                Individual bestIndividual = population.getBestIndividual();
                double fitness = bestIndividual.getFitness();

                writer.append(generation + "," + fitness + "\n");
            }

            System.out.println("CSV�t�@�C���ɓK���x���o�͂���܂���: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Individual crossover(Individual parent1, Individual parent2) {
        // 1�_����
        Random random = new Random();
        int crossoverPoint = random.nextInt(NUM_CITIES);
    
        ArrayList<City> childCities = new ArrayList<>(Collections.nCopies(NUM_CITIES, null));
    
        // �e1�̃c�A�[�̈ꕔ���q���ɃR�s�[
        for (int i = 0; i < crossoverPoint; i++) {
            childCities.set(i, parent1.getCity(i));
        }
    
        // �e2�̃c�A�[����܂��܂܂�Ă��Ȃ��s�s���q���ɃR�s�[
        for (int i = 0; i < NUM_CITIES; i++) {
            City currentCity = parent2.getCity(i);
    
            if (!childCities.contains(currentCity)) {
                for (int j = 0; j < NUM_CITIES; j++) {
                    if (childCities.get(j) == null) {
                        childCities.set(j, currentCity);
                        break;
                    }
                }
            }
        }
    
        return new Individual(childCities);
    }
    
    public static Individual mutate(Individual individual) {
        // 2�̓s�s�������_���ɑI�����Ĉʒu�����ւ���
        Random random = new Random();
        int index1 = random.nextInt(NUM_CITIES);
        int index2 = random.nextInt(NUM_CITIES);

        ArrayList<City> mutatedCities = new ArrayList<>(individual.getCities());
        Collections.swap(mutatedCities, index1, index2);

        return new Individual(mutatedCities);
    }

    public static void main(String[] args){

        // �V�����s�s�f�[�^�̓ǂݍ���
        ArrayList<City> cities = readCities("WesternSaharaPlot.txt");

        // ... (�����̃R�[�h)
        // for (City city : cities) {
        //     System.out.println(city);
        // }

        // �����̌Q�𐶐����A�V�����s�s�f�[�^���g�p���Đi�����J�n
        Population population = new Population(POPULATION_SIZE, cities);
        // �V�����̌Q�𐶐�
            Population newPopulation = new Population();
        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            population.calculateFitness();
            // �K���x�Ɋ�Â��Č̌Q��]��
            population.evaluate();
    
            // �G���[�g�̂�I��
            // Individual elite = population.getElite();
            population.applyElitism();
    
            // �\��
            // System.out.println("���� " + generation + ": �œK�Ȍo�H - " + elite.getTour() + ", �K���x - " + elite.getFitness());
    
            
    
            
            System.out.println("��" + newPopulation);
            int crossoverCount = 0;
            // ������K�p���ĐV�����̌Q�𐶐�
            while (newPopulation.getSize() < POPULATION_SIZE) {
                if (crossoverCount < CROSSOVER_GUARANTEED_COUNT) {
                    // ������K�p
                    Individual parent1 = population.selectParent();
                    Individual parent2 = population.selectParent();
                    Individual child = crossover(parent1, parent2);
                    newPopulation.addIndividual(child);
                    crossoverCount++;
                } else {
                    // �����񐔂����񐔂𒴂����玟�̐���ɐi��
                    break;
                }
                newPopulation.calculateFitness();
            }

            // �ˑR�ψق�K�p
            for (int i = 0; i < newPopulation.getSize(); i++) {
                if (Math.random() < MUTATION_RATE) {
                    Individual mutatedChild = mutate(newPopulation.getIndividual(i));
                    newPopulation.addIndividual(mutatedChild);
                }
            }
            
            // �G���[�g��V�����̌Q�ɒǉ�
            // newPopulation.addIndividual(elite);
            // �V�����̌Q���X�V
            // population = newPopulation;
            // System.out.println("�̂̓��e" + population);
            // // �K���x�Ɋ�Â��Č̌Q��]��
            // population.evaluate();
        }
    
        // �ŏI�I�ȍœK�Ȍo�H���o��
        Individual bestIndividual = population.getBestIndividual();
        System.out.println("�œK�Ȍo�H: " + bestIndividual.getTour() + ", �K���x: " + bestIndividual.getFitness());

        // CSV�t�@�C���ɓK���x���o��
        writeFitnessValuesToCSV("fitness_values.csv", population);
    }
}

class City{
    private int index;
    private double x;
    private double y;

    public City(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    // 2�̓s�s�Ԃ̋������v�Z
    public double distanceTo(City otherCity) {
        if (this.x == 0 || otherCity.x == 0) {
            throw new IllegalStateException("City coordinates cannot be zero.");
        }
        double dx = this.x - otherCity.x;
        double dy = this.y - otherCity.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // @Override
    // public String toString() {
    //     return "City{" +
    //             "index=" + index 
    //             // +
    //             // ", x=" + x +
    //             // ", y=" + y +
    //             // '}'
    //             ;
    // }

}

class Individual {
    private ArrayList<City> tour;
    private double fitness;

    // �R���X�g���N�^�⑼�̃��\�b�h�͏ȗ�

    public Individual(ArrayList<City> tour) {
        this.tour = new ArrayList<>();
        for (City city : tour) {
            // �s�s��null�łȂ����Ƃ��m�F���Ă���ǉ�
            if (city != null) {
                this.tour.add(new City(city.getIndex(), city.getX(), city.getY()));
            }
        }
        calculateFitness();
    }

    public ArrayList<City> getCities() {
        return tour;
    }

    public ArrayList<City> getTour() {
        return tour;
    }

    public double getFitness() {
        return fitness;
    }

    public City getCity(int index) {
        return tour.get(index);
    }

    public void generateIndividual() {
        Collections.shuffle(tour);
        calculateFitness();
    }

    public void calculateFitness() {
        double totalDistance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            City city1 = tour.get(i);
            City city2 = tour.get(i + 1);
            if (city1 != null && city2 != null) {
                totalDistance += city1.distanceTo(city2);
            } else {
                System.out.println("Null city found in the tour at index: " + i);
                throw new IllegalStateException("City in the tour is null.");
            }
        }
        City lastCity = tour.get(tour.size() - 1);
        City firstCity = tour.get(0);
        if (lastCity != null && firstCity != null) {
            totalDistance += lastCity.distanceTo(firstCity); // �Ō�̓s�s����n�_�ւ̋���
        } else {
            System.out.println("Null city found at the beginning or end of the tour.");
            throw new IllegalStateException("City in the tour is null.");
        }
        fitness = totalDistance;
    }

    // @Override
    // public String toString() {
    //     StringBuilder sb = new StringBuilder("Individual{tour=[");
    //     for (City city : tour) {
    //         sb.append(city).append(", ");
    //     }
    //     sb.delete(sb.length() - 2, sb.length()); // �����̕s�v�ȃR���}�ƃX�y�[�X���폜
    //     sb.append("], fitness=").append(fitness).append('}');
    //     return sb.toString();
    // }
}

class Population {
    private ArrayList<Individual> individuals;

    public Population() {
        this.individuals = new ArrayList<>();
    }

    public Population(int populationSize, ArrayList<City> cities) {
        this.individuals = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            ArrayList<City> shuffledCities = new ArrayList<>(cities);
            Collections.shuffle(shuffledCities);
            Individual individual = new Individual(shuffledCities);
            individuals.add(individual);
        }
    }

    public int getSize() {
        return individuals.size();
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    // �K���x�Ɋ�Â��Č̌Q��]��
    public void evaluate() {
        for (Individual individual : individuals) {
            individual.calculateFitness();
        }
        individuals.sort((i1, i2) -> Double.compare(i2.getFitness(), i1.getFitness()));
    }

    // �G���[�g�̂��擾
    public Individual getElite() {
        return individuals.get(0);
    }

    // �����_���Ȍ̂��擾
    public Individual getRandomIndividual() {
        Random random = new Random();
        int index = random.nextInt(getSize());
        return getIndividual(index);
    }

    // �̂�ǉ�
    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    // �ŗǂ̌̂��擾
    public Individual getBestIndividual() {
        return individuals.get(0);
    }

    // �e��I��
    public Individual selectParent() {
        // ���[���b�g�I�����g�p
        double totalFitness = individuals.stream().mapToDouble(Individual::getFitness).sum();
        double target = Math.random() * totalFitness;
        double currentSum = 0;

        for (Individual individual : individuals) {
            currentSum += individual.getFitness();
            if (currentSum >= target) {
                return individual;
            }
        }

        // �ʏ�͂����ɓ��B���Ȃ��͂�
        return individuals.get(individuals.size() - 1);
    }

    public void applyElitism() {
        // �G���[�g�헪�̓K�p
        int eliteCount = (int) (20 * 0.1);
        ArrayList<Individual> elites = new ArrayList<>(individuals.subList(0, eliteCount));

        // �V�����̌Q�ɃG���[�g�̂�ǉ�
        for (Individual elite : elites) {
            individuals.add(new Individual(elite.getCities()));
        }
    }

    public Population calculateFitness() {
        for (Individual individual : individuals) {
            individual.calculateFitness();
        }
        return this;
    }
}