import javax.sound.midi.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
// import mymusic.MelodyEvaluationUtil;

public class MusicCompositionAndMidiCreator {
    public static void main(String[] args) {
        // 初期個体群の生成
        int num_ind = 20; // 個体数
        int num_code = 4; // コードの数
        int num_measure = 4; // 小節の数
        int num_Gene = 2000; //世代数
        int[][][] population = generateInitialPopulation(num_ind, num_code, num_measure);
    
        // エリート個体を保存するための変数
        int[][][] elite = new int[1][num_code][num_measure];
        int eliteScore = Integer.MIN_VALUE;
    
        // // 世代を通じた評価と交叉
        // for (int gen = 0; gen < num_Gene; gen++) {
        //     System.out.println("世代: " + (gen + 1));
    
        //     // 現在の世代の個体群を評価
        //     for (int i = 0; i < population.length; i++) {
        //         int score = MelodyEvaluationUtil.evaluateMelody(population, i);
        //         // エリート個体の更新
        //         if (score > eliteScore) {
        //             eliteScore = score;
        //             elite[0] = copyIndividual(population[i]);
        //         }
        //         // System.out.println("個体 " + i + " の評価: " + score);
        //     }
    
        //     // 交叉処理
        //     performCrossover(population, 10);
    
        //     // エリート個体を新しい世代に引き継ぐ
        //     // population[0] = elite[0];
        //     for (int j = 0; j < num_code; j++) {
        //         for (int k = 0; k < num_measure; k++) {
        //             population[0][j][k] = elite[0][j][k];
        //         }
        //     }
            
        // }
    
        // // 結果のエリート個体の評価
        // System.out.println("最終エリート個体の評価: " + eliteScore);

        // // 最終的なエリート個体のインデックスを取得するロジックを実装
        // int eliteIndex = getEliteIndividualIndex(population);

        

        try (FileWriter csvWriter = new FileWriter("generation_stats.csv")) {
            // CSVファイルのヘッダー
            csvWriter.append("Generation,Max,Min,Average\n");

            for (int gen = 0; gen < num_Gene; gen++) {
                System.out.println("世代: " + (gen + 1));
                int[] scores = new int[num_ind];  // この世代の全個体のスコア

                // 現在の世代の個体群を評価
                for (int i = 0; i < population.length; i++) {
                    int score = MelodyEvaluationUtil.evaluateMelody(population, i);
                    scores[i] = score;  // スコア配列に保存
                    // エリート個体の更新...
                    if (score > eliteScore) {
                        eliteScore = score;
                        elite[0] = copyIndividual(population[i]);
                    }
                }

                // 最大値、最小値、平均値の計算
                int max = Arrays.stream(scores).max().getAsInt();
                int min = Arrays.stream(scores).min().getAsInt();
                double average = Arrays.stream(scores).average().getAsDouble();

                // CSVファイルへの書き込み
                csvWriter.append(String.format("%d,%d,%d,%.2f\n", gen + 1, max, min, average));

                // 交叉処理
                performCrossover(population, 10);
    
                // エリート個体を新しい世代に引き継ぐ
                for (int j = 0; j < num_code; j++) {
                    for (int k = 0; k < num_measure; k++) {
                        population[0][j][k] = elite[0][j][k];
                    }
                }
            }

            System.out.println("CSVファイルが生成されました。");

        } catch (IOException e) {
            e.printStackTrace();
        }
            // 結果のエリート個体の評価
            System.out.println("最終エリート個体の評価: " + eliteScore);

            // 最終的なエリート個体のインデックスを取得するロジックを実装
            int eliteIndex = getEliteIndividualIndex(population);
        // エリート個体をMIDIファイルに書き込む
        try {
            writeToMidiFile(population, "generated_melody.mid", eliteIndex);
            System.out.println("MIDIファイルが生成されました");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 個体のコピーを作成するヘルパーメソッド
    public static int[][] copyIndividual(int[][] individual) {
        int[][] newIndividual = new int[individual.length][individual[0].length];
        for (int i = 0; i < individual.length; i++) {
            System.arraycopy(individual[i], 0, newIndividual[i], 0, individual[i].length);
        }
        return newIndividual;
    }
    
    // 3次元配列 population の内容を表示するメソッド
    public static void printPopulation(int[][][] population) {
        int num_ind = population.length;
        int num_code = population[0].length;
        int num_measure = population[0][0].length;

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

    // 交叉を行うメソッド
    public static void performCrossover(int[][][] population, int crossoverCount) {
        Random random = new Random();
        int num_ind = population.length;
        int num_code = population[0].length;
        int num_measure = population[0][0].length;

        for (int measure = 0; measure < num_measure; measure++) {
            for (int count = 0; count < crossoverCount; count++) {
                // 交叉する個体のペアをランダムに選択
                int ind1 = random.nextInt(num_ind);
                int ind2 = random.nextInt(num_ind);

                // 同一小節内で交叉実行
                for (int j = 0; j < num_code; j++) {
                    // 交叉ポイントをランダムに選択
                    int crossoverPoint = random.nextInt(num_code);

                    // 交叉（1点交叉）
                    int temp = population[ind1][j][measure];
                    population[ind1][j][measure] = population[ind2][crossoverPoint][measure];
                    population[ind2][crossoverPoint][measure] = temp;
                }
            }
        }
    }
    
    private static int[][][] generateInitialPopulation(int num_ind, int num_code, int num_measure) {
        int[][][] population = new int[num_ind][num_code][num_measure];
        Random random = new Random();

        // 各コードに対応するMIDIノート
        int[] cMajor = {60, 64, 67}; // C, E, G
        int[] fMajor = {65, 69, 60}; // F, A, C
        int[] gMajor = {67, 71, 62}; // G, B, D

        for (int i = 0; i < num_ind; i++) {
            for (int j = 0; j < num_code; j++) {
                for (int k = 0; k < num_measure; k++) {
                    if (j == 0) { // Cコード
                        population[i][j][k] = cMajor[random.nextInt(cMajor.length)];
                    } else if (j == 1) { // Fコード
                        population[i][j][k] = fMajor[random.nextInt(fMajor.length)];
                    } else if (j == 2) { // Gコード
                        population[i][j][k] = gMajor[random.nextInt(gMajor.length)];
                    } else { // 再びCコード
                        population[i][j][k] = cMajor[random.nextInt(cMajor.length)];
                    }
                }
            }
        }
        return population;
    }

    public static int getEliteIndividualIndex(int[][][] population) {
        int bestScore = Integer.MIN_VALUE; // 最も高いスコアを保持するための変数。可能な限り低い値で初期化。
        int bestIndex = -1; // 最も高いスコアを持つ個体のインデックス。存在しない場合のために-1で初期化。
    
        // 全ての個体に対してループを行い、それぞれを評価
        for (int i = 0; i < population.length; i++) {
            int score = MelodyEvaluationUtil.evaluateMelody(population, i); // 個体のスコアを評価
    
            // 現在の個体のスコアがこれまでの最高スコアよりも高い場合、情報を更新
            if (score > bestScore) {
                bestScore = score; // 最高スコアを更新
                bestIndex = i; // 最高スコアを持つ個体のインデックスを更新
            }
        }
    
        // 最も高いスコアを持つ個体のインデックスを返す
        return bestIndex;
    }
    
    private static void writeToMidiFile(int[][][] population, String fileName, int eliteIndex) throws InvalidMidiDataException, IOException {
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
    
        // エリート個体の音符をMIDIイベントとして追加
        int tick = 0;
        for (int j = 0; j < population[eliteIndex].length; j++) {
            for (int k = 0; k < population[eliteIndex][j].length; k++) {
                int note = population[eliteIndex][j][k];
                track.add(createNoteOnEvent(note, tick));
                tick += 4; // 音符の長さ（固定）
                track.add(createNoteOffEvent(note, tick));
            }
        }
    
        File midiFile = new File(fileName);
        MidiSystem.write(sequence, 1, midiFile);
    }
    

    private static MidiEvent createNoteOnEvent(int nKey, long tick) throws InvalidMidiDataException {
        return new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, nKey, 0x60), tick);
    }

    private static MidiEvent createNoteOffEvent(int nKey, long tick) throws InvalidMidiDataException {
        return new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, nKey, 0x40), tick);
    }
}
