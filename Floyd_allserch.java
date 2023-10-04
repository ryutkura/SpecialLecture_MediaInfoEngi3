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
        for(int az=0;az<2;az++){
        Flv[25] = az;
        for(int ba=0;ba<2;ba++){
        Flv[26] = ba;
        for(int bb=0;bb<2;bb++){
        Flv[27] = bb;
        for(int bc=0;bc<2;bc++){
        Flv[28] = bc;
        for(int bd=0;bd<2;bd++){
        Flv[29] = bd;
        for(int be=0;be<2;be++){
        Flv[30] = be;
        for(int bf=0;bf<2;bf++){
        Flv[31] = bf;
        for(int bg=0;bg<2;bg++){
        Flv[32] = bg;
        for(int bh=0;bh<2;bh++){
        Flv[33] = bh;
        for(int bi=0;bi<2;bi++){
        Flv[34] = bi;
        for(int bj=0;bj<2;bj++){
        Flv[35] = bj;
        for(int bk=0;bk<2;bk++){
        Flv[36] = bk;
        for(int bl=0;bl<2;bl++){
        Flv[37] = bl;
        for(int bm=0;bm<2;bm++){
        Flv[38] = bm;
        for(int bn=0;bn<2;bn++){
        Flv[39] = bn;
        for(int bo=0;bo<2;bo++){
        Flv[40] = bo;
        for(int bp=0;bp<2;bp++){
        Flv[41] = bp;
        for(int bq=0;bq<2;bq++){
        Flv[42] = bq;
        for(int br=0;br<2;br++){
        Flv[43] = br;
        for(int bs=0;bs<2;bs++){
        Flv[44] = bs;                                                                                                    
        for(int bt=0;bt<2;bt++){
        Flv[45] = bt;
        for(int bu=0;bu<2;bu++){
        Flv[46] = bu;
        for(int bv=0;bv<2;bv++){
        Flv[47] = bv;
        for(int bw=0;bw<2;bw++){
        Flv[48] = bw;
        for(int bx=0;bx<2;bx++){
        Flv[49] = bx;
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