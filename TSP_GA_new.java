import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class TSP_GA_new {

    // 都市の数
    private static final int NUM_CITIES = 29;
    // 個体数
    private static final int POPULATION_SIZE = 20;
    // 一定回数の交叉を保証する回数
    private static final int CROSSOVER_GUARANTEED_COUNT = 500;
    // 突然変異率
    private static final double MUTATION_RATE = 0.5;
    // 世代数
    private static final int NUM_GENERATIONS = 2000;

    // 個体を管理する配列
    static int[][] root;
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
        // 評価値を計算
        fitness_value(root, fitness_value);
        // 初期個体の内容と評価値を出力(デバッグ用メソッド)
        printMatrix(root,fitness_value);
        // 最小値のみを出力
        printmin(fitness_value);
        // 最大値のみを出力
        printmax(fitness_value);
        // 平均値のみを出力
        printave(fitness_value);
        for(int k=0;k<10;k++){
            double randomValue = new Random().nextDouble();
            if(MUTATION_RATE>randomValue){
                System.out.println("突然変異が起こりました。");
                mutation(root);
                // 評価値を計算
                fitness_value(root, fitness_value);
                // 初期個体の内容と評価値を出力(デバッグ用メソッド)
                printMatrix(root,fitness_value);
                // 最小値のみを出力
                printmin(fitness_value);
                // 最大値のみを出力
                printmax(fitness_value);
                // 平均値のみを出力
                printave(fitness_value);
            }
        }

    }
}
