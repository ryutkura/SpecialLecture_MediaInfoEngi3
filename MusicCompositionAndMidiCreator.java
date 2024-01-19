import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MusicCompositionAndMidiCreator {

    public static void main(String[] args) {
        // �����̂̐���
        int num_ind = 10; // �̐�
        int num_code = 4; // �R�[�h�̐�
        int num_measure = 4; // ���߂̐�
        int[][][] population = generateInitialPopulation(num_ind, num_code, num_measure);

        // �̂̕\���i�f�o�b�O�p�j
        // printPopulation(population);
        // ��������
        performCrossover(population, 10); // �����񐔂͗�Ƃ���10��Ƃ���
        // �̂̕\���i�f�o�b�O�p�j
        // printPopulation(population);

        // MIDI�t�@�C���ɏ�������
        try {
            writeToMidiFile(population, "generated_melody.mid");
            System.out.println("MIDI�t�@�C������������܂���");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 3�����z�� population �̓��e��\�����郁�\�b�h
    public static void printPopulation(int[][][] population) {
        int num_ind = population.length;
        int num_code = population[0].length;
        int num_measure = population[0][0].length;

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

    // �������s�����\�b�h
    public static void performCrossover(int[][][] population, int crossoverCount) {
        Random random = new Random();
        int num_ind = population.length;
        int num_code = population[0].length;
        int num_measure = population[0][0].length;

        for (int measure = 0; measure < num_measure; measure++) {
            for (int count = 0; count < crossoverCount; count++) {
                // ��������̂̃y�A�������_���ɑI��
                int ind1 = random.nextInt(num_ind);
                int ind2 = random.nextInt(num_ind);

                // ���ꏬ�ߓ��Ō������s
                for (int j = 0; j < num_code; j++) {
                    // �����|�C���g�������_���ɑI��
                    int crossoverPoint = random.nextInt(num_code);

                    // �����i1�_�����j
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

        // �e�R�[�h�ɑΉ�����MIDI�m�[�g
        int[] cMajor = {60, 64, 67}; // C, E, G
        int[] fMajor = {65, 69, 60}; // F, A, C
        int[] gMajor = {67, 71, 62}; // G, B, D

        for (int i = 0; i < num_ind; i++) {
            for (int j = 0; j < num_code; j++) {
                for (int k = 0; k < num_measure; k++) {
                    if (j == 0) { // C�R�[�h
                        population[i][j][k] = cMajor[random.nextInt(cMajor.length)];
                    } else if (j == 1) { // F�R�[�h
                        population[i][j][k] = fMajor[random.nextInt(fMajor.length)];
                    } else if (j == 2) { // G�R�[�h
                        population[i][j][k] = gMajor[random.nextInt(gMajor.length)];
                    } else { // �Ă�C�R�[�h
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

        // �����_���Ɍ̂�I��
        Random random = new Random();
        int selectedInd = random.nextInt(population.length);

        // �I�������̂̉�����MIDI�C�x���g�Ƃ��Ēǉ�
        int tick = 0;
        for (int j = 0; j < population[selectedInd].length; j++) {
            for (int k = 0; k < population[selectedInd][j].length; k++) {
                int note = population[selectedInd][j][k];
                track.add(createNoteOnEvent(note, tick));
                tick += 4; // �����̒����i�Œ�j
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
