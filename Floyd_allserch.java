class Floyd_allserch{
    public static double calcflo(int[] Flv){
        double A = 0;
        double B = 0;
        double ab = 1;
        for(int i=0;i<3;i++){
            if(Flv[i] == 1){
                A = A + Math.sqrt(i+1);
                System.out.println("A�̍��v�l��"+A+"�ł��B");
            }
            else{
                B = B + Math.sqrt(i+1);
                System.out.println("B�̍��v�l��"+B+"�ł��B");
            }
        }
        ab = A - B;
        return Math.abs(ab);
        // System.out.println("�����̐�Βl��"+Math.abs(ab)+"�ł��B");
    }

    public static void main(String args[]){
        int[] Flv = new int[3];
        //Flv = new int[]{0,1,1,0,0};
        for(int i=0;i<2;i++){
            Flv[0] = i;
            // System.out.println("Flv["+i+"]��"+Flv[i]+"�ł��B");
            for(int j=0;j<2;j++){
                Flv[1] = j;
                // System.out.println("Flv["+j+"]��"+Flv[j]+"�ł��B");
                for(int k=0;k<2;k++){
                    Flv[2] = k;
                    // System.out.println("Flv["+k+"]��"+Flv[k]+"�ł��B");
                    System.out.println("Floyd���̍���̉񓚂�"+calcflo(Flv)+"�ł��B");
                }
            }
        }

    }
}