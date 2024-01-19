import java.util.Random;

public class MusicComposition {

    public static void main(String[] args) {
        // パラメータの設定
        int num_ind = 10; // 個体数
        int num_code = 4; // コードの数
        int num_measure = 4; // 小節の数
        int[][][] population = new int[num_ind][num_code][num_measure];

        // 初期個体の生成
        Random random = new Random();
        for (int i = 0; i < num_ind; i++) {
            for (int j = 0; j < num_code; j++) {
                for (int k = 0; k < num_measure; k++) {
                    // ド(60)から次のドまでの範囲でランダムな音符を生成
                    population[i][j][k] = 60 + random.nextInt(13);
                }
            }
        }

        // 生成された初期個体の表示
        for (int i = 0; i < num_ind; i++) {
            System.out.println("個体 " + (i + 1) + ":");
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
