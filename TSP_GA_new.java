import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TSP_GA_new {

    // 都市の数
    private static final int NUM_CITIES = 29;
    // 個体数
    private static final int POPULATION_SIZE = 400;
    // 一定回数の交叉を保証する回数
    // private static final int CROSSOVER_GUARANTEED_COUNT = 500;
    // 突然変異率
    private static final double MUTATION_RATE = 0.5;
    // 世代数
    private static final int NUM_GENERATIONS = 100;

    // 個体を管理する配列
    static int[][] root;
    static int[][] order;
    static int[][] childOrder;
    static double[] x;
    static double[] y;
    static double[] fitness_value;

    // 座標データを読み込むメソッド
    private static void readCoordinatesFromFile(String filePath, double[] x, double[] y) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null && index < x.length) {
                String[] coordinates = line.split("\\s+");

                if (coordinates.length >= 2) {
                    x[index] = Double.parseDouble(coordinates[0]);
                    y[index] = Double.parseDouble(coordinates[1]);
                    index++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // 2点間の距離の計算を行うメソッド
    public static double calculateFitness(int p1,int p2,double[] x,double[] y){
        double ans = 0;
        double dx = x[p1] - x[p2];
        double dy = y[p1] - y[p2];
        ans = Math.sqrt(dx*dx + dy*dy);

        return ans;
    }

    // 初期個体の生成を行うメソッド
    public static int[][] InitRoot(int[][] root) {
        Random random = new Random();

        for(int i=0;i<POPULATION_SIZE;i++){
            for(int j=0;j<NUM_CITIES;j++){
                root[i][j] = j;
            }
            for(int j=0;j<NUM_CITIES;j++){
                int index = random.nextInt(j + 1);
                // 要素の入れ替え
                int temp = root[i][j];
                root[i][j] = root[i][index];
                root[i][index] = temp;
            }
        }
        return root;
    }
    // 個体の距離の算出を行うメソッド
    public static void fitness_value(int[][] root,double[] fitness_value){
        for(int i=0;i<POPULATION_SIZE;i++){
            double totalDistance = 0;
            for(int j=0;j<NUM_CITIES;j++){
                if(j+1 != NUM_CITIES){
                    totalDistance += calculateFitness(root[i][j], root[i][j + 1], x, y);
                }
            }
            totalDistance += calculateFitness(root[i][NUM_CITIES - 1], root[i][0], x, y);
            fitness_value[i] = totalDistance;
        }
    }
    // debug用の出力メソッド
    public static void printMatrix(int[][] matrix,double[] mat) {
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                System.out.print(" "+matrix[i][j]);
            }
            // System.out.println("");
            System.out.println("："+mat[i]);
        }
        System.out.println("---------------------------------");
    }
    // ↓最大値を求めるメソッド
    public static double findMax(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty or null");
        }

        return Arrays.stream(array)
                .max()
                .orElseThrow();
    }
    // ↓最小値を求めるメソッド
    public static double findMin(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty or null");
        }

        return Arrays.stream(array)
                .min()
                .orElseThrow();
    }
    // ↓平均値を求めるメソッド
    public static double findAverage(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty or null");
        }

        return Arrays.stream(array)
                .average()
                .orElseThrow();
    }
    // 最小値を出力するメソッド
    public static void printmin(double[] fitness_value){
        System.out.println("最小値は"+findMin(fitness_value)+"です。");
    }
    // 最大値を出力するメソッド
    public static void printmax(double[] fitness_value){
        System.out.println("最大値は"+findMax(fitness_value)+"です。");
    }
    // 平均値を出力するメソッド
    public static void printave(double[] fitness_value){
        System.out.println("平均値は"+findAverage(fitness_value)+"です。");
    }
    // 突然変異を行うメソッド
    public static void mutation(int[][] array) {
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Invalid array");
        }
        Random random = new Random();
        // ランダムな2つの位置を生成
        int index1 = random.nextInt(NUM_CITIES);
        int index2;
        int index3 = random.nextInt(POPULATION_SIZE);
        do {
            index2 = random.nextInt(NUM_CITIES);
        } while (index1 == index2);
        // 要素の入れ替え
        int temp = array[index3][index1];
        array[index3][index1] = array[index3][index2];
        array[index3][index2] = temp;
        System.out.println("今回の突然変異は個体"+index3+"の要素"+index1+"と要素"+index2+"で起こりました");
    }
    public static int[][] encodePopulation(int[][] population) {
        int[][] order = new int[POPULATION_SIZE][NUM_CITIES];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            order[i] = encode(population[i]);
        }
        return order;
    }

    public static int[] encode(int[] path) {
        int[] order = new int[path.length];
        List<Integer> cities = new ArrayList<>();
        for (int i = 0; i < path.length; i++) {
            cities.add(i);
        }

        for (int i = 0; i < path.length; i++) {
            int index = cities.indexOf(path[i]);
            order[i] = index + 1;
            cities.remove(index);
        }

        return order;
    }
    

    public static int[][] crossoverPopulation(int[][] population) {
        int[][] childOrder = new int[POPULATION_SIZE][NUM_CITIES];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            childOrder[i] = crossover(population[i], population[(i+1)%POPULATION_SIZE]);
        }
        return childOrder;
    }

    public static int[] crossover(int[] parent1, int[] parent2) {
        Random random = new Random();
        int crossPoint = random.nextInt(parent1.length);

        int[] childOrder = new int[parent1.length];
        System.arraycopy(parent1, 0, childOrder, 0, crossPoint);
        System.arraycopy(parent2, crossPoint, childOrder, crossPoint, parent1.length - crossPoint);

        return childOrder;
    }

    public static int[][] decodePopulation(int[][] population) {
        int[][] decodedPath = new int[POPULATION_SIZE][NUM_CITIES];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            decodedPath[i] = decode(population[i]);
        }
        return decodedPath;
    }

    public static int[] decode(int[] order) {
        int[] path = new int[order.length];
        List<Integer> cities = new ArrayList<>();
        for (int i = 0; i < order.length; i++) {
            cities.add(i);
        }

        for (int i = 0; i < order.length; i++) {
            int city = cities.remove(order[i] - 1);
            path[i] = city;
        }

        return path;
    }
    //エリート戦略を行うメソッド
    public static int[][] applyEliteStrategy(int[][] root, double[] fitness_value) {
        int eliteSize = (int) Math.round(POPULATION_SIZE * 0.1);

        // Create list of indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            indices.add(i);
        }

        // Sort indices based on fitness values
        indices.sort(Comparator.comparingDouble(i -> fitness_value[i]));

        // Get elite indices
        List<Integer> eliteIndices = indices.subList(0, eliteSize);

        // Save elite individuals
        int[][] eliteRoot = new int[eliteSize][NUM_CITIES];
        for (int i = 0; i < eliteSize; i++) {
            eliteRoot[i] = root[eliteIndices.get(i)].clone();
        }

        return eliteRoot;
    }

    public static int[][] replaceWithElite(int[][] root, int[][] eliteRoot, double[] fitness_value) {
        int eliteSize = eliteRoot.length;
        int nonEliteSize = POPULATION_SIZE - eliteSize;

        // Create list of indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            indices.add(i);
        }

        // Sort indices based on fitness values
        indices.sort(Comparator.comparingDouble(i -> fitness_value[i]));

        // Get non-elite indices
        List<Integer> nonEliteIndices = indices.subList(POPULATION_SIZE - nonEliteSize, POPULATION_SIZE);

        // Replace non-elite individuals with elite ones
        for (int i = 0; i < nonEliteSize; i++) {
            root[nonEliteIndices.get(i)] = eliteRoot[i % eliteSize].clone();
        }

        return root;
    }
    public static void writeFitnessValuesToCSV(double[] fitness_value, String filename) 
    throws IOException {
        try (FileWriter writer = new FileWriter(filename, true)) {
            double min = findMin(fitness_value);
            double max = findMax(fitness_value);
            double ave = findAverage(fitness_value);

            writer.append(min + "," + max + "," + ave + "\n");
        }
    }
    public static boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        // 遺伝情報を保持した個体を生成
        root = new int[POPULATION_SIZE][NUM_CITIES];
        // ファイルの読み込み。同一ディレクトリにある事を想定
        String filePath = "WesternSaharaPlot.txt";
        x = new double[NUM_CITIES]; 
        y = new double[NUM_CITIES];
        fitness_value = new double[POPULATION_SIZE];
        // 配列xとyに座標データを入力
        readCoordinatesFromFile(filePath, x, y);
        // 初期個体をInitRootメソッドで生成
        root = InitRoot(root);
        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {  // Loop for 100 generations
            double randomValue = new Random().nextDouble();
            if(MUTATION_RATE>randomValue){
                // System.out.println("突然変異が起こりました。");
                mutation(root);
            }
            order = encodePopulation(root);
            int[][] eliteRoot = applyEliteStrategy(root, fitness_value);
            childOrder = crossoverPopulation(order);
            root = decodePopulation(childOrder);
            fitness_value(root,fitness_value);
            root = replaceWithElite(root, eliteRoot, fitness_value);
            // printMatrix(root, fitness_value);
            try {
                writeFitnessValuesToCSV(fitness_value, "fitness_values.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
            printmin(fitness_value);
        }
    }
}
