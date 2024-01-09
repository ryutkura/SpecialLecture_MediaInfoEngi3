import javax.sound.midi.*;
import java.io.*;
import java.util.Random;

public class MidiEditor {
    public static void main(String[] args) {
        Random rand = new Random();
        int k = rand.nextInt();
        System.out.println("k=" + k);

        try {
            File inputFile = new File("test1.mid");
            File outputFile = new File("output.mid");

            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);

            int i;
            while ((i = fis.read()) != -1) {
                // �h��\�������̃o�C�g�ł���΁A�����ɂ���
                if (i == 60) {
                    i++;
                }
                fos.write(i);
            }

            fis.close();
            fos.close();

            // MIDI�t�@�C�����Đ�
            playMidi(outputFile);

        } catch (IOException | MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private static void playMidi(File file) throws IOException, MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer();
        if (sequencer == null) {
            System.err.println("Sequencer device not supported");
            return;
        }
        sequencer.open();

        // MIDI�t�@�C����ǂݍ���
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        sequencer.setSequence(is);

        // �Đ�
        sequencer.start();

        System.out.println("output.mid���Đ���... Enter�L�[�Œ�~���܂��B");
        System.in.read();

        // ��~
        sequencer.stop();
        sequencer.close();
    }
}
