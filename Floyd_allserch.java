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
        int N = 3;
        int[] Flv = new int[N];

        long startTime = System.nanoTime();
        for(int i=0;i<2;i++){
            Flv[0] = i;
            for(int j=0;j<2;j++){
                Flv[1] = j;
                for(int k=0;k<2;k++){
                    Flv[2] = k;
                    System.out.println("Floyd–â‘è‚Ì¡‰ñ‚Ì‰ñ“š‚Í"+calcflo(Flv, N)+"‚Å‚·B");
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("ˆ—ŽžŠÔF" + (endTime - startTime) + " ƒiƒm•b");

    }
}