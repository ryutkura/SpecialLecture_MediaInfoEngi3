# 探したネットの資料
## GAのエリート戦略に使用する特定の数字を除いた乱数の作成
> https://qiita.com/kiyoshi999/items/a4f4036b33337264d4bf

リストやセット等のコレクションを利用してコードを書いている。
### コードメモ(set編)
```java
import java.util.Random;
import java.util.HashSet;

public class Main {
    
    public static void main(String[] args) {
        Random rand = new Random();
        int i;
        // 除外したい数値を格納する
        Set exSet = new HashSet();
        exList.add(2);
        exList.add(5);
        exList.add(8);

        do {
            i = Interger.valueOf(rand.nextInt(10)+1);　// 1~10までの乱数を生成し文字列に変換
        } while (exSet.contains(i)); //除外リストのいずれかの数値と一致なら繰り返す
        System.out.println(i);
    }
}
```
### コードメモ(list編)
```java
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        String i;
        // 除外したい数値を格納する
        List<String> exList = new ArrayList<String>();
        exList.add("2");
        exList.add("5");
        exList.add("8");

        do {
            i = String.valueOf(rand.nextInt(10)+1);　// 1~10までの乱数を生成し文字列に変換
        } while (exList.contains(i)); //除外リストのいずれかの数値と一致なら繰り返す
        System.out.println(i);
    }
}
```

# 探した書籍の資料