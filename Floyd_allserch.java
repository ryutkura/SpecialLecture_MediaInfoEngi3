class Floyd_allserch{
    public static double calcflo(int[] Flv, int N){
        double A = 0;
        double B = 0;
        double ab = 1;
        for(int i=0;i<N;i++){
            if(Flv[i] == 1){
                A = A + Math.sqrt(i+1);
            }
            else{
                B = B + Math.sqrt(i+1);
            }
        }
        ab = A - B;
        return Math.abs(ab);
    }

    public static void main(String args[]){
        int N = 5;
        int[] Flv = new int[N];

        long startTime = System.nanoTime();
        for(int aa=0;aa<2;aa++){
            Flv[0] = aa;
            for(int ab=0;ab<2;ab++){
                Flv[1] = ab;
                for(int ac=0;ac<2;ac++){
                    Flv[2] = ac;
                    for(int ad=0;ad<2;ad++){
                        Flv[3] = ad;
                        for(int ae=0;ae<2;ae++){
                            Flv[4] = ae;
                            System.out.println("Floyd問題の今回の回答は"+calcflo(Flv, N)+"です。");
                        }
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("処理時間：" + (endTime - startTime) + " ナノ秒");

    }
}