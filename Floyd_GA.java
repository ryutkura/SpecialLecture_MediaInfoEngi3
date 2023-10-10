class Floyd_GA{
    public static void fitness(int[][] indiv,double[] fit_value){
        double ans = 0;
        double A = 0;
        double B = 0;
        double ab = 1;

        for(int i=0;i<indiv.length;i++){
            A = 0;
            B = 0;
            for(int j=0;j<11;j++){
                if(indiv[i][j] == 1){
                    A = A + Math.sqrt(j+1);
                }
                else{
                    B = B + Math.sqrt(j+1);
                }
            }
            fit_value[i] = 0;
            ab = A - B;
            ans = Math.abs(ab);
            fit_value[i] = ans;
        }
    }
    public static int[][] init(int[][] indiv){
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<11;j++){
                double alpha = Math.random();
                if(alpha>0.5){
                    indiv[i][j] = 1;
                }
            }
        }
        return indiv;
    }
    static void printvec(int[][] indiv, double[] fit_value){
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<11;j++){
                System.out.print(" "+indiv[i][j]);
            }
            System.out.println(" "+fit_value[i]);
        }
    }
    public static int[][] GA(int[][] indiv){
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<11;j++){
                indiv[i][j] = 1;
                //ここの中身を書き換えたらGAプログラムの完成
            }
        }
        return indiv;
    }
    
    public static void main(String args[]){
        int indiv[][] = new int[10][11];
        double fit_value[] = new double[10];
        init(indiv);
        fitness(indiv,fit_value);
        printvec(indiv,fit_value);
        for(int i=0;i<indiv.length;i++){
            System.out.println("ただいま"+(i+1)+"回目");
            GA(indiv);
            fitness(indiv, fit_value);
            printvec(indiv,fit_value);
        }
    }
}