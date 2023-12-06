import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_GA {

    // �s�s�̐�
    private static final int NUM_CITIES = 29;
    // �̐�
    private static final int POPULATION_SIZE = 20;
    // ������
    private static final double CROSSOVER_RATE = 0.8;
    // �ˑR�ψٗ�
    private static final double MUTATION_RATE = 0.02;
    // ���㐔
    private static final int NUM_GENERATIONS = 1000;

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

    private static Individual crossover(Individual parent1, Individual parent2) {
        // PMX���g�p��������
        Random random = new Random();
        int startPos = random.nextInt(NUM_CITIES);
        int endPos = random.nextInt(NUM_CITIES - startPos) + startPos;

        ArrayList<City> childCities = new ArrayList<>(Collections.nCopies(NUM_CITIES, null));
        for (int i = startPos; i <= endPos; i++) {
            childCities.set(i, parent1.getCity(i));
        }

        for (int i = 0; i < NUM_CITIES; i++) {
            if (i < startPos || i > endPos) {
                City currentCity = parent2.getCity(i);
                int currentIndex = parent1.getCities().indexOf(currentCity);


                while (childCities.contains(currentCity)) {
                    currentIndex = parent1.getCities().indexOf(currentCity);
                    currentCity = parent2.getCity(currentIndex);
                }
                

                childCities.set(currentIndex, currentCity);
            }
        }

        return new Individual(childCities);
    }

    private static Individual mutate(Individual individual) {
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

        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            // �K���x�Ɋ�Â��Č̌Q��]��
            population.evaluate();
        
            // �G���[�g�̂�I��
            Individual elite = population.getElite();
        
            // �V�����̌Q�𐶐�
            Population newPopulation = new Population();
        
            // �G���[�g��V�����̌Q�ɒǉ�
            newPopulation.addIndividual(elite);
        
            // �����ƓˑR�ψق�K�p���ĐV�����̌Q�𐶐�
            while (newPopulation.getSize() < POPULATION_SIZE) {
                // ������K�p
                if (Math.random() < CROSSOVER_RATE) {
                    Individual parent1 = population.selectParent();
                    Individual parent2 = population.selectParent();
                    Individual child = crossover(parent1, parent2);
                    newPopulation.addIndividual(child);
                } else {
                    // ������K�p���Ȃ��ꍇ�̓����_���ɑI�����ēˑR�ψق�K�p
                    Individual randomIndividual = population.getRandomIndividual();
                    Individual mutatedChild = mutate(randomIndividual);
                    newPopulation.addIndividual(mutatedChild);
                }
            }
        
            // �V�����̌Q���X�V
            population = newPopulation;
        }
        

        // �ŏI�I�ȍœK�Ȍo�H���o��
        Individual bestIndividual = population.getBestIndividual();
        System.out.println("�œK�Ȍo�H: " + bestIndividual);

        // // �������������̌Q�̓K���x��\��
        // System.out.println("�����̌Q�̓K���x:");
        // for (int i = 0; i < population.getSize(); i++) {
        //     Individual individual = population.getIndividual(i);
        //     System.out.println("�� " + (i + 1) + " �̓K���x: " + individual.getFitness());
        // }

        // // �������������̌Q�̓K���x��\��
        // System.out.println("�����̌Q�̏���H:");
        // for (int i = 0; i < population.getSize(); i++) {
        //     Individual individual = population.getIndividual(i);
        //     System.out.println("�� " + (i + 1) + " �̏���H: " + individual.getTour());
        // }

        // System.out.println("�����̌Q:");
        // for (int j = 0; j < population.getSize(); j++) {
        //     System.out.println("�� " + (j + 1) + ": " + population.getIndividual(j));
        // }
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

    // 2�̓s�s�Ԃ̋������v�Z
    public double distanceTo(City otherCity) {
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

    // public static City createCity(String coordinates) {
    //     String[] coords = coordinates.split("\\s+");
    //     double x = Double.parseDouble(coords[0]);
    //     double y = Double.parseDouble(coords[1]);
    //     return new City(x, y);
    // }
}

class Individual {
    private ArrayList<City> tour;
    private double fitness;

    // �R���X�g���N�^�⑼�̃��\�b�h�͏ȗ�

    public Individual(ArrayList<City> tour) {
        this.tour = new ArrayList<>(tour);
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
            totalDistance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        totalDistance += tour.get(tour.size() - 1).distanceTo(tour.get(0)); // �Ō�̓s�s����n�_�ւ̋���
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
}