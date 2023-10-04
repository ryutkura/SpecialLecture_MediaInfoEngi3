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
        int N = 10;
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
        long endTime = System.nanoTime();
        System.out.println("ˆ—ŽžŠÔF" + (endTime - startTime) + " ƒiƒm•b");

                            

    }
}