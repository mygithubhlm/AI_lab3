package GeneticAlgrithms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import GeneticAlgrithms.FileIO;
public class KS {
//	超参数
//	交叉率
	static double crossRate = 0.8;
//	变异率
	static double mutationRate = 0.01;
//	总重量限制
	static double totalweight = 30;
//	种群数量
	private static int populationSize = 200;
//	进化代数
	private static int generations = 1000;
	// Create and add our 
	public static void main(String[] args){
		//
		FileIO reader = new FileIO();
		ArrayList<Gene> gene = new ArrayList<Gene>();
		 File file = new File("src/testknapsack/ks.txt");
	        List<Object> data = reader.read(file,Type.Knapsack);
	        totalweight = FileIO.weight;
	        double[] weight = (double[])data.get(0);  
	        double[] profit = (double[])data.get(1);  
	        for(int i = 0; i < weight.length; i++) {  
	            Gene g = new Gene(weight[i],profit[i]);
	            gene.add(g);
	        }  
        GA ga = new GA(gene,Type.Knapsack,totalweight,crossRate,mutationRate);
        Population pop = new Population(populationSize, true);

        System.out.printf("Initial profit: %9.3f ", pop.getFitnessIndividual().getFitness());

        System.out.println();    
        // 进化到指定代就停止
        for (int i = 0; i < generations; i++) {
            pop = ga.evolve(pop);
            System.out.println(i + ":" + pop.getFitnessIndividual().getWeight()+" ,   "+pop.getFitnessIndividual().getFitness()+" ,    "+pop.get_avg_fitness());
        }

        // 打印最终的结果
        Individual a = pop.getFitnessIndividual();
        System.out.printf("Final distance: %9.3f", a.getFitness());
        System.out.println();
        
        String fileToWrite = a.getFitness()+"\n";
        for(int k = 0; k<a.getGenes_num(); k++){
        		fileToWrite += (k+1)+" "+boo_int(a.getGene(k).isPhenotype())+"\n";
        		System.out.println((k+1)+":"+a.getGene(k).isPhenotype());	
        }
        System.out.printf("\nweight: "+a.getWeight()+"\tprofit: "+a.getProfit()+"\n");
        reader.writer("src/testknapsack/ks_test.txt", fileToWrite);
	}
//	boolean转int
	public static int boo_int(boolean boo){
		if(boo){
			return 1;
		}else {
			return 0;
		}
	}
}
