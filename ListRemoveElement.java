import java.util.ArrayList;
import java.util.List;

public class ListRemoveElement {

    public static void main(String[] args) {
        // 0����28�̐������܂ރ��X�g���쐬
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 28; i++) {
            list.add(i);
        }
        // ���X�g�̃T�C�Y���擾
        int size = list.size();

        // ���ʂ�\��
        System.out.println("�ύX�O�̃��X�g�̃T�C�Y: " + size);

        int indexToRemove = 15; // ��Ƃ���16�Ԗڂ̗v�f���폜

        // ���X�g�̗v�f���폜���A�l�߂�
        removeElement(list, indexToRemove);

        // ���ʂ�\��
        for (int value : list) {
            System.out.print(value + " ");
        }
        System.out.println("");
        // // ���X�g�̃T�C�Y���擾
        int size1 = list.size();

        // ���ʂ�\��
        System.out.println("�ύX��̃��X�g�̃T�C�Y: " + size1);
    }

    // ���X�g�̗v�f���폜���A�l�߂郁�\�b�h
    public static void removeElement(List<Integer> list, int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= list.size()) {
            // �����ȃC���f�b�N�X���w�肳�ꂽ�ꍇ�͂��̂܂ܕԂ�
            return;
        }

        // �v�f���폜
        list.remove(indexToRemove);

        // ���X�g�̃T�C�Y���ύX���ꂽ��l�߂鏈���͕s�v
    }
}
