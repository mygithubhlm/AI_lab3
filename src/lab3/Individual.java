package lab3;

//Individual.java
abstract class Individual implements Cloneable{
    protected Chromosome chrom;//个体基因型:一个基因型染色体由多个基因组成
    protected int genelen;//基因长度
    protected double fitness;//适应度
    protected double targetValue;//目标函数值
    
    public abstract void coding();//编码
    public abstract void decode();//解码
    public abstract void calFitness();//计算个体适应度
    public abstract void generateIndividual();//随机产生个体
    public abstract void calTargetValue();//获取目标函数值 
    
    public double getFitness(){
        return fitness;
    }
    
    public double getTargetValue(){
        return targetValue;
    }
    
    public int getChromlen(){
        return chrom.getLength();
    }
    
    public boolean setChrom(int begin , int end , String gene){
        return chrom.setGene(begin,end,gene);
    }
    
    public String getChrom(int begin , int end){
        return chrom.getGene(begin,end);
    }
    
    public void mutateSingleBit(int index){
        String gene , gn;
        gene = chrom.getGene(index , index);
        gn = gene.equals("0") ? "1":"0";
        chrom.setGene(index , index , gn);
    }
    
    @Override
    public Object clone(){
        Individual indv = null;
        
        try{
            indv = (Individual)super.clone();
            indv.chrom = (Chromosome)chrom.clone();
            System.out.println("clone!");
        }catch(CloneNotSupportedException e ){
            System.out.println(e.getMessage());
        }
        
        return indv;
    }
}