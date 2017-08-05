package lab3;

//Population.java
class Population{
    private int generation; //种群的代数
    private int size;            //群体大小
    private Individual[] pop;   //种群
    private double averageFitness;    //平均适应度
    private double[] relativeFitness;    //相对适应度
    private int chromlen;//基因长度
    Individual bestIndividual;//当前代适应度最好的个体
    Individual worstIndividual;//当前代适应度最差的个体
    Individual currentBest;//到目前代为止最好的个体
    private int worstIndex;//bestIndividual对应的数组下标

    
    public Population(int size){
        this.generation = 0;
        this.size = size;
        
        this.pop = new Individual[size];
        this.averageFitness = 0;
        this.relativeFitness = new double[size];
        this.chromlen = 20;
        
        for(int i = 0; i < size; i++){
            pop[i] = new RosenbrockIndividual(chromlen);
        }
    }
    
    
    //初始化种群
    public void initPopulation(){
        for(int i = 0;i < size;i++){
            pop[i].generateIndividual();
        }
        
        findBestAndWorstIndividual();                
    }

    //----------------------------------------------------
    //比例选择
    public void  select(){
        double[] rouletteWheel; //赌盘
        Individual[] childPop = new Individual[size];
        
        calRelativeFitness();
        
        //产生赌盘
        rouletteWheel  = new double[size];
        rouletteWheel[0] = relativeFitness[0];
        for(int i=1;i<size -1;i++){
            rouletteWheel[i] = relativeFitness[i] + rouletteWheel[i - 1];
        }
        rouletteWheel[size - 1] = 1;
        
        //进行赌盘选择,产生新种群
        for(int i = 0;i < size ; i++){
            double rnd = rand();
            for(int j = 0; j < size; j++){
                if(rnd < rouletteWheel[j]){
                    childPop[i] = pop[j];
                    break;
                }    
            }         
        }
        
        for(int i = 0;i < size; i++){
            pop[i] = childPop[i];
        }
        
        //return     childPop;
    }
    
    //求总适应度
    private double calTotalFitness(){
        double total = 0;
        for(int i = 0 ; i < size ;i++){
        	//System.out.println((pop[i]==null)+": "+i);
            total += pop[i].getFitness();
        }
        return total;
    }
        
    //计算相对适应度
    public double[] calRelativeFitness(){
        double totalFitness = calTotalFitness();
        for(int i = 0 ;i < size ; i++){
            relativeFitness[i] = pop[i].getFitness() / totalFitness;    
        }
        
        return relativeFitness;
    }
    
    //================================
    
    //------------------------------------------------------
    //单点交叉
    public void crossover(){
        for(int i = 0 ; i < size/2*2; i+=2){
            int rnd;
            //随机两两配对
            rnd = rand(i , size);

            if(rnd != i)
                exchange(pop , i , rnd);
                
            rnd = rand(i , size);
            if(rnd != i+1)
                exchange(pop , i + 1 , rnd);    
                    
            //交叉
            double random = rand();

            if(random < GeneticAlgorithms.crossoverRate){
                cross(i);
            }            
        }
    }
    
    //执行交叉操作
    private void cross(int i){
        String chromFragment1,chromFragment2;//基因片段
        
        int rnd = rand(0 , getChromlen() - 1);//交叉点为rnd之后,可能的位置有chromlen - 1个.
        chromFragment1 = pop[i].getChrom(rnd + 1 , getChromlen() - 1);
        chromFragment2 = pop[i+1].getChrom(rnd + 1 , getChromlen() - 1);
            
        pop[i].setChrom(rnd + 1 , getChromlen() - 1 , chromFragment2);
        pop[i+1].setChrom(rnd + 1 , getChromlen() - 1 , chromFragment1);            
    }
    
    //产生随机数
    private int rand(int start , int end){//产生区间为[start , end)的随机整数
        return (int)(rand()*(end - start) + start);
    }
    
    //交换
    private void exchange(Individual[] p ,int src , int dest){
        Individual temp;
        temp = p[src];
        p[src] = p[dest];
        p[dest] = temp;    
    }
    //==============================

    //-----------------------------------------------------
    //变异
    public void mutate(){
        for(int i = 0 ; i < size;i++){
            for(int j = 0 ;j < getChromlen(); j++){
                if(rand() < GeneticAlgorithms.mutateRate){
                    pop[i].mutateSingleBit(j);
                    ///System.out.print("变异"+ i +" - "+ j + "  ");///
                }    
            }
        }
    }
    //==============================
    
    //-----------------------------------------------------
    //进化
    public void evolve(){
        select();
        crossover();
        mutate();
        evaluate();  
    }
    
    
    //==============================
    //计算目标函数值、适应度、找出最优个体。
    public void evaluate(){
        //同步目标函数值和适应度
        for(int i = 0; i < size; i++){
            pop[i].calTargetValue();
            pop[i].calFitness();
        }
        
        //使用最优保存策略(Elitist Model)保存最优个体
        findBestAndWorstIndividual();
        System.out.println("Clone1");
        
        pop[worstIndex] = (Individual)currentBest.clone();
        
        generation++;    
    }    
    //找出适应度最大的个体
    public void findBestAndWorstIndividual(){
        bestIndividual = worstIndividual = pop[0];
        for(int i = 1; i <size;i++){
            if(pop[i].fitness > bestIndividual.fitness){
                bestIndividual = pop[i];
            }
            if(pop[i].fitness < worstIndividual.fitness){
                worstIndividual = pop[i];
                worstIndex = i;
            }
        }
    
        if( generation == 0 ){//初始种群
            currentBest = (Individual)bestIndividual.clone();
        }else{
            if(bestIndividual.fitness > currentBest.fitness)
                currentBest = (Individual)bestIndividual.clone();
        }
    }
    
    //判断进化是否完成
    public boolean isEvolutionDone(){
        if(generation < GeneticAlgorithms.maxGeneration)
            return false;
        return true;    
    }
        
    //计算平均适应度
    public double calAverageFitness(){
        for(int i = 0 ; i < size; i++){
            averageFitness += pop[i].getFitness();
        }
        averageFitness/=size;
            
        return averageFitness;
    } 
                
    //产生随机数
    private double rand(){
        return Math.random();
    }
    
    public int getChromlen(){
        return chromlen;
    }
    
    public void setGeneration(int generation){
        this.generation = generation;
    }
    
    public int getGeneration(){
        return generation;
    }
    /*
    public String printPop(Individual[] pop){
        String str="";
        for(int i = 0 ; i < size ; i++)
            str += pop[i];
        return str;
    }*/
    
    public String toString(){
        String str="";
        for(int i = 0 ; i < size ; i++)
            str += pop[i];
        return str;
    }    
}