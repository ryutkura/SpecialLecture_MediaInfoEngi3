import java.util.Random;

public class MusicComposition {

    public static void main(String[] args) {
        // �p�����[�^�̐ݒ�
        int num_ind = 10; // �̐�
        int num_code = 4; // �R�[�h�̐�
        int num_measure = 4; // ���߂̐�
        int[][][] population = new int[num_ind][num_code][num_measure];

        // �����̂̐���
        Random random = new Random();
        for (int i = 0; i < num_ind; i++) {
            for (int j = 0; j < num_code; j++) {
                for (int k = 0; k < num_measure; k++) {
                    // �h(60)���玟�̃h�܂ł͈̔͂Ń����_���ȉ����𐶐�
                    population[i][j][k] = 60 + random.nextInt(13);
                }
            }
        }

        // �������ꂽ�����̂̕\��
        for (int i = 0; i < num_ind; i++) {
            System.out.println("�� " + (i + 1) + ":");
            for (int j = 0; j < num_code; j++) {
                for (int k = 0; k < num_measure; k++) {
                    System.out.print(population[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
