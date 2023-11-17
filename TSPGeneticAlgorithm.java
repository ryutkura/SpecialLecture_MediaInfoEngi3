import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSPGeneticAlgorithm {

    // �s�s�̐�
    private static final int NUM_CITIES = 10;
    // �̐�
    private static final int POPULATION_SIZE = 50;
    // ������
    private static final double CROSSOVER_RATE = 0.8;
    // �ˑR�ψٗ�
    private static final double MUTATION_RATE = 0.02;
    // ���㐔
    private static final int NUM_GENERATIONS = 1000;

    public static void main(String[] args) {
        // �s�s�̍��W�𐶐�
        ArrayList<City> cities = generateCities();

        // �����̌Q�𐶐�
        Population population = new Population(POPULATION_SIZE, cities);

        // �i�����J��Ԃ�
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
    }

    // �s�s�̍��W�������_���ɐ���
    private static ArrayList<City> generateCities() {
        ArrayList<City> cities = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < NUM_CITIES; i++) {
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            cities.add(new City(x, y));
        }

        return cities;
    }

    // ��������
    private static Individual crossover(Individual parent1, Individual parent2) {
        // ��l�������g�p
        Random random = new Random();
        ArrayList<City> childCities = new ArrayList<>(Collections.nCopies(NUM_CITIES, null));

        for (int i = 0; i < NUM_CITIES; i++) {
            if (random.nextBoolean()) {
                childCities.set(i, parent1.getCity(i));
            } else {
                childCities.set(i, parent2.getCity(i));
            }
        }

        return new Individual(childCities);
    }

    // �ˑR�ψّ���
    private static Individual mutate(Individual individual) {
        // 2�̓s�s�������_���ɑI�����Ĉʒu�����ւ���
        Random random = new Random();
        int index1 = random.nextInt(NUM_CITIES);
        int index2 = random.nextInt(NUM_CITIES);

        ArrayList<City> mutatedCities = new ArrayList<>(individual.getCities());
        Collections.swap(mutatedCities, index1, index2);

        return new Individual(mutatedCities);
    }
}

// �s�s�N���X
class City {
    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // 2�̓s�s�Ԃ̋������v�Z
    public double distanceTo(City otherCity) {
        int dx = this.x - otherCity.x;
        int dy = this.y - otherCity.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

// �̃N���X
class Individual {
    private ArrayList<City> cities;
    private double fitness;

    public Individual(ArrayList<City> cities) {
        this.cities = new ArrayList<>(cities);
        calculateFitness();
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public City getCity(int index) {
        return cities.get(index);
    }

    public double getFitness() {
        return fitness;
    }

    // �K���x���v�Z
    public void calculateFitness() {
        double totalDistance = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            totalDistance += cities.get(i).distanceTo(cities.get(i + 1));
        }
        totalDistance += cities.get(cities.size() - 1).distanceTo(cities.get(0)); // �Ō�̓s�s����n�_�ւ̋���
        fitness = 1 / totalDistance; // �ŏ������Ȃ̂ŋ����̋t����K���x�Ƃ���
    }

    @Override
    public String toString() {
        return "Individual{" +
                "cities=" + cities +
                ", fitness=" + fitness +
                '}';
    }
}

// �̌Q�N���X(�m�[�h�����łɌ�z�����Ă�)
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