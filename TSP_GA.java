import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_GA {
    public static void main(String[] args){

    }
}

class City{
    private double x;
    private double y;

    public City(int x, int y) {
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
    }
}