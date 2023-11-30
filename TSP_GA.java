import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_GA {

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

        // �����̌Q�𐶐����A�V�����s�s�f�[�^���g�p���Đi�����J�n
        // Population population = new Population(POPULATION_SIZE, cities);

   

    }
}

class City{
    private double x;
    private double y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

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

    private void calculateFitness() {
        double totalDistance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalDistance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        totalDistance += tour.get(tour.size() - 1).distanceTo(tour.get(0)); // �Ō�̓s�s����n�_�ւ̋���
        fitness = totalDistance;
    }
}