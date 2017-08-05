package knap;
import java.util.ArrayList;

import javax.naming.InitialContext;

public class GA {
	public static final int evolveGenerations = 200;
	private double crossRate = 0.2;
	private double mutationRate = 0.05;
	public static ArrayList<Gene> genes;
	public static double weightlimit;
	public static Type type;


	//evolve
	public GA(ArrayList<Gene> datas,Type type,double crossRate,double mutationRate){
		GA.genes = datas;
		GA.type = type;
		this.crossRate = crossRate;
		this.mutationRate = mutationRate;
		GA.weightlimit = 10000000000000.0;
	}
	public GA(ArrayList<Gene> datas,Type type,double totalWeight,double crossRate,double mutationRate){
		GA.genes = datas;
		GA.type = type;
		this.crossRate = crossRate;
		this.mutationRate = mutationRate;
		GA.weightlimit = totalWeight;
	}

	public Population evolve(Population population){
		Population newPopulation = new Population(population.getSizeOfIndi(), false);
		newPopulation.set_Individual(0, population.getFitnessIndividual());
		//crossover
		for (int i = 1; i < population.getSizeOfIndi(); i++) {
			//select
			Individual parent1 = select(population);
			Individual parent2 = select(population);
			Individual child = crossover_0(parent1,parent2);

			mutate_0(child);
			newPopulation.set_Individual(i, child);
		}
		return newPopulation;

	}
	// select two Individual each time to genetate a child 
	public Individual select(Population p){
		Individual selected = null;
		int size  = p.getSizeOfIndi();
		//赌盘 
		double[] wheel = new double[size];
		double[] fitnessScale = p.getFitnessScale();
		wheel[0] = fitnessScale[0];
		for (int i = 1; i < size-1; i++) {
			wheel[i] = fitnessScale[i] + wheel[i-1];
		}
		wheel[size-1] = 1;
		double ran = Math.random();
		for (int i = 0; i < size; i++) {
			if (ran<wheel[i]) {
				selected = p.get_individual(i);
				break;
			}
		}
		return selected;
	}
	//crossover
	public Individual crossover(Individual parent1, Individual parent2){
		// Create new child
		Individual child = new Individual();
		//		child.create_a_individual();
		child.init_null();
		int length = parent1.getGenes_num();
		int begin = (int) (Math.random() * parent1.getGenes_num());
		double r = Math.random();
		if(r<crossRate){
			for (int i = 0; i < parent1.getGenes_num(); i++) {
				if (i<begin) {
					child.setGene(i, parent1.getGene(i));
				}else{
					break;
				}
			}
			for (int i = 0; i < parent2.getGenes_num(); i++) {
				if (!child.contains(parent2.getGene(i))) {
					for (int ii = 0; ii < child.getGenes_num(); ii++) {
						if (child.getGene(ii)==null) {
							child.setGene(ii, parent2.getGene(i));
						}
					}
				}
			}
		}else{
			for (int i = 0; i < parent1.getGenes_num(); i++) {
				child.setGene(i, parent1.getGene(i));
			} 
		}
		return child;
	}
	public Individual crossover_0(Individual parent1,Individual parent2){
		// Create new child
		Individual child = new Individual();
		child.create_a_individual();
		int length = parent1.getGenes_num();
		//		System.out.println(length);
		for (int i = 0; i < length; i++) {
			//			System.out.println(i+""+(child.getGene(i)==null));
			child.getGene(i).setPhenotype(false);
		}

		// startPos endPos之间的序列，会被遗传到下一代。 (如果startPos<endPos,就是取反)
		int point = (int) (Math.random() * parent1.getGenes_num());

		// Loop and add the sub tour from parent1 to our child
		double r = Math.random();
		if(r<=crossRate){
			for (int i = 0; i < length; i++) {
				// If our start position is less than the end position
				if (i<point) {
					if (child.getWeight()+parent1.getGene(i).getX()<weightlimit) {
						child.getGene(i).setPhenotype(parent1.getGene(i).isPhenotype());
					}	
				} // If our start position is larger
				else{
					if (child.getWeight()+parent2.getGene(i).getX()<weightlimit) {
						child.getGene(i).setPhenotype(parent2.getGene(i).isPhenotype());
					}	

				}
			}
		}else {
			for (int i = 0; i < parent1.getGenes_num(); i++) {
				child.getGene(i).setPhenotype(parent1.getGene(i).isPhenotype());
			}
		}
		//		System.out.println("weight:"+child.getWeight());
		return child;
	}
	//mutate
	public void mutate(Individual child){
		// Loop through tour cities
		for(int tourPos1=0; tourPos1 < child.getGenes_num() ; tourPos1++){
			// Apply mutation rate
			if(Math.random() < mutationRate){
				// Get a second random position in the tour
				int tourPos2 = (int) (child.getGenes_num() * Math.random());
				// Get the cities at target position in tour
				Gene g1 = child.getGene(tourPos1);
				Gene g2 = child.getGene(tourPos2);

				// Swap them around
				child.setGene(tourPos2, g1);
				child.setGene(tourPos1, g2);
			}
		}
	}
	public void mutate_0(Individual child) {
		for (int i = 0; i < child.getGenes_num(); i++) {
			if(Math.random() < mutationRate){
				// Get a random position 
				int pos = (int) (child.getGenes_num() * Math.random());
				// Swap them around
				child.getGene(pos).setPhenotype(!child.getGene(pos).isPhenotype());
				if (child.getWeight()>weightlimit) {
					child.getGene(pos).setPhenotype(false);
				}
			}
		}
	}
}
