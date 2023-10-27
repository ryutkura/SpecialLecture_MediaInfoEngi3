import java.security.SecureRandom;
import java.io.*;
import java.util.*;

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
        //System.out.println("max="+max);
        System.out.println("min="+min);
        //System.out.println("Ave="+average);
        try{
                FileWriter fw = new FileWriter("result.csv", true); 
                PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

                pw.println(average+","+min+","+max);
                pw.close();

                
            }catch(IOException e){
                System.out.println(e);
            }

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
    public static void GA(int[][] indiv,int[][] memo,int NOI, double[] fit_value,int i,int DIN,int[] elite){
        int eliteno = 0;
        int kuso = 0;
        double min = 9999;
        double min1 = 9999;
        // double max = 0;
        double max1 = 0;
        double minever = 9999;
        int elitever[] = new int[DIN];
        //↓エリートの選別と保管をここで処理する(初回限定番)
        for(int k=0;k < fit_value.length;k++){
            if(min > fit_value[k]){
                min = fit_value[k];
                minever = fit_value[k];
                eliteno = k;
            }
        }
        for(int l=0;l < DIN;l++){
            elite[l] = indiv[eliteno][l];
            elitever[l] = indiv[eliteno][l];
        }
        for(int j=0;j<indiv.length;j++){
            int temp1[] = new int[DIN];
            int temp2[] = new int[DIN];
            Random rand = new SecureRandom();
            int rand1 = rand.nextInt(NOI - 1);
            int rand2 = 0;//rand.nextInt(NOI-1);
            int rand3 = rand.nextInt(DIN-1);
            double rand4 = Math.random();
            do {
                rand2 = rand.nextInt(NOI-1);
            } while (rand1 == rand2); //除外リストのいずれかの数値と一致なら繰り返す
            //↓この選出方法を乱数から何かの作戦に切り替える
            //↑選出方法の処理ここまで
            //↓GAの内部処理
            //↓ここから交叉のプログラム
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
            //↓ここから突然変異のプログラム
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
        //↓エリートの選別と保管をここで処理する
        for(int k=0;k < fit_value.length;k++){
            if(min1 > fit_value[k]){
                min1 = fit_value[k];
                eliteno = k;
            }
            else if(fit_value[k] > max1){
                max1 = fit_value[k];
                kuso = k;
            }
        }
        //↓保管の後の処理
        if((min1 > min) && (min1 <= minever)){
            //上書き処理
            for(int m=0;m < DIN; m++){
                indiv[kuso][m] = elitever[m];
            }
        }
        else if((min1 <= min) && (min1 > minever)){
            // for(int n=0;n < DIN;n++){
            //     elitever[n] = indiv[eliteno][n];
            // }
        }
        else if((min1 > min) && (min1 > minever)){
            //上書き処理
            for(int m=0;m < DIN; m++){
                indiv[kuso][m] = elitever[m];
            }
        }
        else if((min1 <= min) && (min1 <= minever)){
            for(int n=0;n < DIN;n++){
                elitever[n] = indiv[eliteno][n];
            }
        }
        for(int l=0;l < DIN;l++){
            indiv[eliteno][l] = elite[l];
            // System.out.println(elite[l]);
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
        int Gene = 2000;//世代数
        int DIN = 20;//桁数
        int indiv[][] = new int[NOI][DIN];//[個体数][桁数]でそれぞれここだけ変えても動くはず
        double fit_value[] = new double[NOI];
        int elite[] = new int[DIN];
        int memo[][] = new int[NOI][3];
        init(indiv);
        fitness(indiv,fit_value);
        printinit(indiv,fit_value);
        for(int i=0;i<Gene;i++){
            GA(indiv,memo,NOI,fit_value,i,DIN,elite);
        }
    }
}