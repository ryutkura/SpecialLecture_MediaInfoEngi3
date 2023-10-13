import java.security.SecureRandom;
import java.util.Random;
import java.util.Arrays;

class Floyd_GA{
    public static void fitness(int[][] indiv,double[] fit_value){
        double ans = 0;
        double A = 0;
        double B = 0;
        double ab = 1;

        for(int i=0;i<indiv.length;i++){
            A = 0;
            B = 0;
            for(int j=0;j<indiv[i].length;j++){
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
            for(int j=0;j<indiv[i].length;j++){
                double alpha = Math.random();
                if(alpha>0.5){
                    indiv[i][j] = 1;
                }
                else{
                    indiv[i][j] = 0;
                }
            }
        }
        return indiv;
    }
    static void printinit(int[][] indiv, double[] fit_value){
        for(int i=0;i<indiv.length;i++){
            for(int j=0;j<indiv[i].length;j++){
                System.out.print(" "+indiv[i][j]);
            }
            System.out.println("："+fit_value[i]);
        }
        System.out.println("---------------------------------");
    }
    static void printNote(double[] fit_value, int i){
        double average = 0;
        double max = 0;
        double min = 0;
        average = Arrays.stream(fit_value).average().getAsDouble();
        max = fit_value[0];
        min = fit_value[0];
        System.out.println("Note：Generation" + (i+1));
        for(int j=1;j < fit_value.length;j++){
            if(max < fit_value[j]){
                max = fit_value[j];
            }
            else if(min > fit_value[j]){
                min = fit_value[j];
            }
        }
        System.out.println("max="+max);
        System.out.println("min="+min);
        System.out.println("Ave="+average);

    }
    static void printGA(int[][] indiv, double[] fit_value,int[][] memo){
        
        for(int i=0;i<indiv.length;i++){
            System.out.print("("+memo[i][0]+","+memo[i][1]+") "+memo[i][2]+"：");
            for(int j=0;j<indiv[i].length;j++){
                System.out.print(" "+indiv[i][j]);
            }
            System.out.println("："+fit_value[i]);
        }
        System.out.println("---------------------------------");
    }
    public static void GA(int[][] indiv,int[][] memo,int NOI, double[] fit_value,int i,int DIN){
        for(int j=0;j<indiv.length;j++){
            int temp1[] = new int[DIN];
            int temp2[] = new int[DIN];
            Random rand = new SecureRandom();
            int rand1 = rand.nextInt(NOI - 1);
            int rand2 = rand.nextInt(NOI-1);
            int rand3 = rand.nextInt(DIN-1);
            double rand4 = Math.random();
            if(rand1 == rand2){
                rand2 = rand.nextInt(NOI-1);
            }
            //↓GAの内部処理
            for(int s=0;s<DIN;s++){
                if(s<=rand3){
                    temp1[s] = indiv[rand1][s];
                }
                else if(rand3<s){
                    temp1[s] = indiv[rand2][s];
                }
            }
            for(int s=0;s<DIN;s++){
                if(s<=rand3){
                    temp2[s] = indiv[rand2][s];
                }
                else if(rand3<s){
                    temp2[s] = indiv[rand1][s];
                }
            }
            for(int s=0;s<DIN;s++){
                    indiv[rand1][s] = temp1[s];
            }
            for(int s=0;s<DIN;s++){
                    indiv[rand2][s] = temp2[s];
            }
            //↑ここまで交叉のプログラム
            if(rand4>0.95){
                if(indiv[rand1][rand3] == 1){
                    indiv[rand1][rand3] = 0;
                }
                else if(indiv[rand1][rand3] == 0){
                    indiv[rand1][rand3] = 1;
                }
            }
            //↑ここまでが突然変異のプログラム
            //↑GAの内部処理
            memo[j][0] = rand1;
            memo[j][1] = rand2;
            memo[j][2] = rand3;
        }
        for(int k=0;k<fit_value.length;k++){
            fit_value[k] = 0;
        }
        fitness(indiv,fit_value);
        printGA(indiv, fit_value,memo);
        printNote(fit_value,i);
    }
    
    public static void main(String args[]){
        int NOI = 10;//個体数
        int Gene = 1;//世代数
        int DIN = 11;//桁数
        int indiv[][] = new int[NOI][DIN];//[個体数][桁数]でそれぞれここだけ変えても動くはず
        double fit_value[] = new double[NOI];
        int memo[][] = new int[NOI][3];
        init(indiv);
        fitness(indiv,fit_value);
        printinit(indiv,fit_value);
        for(int i=0;i<Gene;i++){
            GA(indiv,memo,NOI,fit_value,i,DIN);
        }
    }
}