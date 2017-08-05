package GeneticAlgrithms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TSP {
//	超参数
//	交叉率
	static double crossRate = 0.9;
//	变异率
	static double mutationRate = 0.02;
//	种群数量
	private static int populationSize = 150;
//	进化代数
	private static int generations = 20000;
    public static void main(String[] args) {
    	 File file = new File("src/testtsp/tsp.txt");  
         FileIO reader = new FileIO();
         List<Object> data = reader.read(file,Type.TSP);
         ArrayList<Gene> gene = new ArrayList<Gene>();
         int[] index = (int[])data.get(0);
         double[] x = (double[])data.get(1);  
         double[] y = (double[])data.get(2); 
         for(int i = 0; i < index.length; i++) {  
             Gene g = new Gene(index[i],x[i],y[i]);
             gene.add(g);
         }  

        // Initialize population
        GA ga = new GA(gene,Type.TSP,crossRate,mutationRate);
        Population pop = new Population(populationSize, true);
        System.out.printf("First distance: ", pop.getFitnessIndividual().getDistance());
        System.out.println();    
        // 进化到指定代就停止
        for (int i = 0; i < generations; i++) {
            pop = ga.evolve(pop);
            System.out.println(i + " :  " + pop.getFitnessIndividual().getDistance()+" ,   "+pop.get_avg_distance());
        }
        Individual a = pop.getFitnessIndividual();
        System.out.printf("Final distance: ", a.getDistance());
        System.out.println();
        
        String fileToWrite = a.getDistance()+"\n";
        for(int k = 0; k<a.getGenes_num(); k++){
        	fileToWrite += a.getGene(k).getPos()+" "+a.getGene(k).getX()+" "+a.getGene(k).getY()+"\n";
        	System.out.println(a.getGene(k).getPos()+" : "+a.getGene(k).getX()+" : "+a.getGene(k).getY());	
        }
        reader.writer("src/testtsp/TSP-3[14302010044].txt", fileToWrite);
        
    }
}