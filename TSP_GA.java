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
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split("\\s+");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                cities.add(new City(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
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

        System.out.println("�����̌Q:");
        for (int i = 0; i < population.getSize(); i++) {
            System.out.println("�� " + (i + 1) + ": " + population.getIndividual(i));
        }
    }
}

class City{
    private double x;
    private double y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // public double getX() {
    //     return x;
    // }

    // public double getY() {
    //     return y;
    // }

    // 2�̓s�s�Ԃ̋������v�Z
    public double distanceTo(City otherCity) {
        double dx = this.x - otherCity.x;
        double dy = this.y - otherCity.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static City createCity(String coordinates) {
        String[] coords = coordinates.split("\\s+");
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        return new City(x, y);
    }
}

class Individual {
    private ArrayList<City> tour;
    private double fitness;

    // �R���X�g���N�^�⑼�̃��\�b�h�͏ȗ�

    public Individual(ArrayList<City> tour) {
        this.tour = new ArrayList<>(tour);
        calculateFitness();
    }

    public ArrayList<City> getTour() {
        return tour;
    }

    public double getFitness() {
        return fitness;
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