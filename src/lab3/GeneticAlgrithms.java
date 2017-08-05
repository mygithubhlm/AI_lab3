package lab3;

//GeneticAlgorithms.java 给定参数，测试遗传算法
import java.io.*;
//2008-11-21
class GeneticAlgorithms{
    public static double crossoverRate;//交叉概率
    public static double mutateRate;//变异概率
    public static int maxGeneration;//进化代数
    public static int populationSize;//群体大小
    
    static {
        //crossoverRate = 0.6;
        //mutateRate = 0.001;
        //maxGeneration  = 100;
        //populationSize = 500;
        maxGeneration  = 100;
        populationSize = 500;
        crossoverRate = 0.6;
        mutateRate = 0.001;
    }
    
//    public static void main(String[] args) throws IOException{
//
//        
//    }
    public static void main(String[] args){
    	FileWriter fw = null;
		try {
			fw = new FileWriter("result.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        Population pop = new Population(populationSize);
        pop.initPopulation();

        pw.println("初始种群:\n" + pop);
        while(!pop.isEvolutionDone()){
            pop.evolve();
            pw.print("第" + pop.getGeneration() + "代Best:" + pop.bestIndividual );
            pw.print("第" + pop.getGeneration()  + "代current:" + pop.currentBest );
            pw.println("");        
        }
        pw.println();
        pw.println("第"+ pop.getGeneration()  + "代群体:\n" + pop);

        pw.close();
    }
    public void print(){

    }
}
