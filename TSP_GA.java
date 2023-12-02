import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_GA {

    // 都市の数
    private static final int NUM_CITIES = 29;
    // 個体数
    private static final int POPULATION_SIZE = 20;
    // 交叉率
    private static final double CROSSOVER_RATE = 0.8;
    // 突然変異率
    private static final double MUTATION_RATE = 0.02;
    // 世代数
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

        // 新しい都市データの読み込み
        ArrayList<City> cities = readCities("WesternSaharaPlot.txt");

        // ... (既存のコード)
        // for (City city : cities) {
        //     System.out.println(city);
        // }

        // 初期個体群を生成し、新しい都市データを使用して進化を開始
        Population population = new Population(POPULATION_SIZE, cities);

        System.out.println("初期個体群:");
        for (int i = 0; i < population.getSize(); i++) {
            System.out.println("個体 " + (i + 1) + ": " + population.getIndividual(i));
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

    // 2つの都市間の距離を計算
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

    // コンストラクタや他のメソッドは省略

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
        totalDistance += tour.get(tour.size() - 1).distanceTo(tour.get(0)); // 最後の都市から始点への距離
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

    // 適応度に基づいて個体群を評価
    public void evaluate() {
        for (Individual individual : individuals) {
            individual.calculateFitness();
        }
        individuals.sort((i1, i2) -> Double.compare(i2.getFitness(), i1.getFitness()));
    }

    // エリート個体を取得
    public Individual getElite() {
        return individuals.get(0);
    }

    // ランダムな個体を取得
    public Individual getRandomIndividual() {
        Random random = new Random();
        int index = random.nextInt(getSize());
        return getIndividual(index);
    }

    // 個体を追加
    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    // 最良の個体を取得
    public Individual getBestIndividual() {
        return individuals.get(0);
    }

    // 親を選択
    public Individual selectParent() {
        // ルーレット選択を使用
        double totalFitness = individuals.stream().mapToDouble(Individual::getFitness).sum();
        double target = Math.random() * totalFitness;
        double currentSum = 0;

        for (Individual individual : individuals) {
            currentSum += individual.getFitness();
            if (currentSum >= target) {
                return individual;
            }
        }

        // 通常はここに到達しないはず
        return individuals.get(individuals.size() - 1);
    }
}