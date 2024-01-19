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

        // MIDI�t�@�C���ɏ�������
        try {
            writeToMidiFile(population, "generated_melody.mid");
            System.out.println("MIDI�t�@�C������������܂���");
        } catch (Exception e) {
            e.printStackTrace();
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
