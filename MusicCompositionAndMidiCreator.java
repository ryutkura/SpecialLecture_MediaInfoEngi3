import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MusicCompositionAndMidiCreator {

    public static void main(String[] args) {
        // 初期個体の生成
        int num_ind = 10; // 個体数
        int num_code = 4; // コードの数
        int num_measure = 4; // 小節の数
        int[][][] population = generateInitialPopulation(num_ind, num_code, num_measure);

        // 個体の表示（デバッグ用）
        // printPopulation(population);
        // 交叉処理
        performCrossover(population, 10); // 交叉回数は例として10回とする
        // 個体の表示（デバッグ用）
        // printPopulation(population);

        // MIDIファイルに書き込み
        try {
            writeToMidiFile(population, "generated_melody.mid");
            System.out.println("MIDIファイルが生成されました");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private static void writeToMidiFile(int[][][] population, String fileName) throws InvalidMidiDataException, IOException {
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();

        // ランダムに個体を選択
        Random random = new Random();
        int selectedInd = random.nextInt(population.length);

        // 選択した個体の音符をMIDIイベントとして追加
        int tick = 0;
        for (int j = 0; j < population[selectedInd].length; j++) {
            for (int k = 0; k < population[selectedInd][j].length; k++) {
                int note = population[selectedInd][j][k];
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
