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
        int N = 25;
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
        for(int af=0;af<2;af++){
        Flv[5] = af;
        for(int ag=0;ag<2;ag++){
        Flv[6] = ag;
        for(int ah=0;ah<2;ah++){
        Flv[7] = ah;
        for(int ai=0;ai<2;ai++){
        Flv[8] = ai;
        for(int aj=0;aj<2;aj++){
        Flv[9] = aj;
        for(int ak=0;ak<2;ak++){
        Flv[10] = ak;
        for(int al=0;al<2;al++){
        Flv[11] = al;
        for(int am=0;am<2;am++){
        Flv[12] = am;
        for(int an=0;an<2;an++){
        Flv[13] = an;
        for(int ao=0;ao<2;ao++){
        Flv[14] = ao;
        for(int ap=0;ap<2;ap++){
        Flv[15] = ap;
        for(int aq=0;aq<2;aq++){
        Flv[16] = aq;
        for(int ar=0;ar<2;ar++){
        Flv[17] = ar;
        for(int as=0;as<2;as++){
        Flv[18] = as;
        for(int at=0;at<2;at++){
        Flv[19] = at;                                                                                                    
        for(int au=0;au<2;au++){
        Flv[20] = au;
        for(int av=0;av<2;av++){
        Flv[21] = av;
        for(int aw=0;aw<2;aw++){
        Flv[22] = aw;
        for(int ax=0;ax<2;ax++){
        Flv[23] = ax;
        for(int ay=0;ay<2;ay++){
        Flv[24] = ay;
        System.out.println("Floyd–â‘è‚Ì¡‰ñ‚Ì‰ñ“š‚Í"+calcflo(Flv, N)+"‚Å‚·B");
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        long endTime = System.nanoTime();
        System.out.println("ˆ—ŽžŠÔF" + (endTime - startTime) + " ƒiƒm•b");
    }
}