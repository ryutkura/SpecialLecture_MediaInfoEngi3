import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListRemoveElement {

    public static void main(String[] args) {
        // 0から28の整数を含むリストを作成
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 28; i++) {
            list.add(i);
        }
        // リストのサイズを取得
        int size = list.size();

        // 結果を表示
        System.out.println("変更前のリストのサイズ: " + size);

        Random random = new Random();
        // ランダムな2つの位置を生成
        int index1 = random.nextInt(29);
        int indexToRemove = index1;
        System.out.println("削除した要素："+index1);
        removeElement(list, indexToRemove);
        indexToRemove = 3; // 例として16番目の要素を削除
        System.out.println("削除した要素："+indexToRemove);

        // リストの要素を削除し、詰める
        removeElement(list, indexToRemove);

        // 結果を表示
        for (int value : list) {
            System.out.print(value + " ");
        }
        System.out.println("");
        // // リストのサイズを取得
        int size1 = list.size();

        // 結果を表示
        System.out.println("変更後のリストのサイズ: " + size1);
    }

    // リストの要素を削除し、詰めるメソッド
    public static void removeElement(List<Integer> list, int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= list.size()) {
            // 無効なインデックスが指定された場合はそのまま返す
            return;
        }

        // 要素を削除
        list.remove(indexToRemove);

        // リストのサイズが変更されたら詰める処理は不要
    }
}
