import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSPGeneticAlgorithm {

    // 都市の数
    private static final int NUM_CITIES = 10;
    // 個体数
    private static final int POPULATION_SIZE = 50;
    // 交叉率
    private static final double CROSSOVER_RATE = 0.8;
    // 突然変異率
    private static final double MUTATION_RATE = 0.02;
    // 世代数
    private static final int NUM_GENERATIONS = 1000;

    public static void main(String[] args) {
        // 都市の座標を生成
        ArrayList<City> cities = generateCities();

        // 初期個体群を生成
        Population population = new Population(POPULATION_SIZE, cities);

        // 進化を繰り返す
        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            // 適応度に基づいて個体群を評価
            population.evaluate();

            // エリート個体を選択
            Individual elite = population.getElite();

            // 新しい個体群を生成
            Population newPopulation = new Population();

            // エリートを新しい個体群に追加
            newPopulation.addIndividual(elite);

            // 交叉と突然変異を適用して新しい個体群を生成
            while (newPopulation.getSize() < POPULATION_SIZE) {
                // 交叉を適用
                if (Math.random() < CROSSOVER_RATE) {
                    Individual parent1 = population.selectParent();
                    Individual parent2 = population.selectParent();
                    Individual child = crossover(parent1, parent2);
                    newPopulation.addIndividual(child);
                } else {
                    // 交叉を適用しない場合はランダムに選択して突然変異を適用
                    Individual randomIndividual = population.getRandomIndividual();
                    Individual mutatedChild = mutate(randomIndividual);
                    newPopulation.addIndividual(mutatedChild);
                }
            }

            // 新しい個体群を更新
            population = newPopulation;
        }

        // 最終的な最適な経路を出力
        Individual bestIndividual = population.getBestIndividual();
        System.out.println("最適な経路: " + bestIndividual);
    }

    // 都市の座標をランダムに生成
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

    // 交叉操作
    private static Individual crossover(Individual parent1, Individual parent2) {
        // 一様交叉を使用
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

    // 突然変異操作
    private static Individual mutate(Individual individual) {
        // 2つの都市をランダムに選択して位置を入れ替える
        Random random = new Random();
        int index1 = random.nextInt(NUM_CITIES);
        int index2 = random.nextInt(NUM_CITIES);

        ArrayList<City> mutatedCities = new ArrayList<>(individual.getCities());
        Collections.swap(mutatedCities, index1, index2);

        return new Individual(mutatedCities);
    }
}

// 都市クラス
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

    // 2つの都市間の距離を計算
    public double distanceTo(City otherCity) {
        int dx = this.x - otherCity.x;
        int dy = this.y - otherCity.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

// 個体クラス
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

    // 適応度を計算
    public void calculateFitness() {
        double totalDistance = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            totalDistance += cities.get(i).distanceTo(cities.get(i + 1));
        }
        totalDistance += cities.get(cities.size() - 1).distanceTo(cities.get(0)); // 最後の都市から始点への距離
        fitness = 1 / totalDistance; // 最小化問題なので距離の逆数を適応度とする
    }

    @Override
    public String toString() {
        return "Individual{" +
                "cities=" + cities +
                ", fitness=" + fitness +
                '}';
    }
}

// 個体群クラス(ノード情報ついでに交配もしてる)
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