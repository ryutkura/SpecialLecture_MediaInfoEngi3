class Floyd_allserch{
    public static double calcflo(int[] Flv){
        double A = 0;
        double B = 0;
        double ab = 1;
        for(int i=0;i<3;i++){
            if(Flv[i] == 1){
                A = A + Math.sqrt(i+1);
                System.out.println("Aの合計値は"+A+"です。");
            }
            else{
                B = B + Math.sqrt(i+1);
                System.out.println("Bの合計値は"+B+"です。");
            }
        }
        ab = A - B;
        return Math.abs(ab);
        // System.out.println("差分の絶対値は"+Math.abs(ab)+"です。");
    }

    public static void main(String args[]){
        int[] Flv = new int[3];
        //Flv = new int[]{0,1,1,0,0};
        for(int i=0;i<2;i++){
            Flv[0] = i;
            // System.out.println("Flv["+i+"]は"+Flv[i]+"です。");
            for(int j=0;j<2;j++){
                Flv[1] = j;
                // System.out.println("Flv["+j+"]は"+Flv[j]+"です。");
                for(int k=0;k<2;k++){
                    Flv[2] = k;
                    // System.out.println("Flv["+k+"]は"+Flv[k]+"です。");
                    System.out.println("Floyd問題の今回の回答は"+calcflo(Flv)+"です。");
                }
            }
        }

    }
}