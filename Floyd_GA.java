class Floyd_GA{
    public static int[] init(int[] indiv){
        for(int i=0;i<indiv.length;i++){
            indiv[i] = 1;
        }
        return indiv;
    }
    static void printvec(int[] indiv){
        for(int i=0;i<indiv.length;i++){
            System.out.println(indiv[i]);
        }
    }
    public static int[] GA(int[] indiv){
        int num=1;
        num = (int)Math.ceil(Math.random() * 10);
        for(int i=0;i<indiv.length;i++){
            indiv[i] = 1*num;
        }
        return indiv;
    }
    
    public static void main(String args[]){
        int indiv[] = new int[10];
        init(indiv);
        for(int i=0;i<indiv.length;i++){
            System.out.println("‚½‚¾‚¢‚Ü"+(i+1)+"‰ñ–Ú");
            GA(indiv);
            printvec(indiv);
        }
    }
}