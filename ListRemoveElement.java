import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        Random random = new Random();
        // �����_����2�̈ʒu�𐶐�
        int index1 = random.nextInt(29);
        int indexToRemove = index1;
        System.out.println("�폜�����v�f�F"+index1);
        removeElement(list, indexToRemove);
        indexToRemove = 3; // ��Ƃ���16�Ԗڂ̗v�f���폜
        System.out.println("�폜�����v�f�F"+indexToRemove);

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
