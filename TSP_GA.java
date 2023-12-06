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
        // PMXを使用した交叉
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
        // 2つの都市をランダムに選択して位置を入れ替える
        Random random = new Random();
        int index1 = random.nextInt(NUM_CITIES);
        int index2 = random.nextInt(NUM_CITIES);

        ArrayList<City> mutatedCities = new ArrayList<>(individual.getCities());
        Collections.swap(mutatedCities, index1, index2);

        return new Individual(mutatedCities);
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

        // // 生成した初期個体群の適応度を表示
        // System.out.println("初期個体群の適応度:");
        // for (int i = 0; i < population.getSize(); i++) {
        //     Individual individual = population.getIndividual(i);
        //     System.out.println("個体 " + (i + 1) + " の適応度: " + individual.getFitness());
        // }

        // // 生成した初期個体群の適応度を表示
        // System.out.println("初期個体群の巡回路:");
        // for (int i = 0; i < population.getSize(); i++) {
        //     Individual individual = population.getIndividual(i);
        //     System.out.println("個体 " + (i + 1) + " の巡回路: " + individual.getTour());
        // }

        // System.out.println("初期個体群:");
        // for (int j = 0; j < population.getSize(); j++) {
        //     System.out.println("個体 " + (j + 1) + ": " + population.getIndividual(j));
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

    // 2つの都市間の距離を計算
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

    // コンストラクタや他のメソッドは省略

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
        totalDistance += tour.get(tour.size() - 1).distanceTo(tour.get(0)); // 最後の都市から始点への距離
        fitness = totalDistance;
    }

    // @Override
    // public String toString() {
    //     StringBuilder sb = new StringBuilder("Individual{tour=[");
    //     for (City city : tour) {
    //         sb.append(city).append(", ");
    //     }
    //     sb.delete(sb.length() - 2, sb.length()); // 末尾の不要なコンマとスペースを削除
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