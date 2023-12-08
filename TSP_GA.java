import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_GA {

    // 都市の数
    private static final int NUM_CITIES = 29;
    // 個体数
    private static final int POPULATION_SIZE = 20;
    // 一定回数の交叉を保証する回数
    private static final int CROSSOVER_GUARANTEED_COUNT = 500;
    // 突然変異率
    private static final double MUTATION_RATE = 0.1;
    // 世代数
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
            // CSVヘッダーを書き込む
            writer.append("Generation,Fitness\n");

            // 各世代の適応度を書き込む
            for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
                population.evaluate();
                Individual bestIndividual = population.getBestIndividual();
                double fitness = bestIndividual.getFitness();

                writer.append(generation + "," + fitness + "\n");
            }

            System.out.println("CSVファイルに適応度が出力されました: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Individual crossover(Individual parent1, Individual parent2) {
        // 1点交叉
        Random random = new Random();
        int crossoverPoint = random.nextInt(NUM_CITIES);
    
        ArrayList<City> childCities = new ArrayList<>(Collections.nCopies(NUM_CITIES, null));
    
        // 親1のツアーの一部を子供にコピー
        for (int i = 0; i < crossoverPoint; i++) {
            childCities.set(i, parent1.getCity(i));
        }
    
        // 親2のツアーからまだ含まれていない都市を子供にコピー
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
        // 新しい個体群を生成
            Population newPopulation = new Population();
        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            population.calculateFitness();
            // 適応度に基づいて個体群を評価
            population.evaluate();
    
            // エリート個体を選択
            // Individual elite = population.getElite();
            population.applyElitism();
    
            // 表示
            // System.out.println("世代 " + generation + ": 最適な経路 - " + elite.getTour() + ", 適応度 - " + elite.getFitness());
    
            
    
            
            System.out.println("個体" + newPopulation);
            int crossoverCount = 0;
            // 交叉を適用して新しい個体群を生成
            while (newPopulation.getSize() < POPULATION_SIZE) {
                if (crossoverCount < CROSSOVER_GUARANTEED_COUNT) {
                    // 交叉を適用
                    Individual parent1 = population.selectParent();
                    Individual parent2 = population.selectParent();
                    Individual child = crossover(parent1, parent2);
                    newPopulation.addIndividual(child);
                    crossoverCount++;
                } else {
                    // 交叉回数が一定回数を超えたら次の世代に進む
                    break;
                }
                newPopulation.calculateFitness();
            }

            // 突然変異を適用
            for (int i = 0; i < newPopulation.getSize(); i++) {
                if (Math.random() < MUTATION_RATE) {
                    Individual mutatedChild = mutate(newPopulation.getIndividual(i));
                    newPopulation.addIndividual(mutatedChild);
                }
            }
            
            // エリートを新しい個体群に追加
            // newPopulation.addIndividual(elite);
            // 新しい個体群を更新
            // population = newPopulation;
            // System.out.println("個体の内容" + population);
            // // 適応度に基づいて個体群を評価
            // population.evaluate();
        }
    
        // 最終的な最適な経路を出力
        Individual bestIndividual = population.getBestIndividual();
        System.out.println("最適な経路: " + bestIndividual.getTour() + ", 適応度: " + bestIndividual.getFitness());

        // CSVファイルに適応度を出力
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

    // 2つの都市間の距離を計算
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

    // コンストラクタや他のメソッドは省略

    public Individual(ArrayList<City> tour) {
        this.tour = new ArrayList<>();
        for (City city : tour) {
            // 都市がnullでないことを確認してから追加
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
            totalDistance += lastCity.distanceTo(firstCity); // 最後の都市から始点への距離
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

    public void applyElitism() {
        // エリート戦略の適用
        int eliteCount = (int) (20 * 0.1);
        ArrayList<Individual> elites = new ArrayList<>(individuals.subList(0, eliteCount));

        // 新しい個体群にエリート個体を追加
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