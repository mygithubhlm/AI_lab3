package lab3;

//RosenbrockIndividual.java
class RosenbrockIndividual extends Individual {
    private double x1 , x2; // 个体表现型
    //基因型chromosome由 (x1 , x2)编码而成
    
    RosenbrockIndividual(int chromlen){
        genelen = 10;
        chrom = new Chromosome(chromlen);
    }
    
    //编码
    public void coding(){
        String code1,code2;
        code1 = codingVariable(x1);
        code2 = codingVariable(x2);
        
        chrom.setGene(0 , 9 , code1);
        chrom.setGene(10, 19 , code2);
    }
    
    //解码
    public void decode(){
        String gene1,gene2;
        
        gene1 = chrom.getGene(0 , 9);
        gene2 = chrom.getGene(10 , 19);
        
        x1 = decodeGene(gene1);
        x2 = decodeGene(gene2);
    }
    
    //计算目标函数值
    public  void calTargetValue(){
        decode();
        targetValue = rosenbrock(x1 , x2);
    }
    
    //计算个体适应度
    public void calFitness(){
        fitness = getTargetValue();
    }
    
    private String codingVariable(double x){
        double y = (((x + 2.048) * 1023) / 4.096);
        String code = Integer.toBinaryString((int) y);
        
        StringBuffer codeBuf = new StringBuffer(code);
        for(int i = code.length(); i<genelen; i++)
            codeBuf.insert(0,'0');
            
        return codeBuf.toString();
    }
    
    private double decodeGene(String gene){
        int value ;
        double decode;
        value = Integer.parseInt(gene, 2);
        decode = value/1023.0*4.096 - 2.048;
        
        return decode;
    }
        
    public String toString(){
        String str = "";
        ///str = "基因型:" + chrom + "  ";
        ///str+= "表现型:" + "[x1,x2]=" + "[" + x1 + "," + x2 + "]" + "\t";
        str+="函数值:" + rosenbrock(x1 , x2) + "\n";
        
        return     str;    
    }
    
    /**
     *Rosenbrock函数:
     *f(x1,x2) = 100*(x1**2 - x2)**2 + (1 - x1)**2
     *在当x在[-2.048 , 2.048]内时，
     *函数有两个极大点:
     *f(2.048 , -2.048) = 3897.7342
     *f(-2.048,-2.048) = 3905.926227
     *其中后者为全局最大点。
     */
    public static double rosenbrock(double x1 , double x2){
        double fun;
        fun = 100*Math.pow((x1*x1 - x2) , 2) + Math.pow((1 - x1) , 2);
        
        return fun;
    }
    
    //随机产生个体
    public void generateIndividual(){
        x1 = Math.random() * 4.096 - 2.048;
        x2 = Math.random() * 4.096 - 2.048;
        
        //同步编码和适应度
        coding();
        calTargetValue();
        calFitness();
    }
}
