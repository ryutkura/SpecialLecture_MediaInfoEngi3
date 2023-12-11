import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class TSP_GA_new {

    // �s�s�̐�
    private static final int NUM_CITIES = 29;
    // �̐�
    private static final int POPULATION_SIZE = 20;
    // ���񐔂̌�����ۏ؂����
    private static final int CROSSOVER_GUARANTEED_COUNT = 500;
    // �ˑR�ψٗ�
    private static final double MUTATION_RATE = 0.5;
    // ���㐔
    private static final int NUM_GENERATIONS = 2000;

    // �̂��Ǘ�����z��
    static int[][] root;
    static double[] x;
    static double[] y;
    static double[] fitness_value;

    // ���W�f�[�^��ǂݍ��ރ��\�b�h
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

    // 2�_�Ԃ̋����̌v�Z���s�����\�b�h
    public static double calculateFitness(int p1,int p2,double[] x,double[] y){
        double ans = 0;
        double dx = x[p1] - x[p2];
        double dy = y[p1] - y[p2];
        ans = Math.sqrt(dx*dx + dy*dy);

        return ans;
    }

    // �����̂̐������s�����\�b�h
    public static int[][] InitRoot(int[][] root) {
        Random random = new Random();

        for(int i=0;i<POPULATION_SIZE;i++){
            for(int j=0;j<NUM_CITIES;j++){
                root[i][j] = j;
            }
            for(int j=0;j<NUM_CITIES;j++){
                int index = random.nextInt(j + 1);
                // �v�f�̓���ւ�
                int temp = root[i][j];
                root[i][j] = root[i][index];
                root[i][index] = temp;
            }
        }
        return root;
    }
    // �̂̋����̎Z�o���s�����\�b�h
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
    // debug�p�̏o�̓��\�b�h
    public static void printMatrix(int[][] matrix,double[] mat) {
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                System.out.print(" "+matrix[i][j]);
            }
            // System.out.println("");
            System.out.println("�F"+mat[i]);
        }
        System.out.println("---------------------------------");
    }
    // ���ő�l�����߂郁�\�b�h
    public static double findMax(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty or null");
        }

        return Arrays.stream(array)
                .max()
                .orElseThrow();
    }
    // ���ŏ��l�����߂郁�\�b�h
    public static double findMin(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty or null");
        }

        return Arrays.stream(array)
                .min()
                .orElseThrow();
    }
    // �����ϒl�����߂郁�\�b�h
    public static double findAverage(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty or null");
        }

        return Arrays.stream(array)
                .average()
                .orElseThrow();
    }
    // �ŏ��l���o�͂��郁�\�b�h
    public static void printmin(double[] fitness_value){
        System.out.println("�ŏ��l��"+findMin(fitness_value)+"�ł��B");
    }
    // �ő�l���o�͂��郁�\�b�h
    public static void printmax(double[] fitness_value){
        System.out.println("�ő�l��"+findMax(fitness_value)+"�ł��B");
    }
    // ���ϒl���o�͂��郁�\�b�h
    public static void printave(double[] fitness_value){
        System.out.println("���ϒl��"+findAverage(fitness_value)+"�ł��B");
    }
    // �ˑR�ψق��s�����\�b�h
    public static void mutation(int[][] array) {
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Invalid array");
        }
        Random random = new Random();
        // �����_����2�̈ʒu�𐶐�
        int index1 = random.nextInt(NUM_CITIES);
        int index2;
        int index3 = random.nextInt(POPULATION_SIZE);
        do {
            index2 = random.nextInt(NUM_CITIES);
        } while (index1 == index2);
        // �v�f�̓���ւ�
        int temp = array[index3][index1];
        array[index3][index1] = array[index3][index2];
        array[index3][index2] = temp;
        System.out.println("����̓ˑR�ψق͌�"+index3+"�̗v�f"+index1+"�Ɨv�f"+index2+"�ŋN����܂���");
    }

    public static void main(String[] args){
        // ��`����ێ������̂𐶐�
        root = new int[POPULATION_SIZE][NUM_CITIES];
        // �t�@�C���̓ǂݍ��݁B����f�B���N�g���ɂ��鎖��z��
        String filePath = "WesternSaharaPlot.txt";
        x = new double[NUM_CITIES]; 
        y = new double[NUM_CITIES];
        fitness_value = new double[POPULATION_SIZE];
        // �z��x��y�ɍ��W�f�[�^�����
        readCoordinatesFromFile(filePath, x, y);
        // �����̂�InitRoot���\�b�h�Ő���
        root = InitRoot(root);
        // �]���l���v�Z
        fitness_value(root, fitness_value);
        // �����̂̓��e�ƕ]���l���o��(�f�o�b�O�p���\�b�h)
        printMatrix(root,fitness_value);
        // �ŏ��l�݂̂��o��
        printmin(fitness_value);
        // �ő�l�݂̂��o��
        printmax(fitness_value);
        // ���ϒl�݂̂��o��
        printave(fitness_value);
        for(int k=0;k<10;k++){
            double randomValue = new Random().nextDouble();
            if(MUTATION_RATE>randomValue){
                System.out.println("�ˑR�ψق��N����܂����B");
                mutation(root);
                // �]���l���v�Z
                fitness_value(root, fitness_value);
                // �����̂̓��e�ƕ]���l���o��(�f�o�b�O�p���\�b�h)
                printMatrix(root,fitness_value);
                // �ŏ��l�݂̂��o��
                printmin(fitness_value);
                // �ő�l�݂̂��o��
                printmax(fitness_value);
                // ���ϒl�݂̂��o��
                printave(fitness_value);
            }
        }

    }
}
