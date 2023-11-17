# 遺伝的アルゴリズムで巡回セールスマン問題を解く
## 基本方針
1. 都市データの取得
2. 都市データはインスタンスで管理する。
3. 2つの都市を選択(この選択方法は後述)してインスタンスを参照、その後都市間の距離を算出
4. 選んだ都市をlistに格納する
5. 3,4を都市がなくなるまで繰り返す
6. 上記で個体の評価値やノード情報が確定する  
個体数の限り繰り返す
7. これらをルーレット等の戦略で交配
8. 3~7を世代数でループ
9. 解を得る

## コードについて
- 都市データのインスタンス
```java
// 都市クラス
class City {
    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // 2つの都市間の距離を計算
    public double distanceTo(City otherCity) {
        int dx = this.x - otherCity.x;
        int dy = this.y - otherCity.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
```
- 個体データのインスタンス
```java
public class Individual {
    private ArrayList<City> tour;
    private double fitness;

    // コンストラクタや他のメソッドは省略

    public Individual(ArrayList<City> tour) {
        this.tour = new ArrayList<>(tour);
        calculateFitness();
    }

    public ArrayList<City> getTour() {
        return tour;
    }

    public double getFitness() {
        return fitness;
    }

    public void generateIndividual() {
        Collections.shuffle(tour);
        calculateFitness();
    }

    private void calculateFitness() {
        // 個体の適応度（fitness）を計算するメソッド
        City.distanceTo();
    }
}
```

- ノード情報の保持  
初期化ではlistの中身をランダムに入れ替えて個体の情報とする  
選んだ都市(2つ)の数値での添え字をListに格納する  
以降listの中身に無い都市のインスタンスを選ぶようにする
- 交配について  

- 突然変異について  
ランダムに任意の2都市を入れ替える
