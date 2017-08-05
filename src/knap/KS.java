package knap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KS {
	public static ArrayList<Gene> destinationCities = new ArrayList<Gene>();
	static double crossRate = 0.9;
	static double mutationRate = 0.02;
	static double totalweight = 30;
	private static int populationSize = 80;
	private static int generations = 3000;
	// Create and add our 
	public static void main(String[] args){
		//
		FileIO reader = new FileIO();
		ArrayList<Gene> gene = new ArrayList<Gene>();
		 File file = new File("src/testknapsack/Knapsack3.txt");
	        List<Object> data = reader.read(file,Type.Knapsack);
	        totalweight = FileIO.weight;
	        double[] weight = (double[])data.get(0);  
	        double[] profit = (double[])data.get(1);  
	        for(int i = 0; i < weight.length; i++) {  
	            System.out.print(weight[i] + "\t"); 
	            Gene g = new Gene(weight[i],profit[i]);
	            gene.add(g);
	        }  
	        System.out.println();  
	        for(int i = 0; i < profit.length; i++) {  
	            System.out.print(profit[i] + "\t");  
	        }
	        System.out.println();
		
        GA ga = new GA(gene,Type.Knapsack,totalweight,crossRate,mutationRate);
        Population pop = new Population(populationSize, true);

        System.out.printf("Initial profit: %9.3f ", pop.getFitnessIndividual().getFitness());

        System.out.println();    
        // 进化到50代就停止程序
        for (int i = 0; i < generations; i++) {
            pop = ga.evolve(pop);
            System.out.println(i+" : "+pop.getFitnessIndividual().getWeight()+","+pop.getFitnessIndividual().getFitness());
        }

        // 打印最终的结果
        Individual a = pop.getFitnessIndividual();
        System.out.printf("Final distance: %9.3f", a.getFitness());
        System.out.println();
        System.out.println("Solution:");
        
        String fileToWrite = a.getFitness()+"\n";
        double t = 0;
        for(int k = 0; k<a.getGenes_num(); k++){
        		fileToWrite += (k+1)+" "+boo_int(a.getGene(k).isPhenotype())+"\n";
        		if (a.getGene(k).isPhenotype()) {
					t+=a.getGene(k).getX();
				}
//        		System.out.println((k+1)+":"+a.getGene(k).isPhenotype());	
        }
        System.out.println(t);
        System.out.printf("\nweight: "+a.getWeight()+"\tprofit: "+a.getProfit()+"\n");
        reader.writer("src/testknapsack/Knapsack-3[14302010044].txt", fileToWrite);
	}
	public static int boo_int(boolean boo){
		if(boo){
			return 1;
		}else {
			return 0;
		}
	}
}
