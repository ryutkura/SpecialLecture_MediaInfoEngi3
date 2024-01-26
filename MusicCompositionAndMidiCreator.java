import javax.sound.midi.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
// import mymusic.MelodyEvaluationUtil;

public class MusicCompositionAndMidiCreator {
    public static void main(String[] args) {
        // �����̌Q�̐���
        int num_ind = 20; // �̐�
        int num_code = 4; // �R�[�h�̐�
        int num_measure = 4; // ���߂̐�
        int num_Gene = 2000; //���㐔
        int[][][] population = generateInitialPopulation(num_ind, num_code, num_measure);
    
        // �G���[�g�̂�ۑ����邽�߂̕ϐ�
        int[][][] elite = new int[1][num_code][num_measure];
        int eliteScore = Integer.MIN_VALUE;
    
        // // �����ʂ����]���ƌ���
        // for (int gen = 0; gen < num_Gene; gen++) {
        //     System.out.println("����: " + (gen + 1));
    
        //     // ���݂̐���̌̌Q��]��
        //     for (int i = 0; i < population.length; i++) {
        //         int score = MelodyEvaluationUtil.evaluateMelody(population, i);
        //         // �G���[�g�̂̍X�V
        //         if (score > eliteScore) {
        //             eliteScore = score;
        //             elite[0] = copyIndividual(population[i]);
        //         }
        //         // System.out.println("�� " + i + " �̕]��: " + score);
        //     }
    
        //     // ��������
        //     performCrossover(population, 10);
    
        //     // �G���[�g�̂�V��������Ɉ����p��
        //     // population[0] = elite[0];
        //     for (int j = 0; j < num_code; j++) {
        //         for (int k = 0; k < num_measure; k++) {
        //             population[0][j][k] = elite[0][j][k];
        //         }
        //     }
            
        // }
    
        // // ���ʂ̃G���[�g�̂̕]��
        // System.out.println("�ŏI�G���[�g�̂̕]��: " + eliteScore);

        // // �ŏI�I�ȃG���[�g�̂̃C���f�b�N�X���擾���郍�W�b�N������
        // int eliteIndex = getEliteIndividualIndex(population);

        

        try (FileWriter csvWriter = new FileWriter("generation_stats.csv")) {
            // CSV�t�@�C���̃w�b�_�[
            csvWriter.append("Generation,Max,Min,Average\n");

            for (int gen = 0; gen < num_Gene; gen++) {
                System.out.println("����: " + (gen + 1));
                int[] scores = new int[num_ind];  // ���̐���̑S�̂̃X�R�A

                // ���݂̐���̌̌Q��]��
                for (int i = 0; i < population.length; i++) {
                    int score = MelodyEvaluationUtil.evaluateMelody(population, i);
                    scores[i] = score;  // �X�R�A�z��ɕۑ�
                    // �G���[�g�̂̍X�V...
                    if (score > eliteScore) {
                        eliteScore = score;
                        elite[0] = copyIndividual(population[i]);
                    }
                }

                // �ő�l�A�ŏ��l�A���ϒl�̌v�Z
                int max = Arrays.stream(scores).max().getAsInt();
                int min = Arrays.stream(scores).min().getAsInt();
                double average = Arrays.stream(scores).average().getAsDouble();

                // CSV�t�@�C���ւ̏�������
                csvWriter.append(String.format("%d,%d,%d,%.2f\n", gen + 1, max, min, average));

                // ��������
                performCrossover(population, 10);
    
                // �G���[�g�̂�V��������Ɉ����p��
                for (int j = 0; j < num_code; j++) {
                    for (int k = 0; k < num_measure; k++) {
                        population[0][j][k] = elite[0][j][k];
                    }
                }
            }

            System.out.println("CSV�t�@�C������������܂����B");

        } catch (IOException e) {
            e.printStackTrace();
        }
            // ���ʂ̃G���[�g�̂̕]��
            System.out.println("�ŏI�G���[�g�̂̕]��: " + eliteScore);

            // �ŏI�I�ȃG���[�g�̂̃C���f�b�N�X���擾���郍�W�b�N������
            int eliteIndex = getEliteIndividualIndex(population);
        // �G���[�g�̂�MIDI�t�@�C���ɏ�������
        try {
            writeToMidiFile(population, "generated_melody.mid", eliteIndex);
            System.out.println("MIDI�t�@�C������������܂���");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // �̂̃R�s�[���쐬����w���p�[���\�b�h
    public static int[][] copyIndividual(int[][] individual) {
        int[][] newIndividual = new int[individual.length][individual[0].length];
        for (int i = 0; i < individual.length; i++) {
            System.arraycopy(individual[i], 0, newIndividual[i], 0, individual[i].length);
        }
        return newIndividual;
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

    public static int getEliteIndividualIndex(int[][][] population) {
        int bestScore = Integer.MIN_VALUE; // �ł������X�R�A��ێ����邽�߂̕ϐ��B�\�Ȍ���Ⴂ�l�ŏ������B
        int bestIndex = -1; // �ł������X�R�A�����̂̃C���f�b�N�X�B���݂��Ȃ��ꍇ�̂��߂�-1�ŏ������B
    
        // �S�Ă̌̂ɑ΂��ă��[�v���s���A���ꂼ���]��
        for (int i = 0; i < population.length; i++) {
            int score = MelodyEvaluationUtil.evaluateMelody(population, i); // �̂̃X�R�A��]��
    
            // ���݂̌̂̃X�R�A������܂ł̍ō��X�R�A���������ꍇ�A�����X�V
            if (score > bestScore) {
                bestScore = score; // �ō��X�R�A���X�V
                bestIndex = i; // �ō��X�R�A�����̂̃C���f�b�N�X���X�V
            }
        }
    
        // �ł������X�R�A�����̂̃C���f�b�N�X��Ԃ�
        return bestIndex;
    }
    
    private static void writeToMidiFile(int[][][] population, String fileName, int eliteIndex) throws InvalidMidiDataException, IOException {
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
    
        // �G���[�g�̂̉�����MIDI�C�x���g�Ƃ��Ēǉ�
        int tick = 0;
        for (int j = 0; j < population[eliteIndex].length; j++) {
            for (int k = 0; k < population[eliteIndex][j].length; k++) {
                int note = population[eliteIndex][j][k];
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
