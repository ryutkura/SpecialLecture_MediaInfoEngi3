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
    private static final double MUTATION_RATE = 0.1;
    // 世代数
    private static final int NUM_GENERATIONS = 2000;

    // 個体を管理する配列
    static int[][] root;
    static double[] x;
    static double[] y;

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

    public static double calculateFitness(int i,int j,double[] x,double[] y){
        double ans = 0;
        double dx = x[i] - x[j];
        double dy = y[i] - y[j];
        ans = Math.sqrt(dx*dx + dy*dy);

        return ans;
    }

    public static int[][] InitRoot(int[][] root) {
        Random random = new Random();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            // 0から28の整数を重複なしで入力
            for (int j = 0; j < NUM_CITIES; j++) {
                int city;
                do {
                    city = random.nextInt(NUM_CITIES);
                } while (contains(root[i], city));
                root[i][j] = city;
            }
        }
        return root;
    }
    public static boolean contains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }
    public static void printMatrix(int[][] matrix) {
        // static void printinit(int[][] indiv, double[] fit_value){
            for(int i=0;i<matrix.length;i++){
                for(int j=0;j<matrix[i].length;j++){
                    System.out.print(" "+matrix[i][j]);
                }
                // System.out.println("："+fit_value[i]);
                System.out.println("");
            }
            System.out.println("---------------------------------");
        
    }

    public static void main(String[] args){
        root = new int[POPULATION_SIZE][NUM_CITIES];

        String filePath = "WesternSaharaPlot.txt";
        double[] x = new double[NUM_CITIES]; 
        double[] y = new double[NUM_CITIES]; 

        readCoordinatesFromFile(filePath, x, y);

        for(int k=0;k<10;k++){
            Random random = new Random();
            int i = random.nextInt(NUM_CITIES);
            int j = random.nextInt(NUM_CITIES);

            System.out.println("iの値は"+(i+1)+"、jの値は"+(j+1)+"、答えは:"+calculateFitness(i, j, x, y));
        }

        root = InitRoot(root);
        // 2次元配列の内容を表示（デバッグ用）
        printMatrix(root);

    }
}
