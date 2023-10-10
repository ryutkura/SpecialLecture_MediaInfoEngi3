class Floyd_GA{
    public static double fitness(int[][] indiv){
        double A = 0;
        double B = 0;
        double ab = 1;
        for(int i=0;i<indiv.length;i++){
            if(indiv[i][0] == 1){
                A = A + Math.sqrt(i+1);
            }
            else{
                B = B + Math.sqrt(i+1);
            }
        }
        ab = A - B;
        return Math.abs(ab);
    }
    public static int[][] init(int[][] indiv){
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<11;j++){
                indiv[i][j] = 1;
            }
        }
        return indiv;
    }
    static void printvec(int[][] indiv){
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<11;j++){
                System.out.print(indiv[i][j]);
            }
            System.out.println("");
        }
    }
    public static int[][] GA(int[][] indiv){
        int num=1;
        num = (int)Math.ceil(Math.random() * 10);
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<11;j++){
                indiv[i][j] = 1*num;
            }
        }
        return indiv;
    }
    
    public static void main(String args[]){
        int indiv[][] = new int[10][11];
        init(indiv);
        for(int i=0;i<indiv.length;i++){
            System.out.println("‚½‚¾‚¢‚Ü"+(i+1)+"‰ñ–Ú");
            GA(indiv);
            printvec(indiv);
        }
    }
}