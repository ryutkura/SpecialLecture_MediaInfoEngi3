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
### 新結論
アク禁は悪手だった。
そもそもエリート戦略は優秀な個体がそうでない個体と配合する事で優秀でなくなりエリートの要素が世代を追う毎になくなっていくのを防ぐ位のものであり、エリートとの交配を行わない作戦ではなかった。

## GAにおけるエリート戦略の使い方(俺流)
評価値の良い個体は特定の選出方法で選ばれる。
交叉を行う。
- より良い解が見つかったら最大値を保存した最小値で上書きする
- そうでないなら最小値を上書きする

これらでエリートを残していく

## 選出方法

# 探した書籍の資料