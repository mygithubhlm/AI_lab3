package GeneticAlgrithms;

import java.util.ArrayList;

public class GA {
	public static final int evolveGenerations = 200;
	private double crossRate = 0.8;
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

//	进化
	public Population evolve(Population population){
		Population newPopulation = new Population(population.getSizeOfIndi(), false);
		newPopulation.set_Individual(0, population.getFitnessIndividual());

		for (int i = 1; i < population.getSizeOfIndi(); i++) {
			//select
			Individual parent1 = select(population);
			Individual parent2 = select(population);
			//crossover
			Individual child;
			if (type==Type.TSP) {
				child = crossover(parent1,parent2);
			}else//当类型为背包问题时
				child = crossover_0(parent1,parent2);

			//mutate
			if (type==Type.TSP) {
				mutate(child);
			}else
				mutate_0(child);

			newPopulation.set_Individual(i, child);
		}
		//		if (newPopulation.getFitnessIndividual().getFitness()>population.getFitnessIndividual().getFitness()) {
		return newPopulation;
	}
	// select two Individual each time to genetate a child 
	public Individual select(Population p){
		Individual selected = null;
		int size  = p.getSizeOfIndi();
		//制作赌盘
		double[] wheel = new double[size];
		double[] fitnessScale = new double[size];
		if (type == Type.TSP) {
			fitnessScale = p.getFitnessScale(p.getBiggestDistance());
		}else{
			fitnessScale = p.getFitnessScale();
		}

		wheel[0] = fitnessScale[0];
		for (int i = 1; i < size-1; i++) {
			wheel[i] = fitnessScale[i] + wheel[i-1];
		}
		wheel[size-1] = 1;
		double ran = Math.random();
//		用赌盘随机选择
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
		child.init_null();
//		int length = parent1.getGenes_num();
		int begin = (int) (Math.random() * parent1.getGenes_num());
	
		double r = Math.random();
		ArrayList<Gene> child_genes = new ArrayList<Gene>();
//		当产生的概率低于交叉率的时候，进行交叉
		if(r<crossRate){
			for (int i = 0; i < parent1.getGenes_num(); i++) {
				if (i<begin) {
					child_genes.add(parent1.getGene(i));
				}else{
					break;
				}
			}
			for (int i = 0; i < parent2.getGenes_num(); i++) {
				if (!contain(child_genes,parent2.getGene(i))) {
					child_genes.add(parent2.getGene(i));
				}
			}
			//			System.out.println("child_genes:"+child_genes.size());
			for (int i = 0; i < parent1.getGenes_num(); i++) {

				child.addGene(child_genes.get(i));
				//				System.out.print("child:"+child.getGene(i).getPos());
			}
			//			System.out.println();
			child_genes.clear();
		}else{//当概率大于交叉率时，将双亲中的一个遗传给儿子
			for (int i = 0; i < parent1.getGenes_num(); i++) {
				//				System.out.println(parent1.getGene(i)==null);
				child.addGene(parent1.getGene(i));
			} 
		}
		return child;
	}
	//背包
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
				int point = (int) (Math.random() * parent1.getGenes_num());

				double r = Math.random();
//				当产生的概率低于交叉率的时候，进行交叉
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
				}else {//		当产生的概率高于交叉率的时候，将双亲的一个遗传给子代
					for (int i = 0; i < parent1.getGenes_num(); i++) {
						child.getGene(i).setPhenotype(parent1.getGene(i).isPhenotype());
					}
				}
				//		System.out.println("weight:"+child.getWeight());
				return child;
	}
	//mutate
	public void mutate(Individual child){
		// 
		for(int pos1=0; pos1 < child.getGenes_num() ; pos1++){
			// Apply mutation rate
			if(Math.random() < mutationRate){
				// Get a second random position in the tour
				int pos2 = (int) (child.getGenes_num() * Math.random());
				// Get the cities at target position in tour
				Gene g1 = child.getGene(pos1);
				Gene g2 = child.getGene(pos2);
				// Swap them around
				child.setGene(pos2, g1);
				child.setGene(pos1, g2);
			}
		}
	}
	//背包
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
//	gs1中是否包含gs2基因
	public boolean contain(ArrayList<Gene> gs1,Gene gs2){
		for (int j = 0; j < gs1.size(); j++) {
			if (gs1.get(j).getPos()==gs2.getPos()) {
				return true;
			}
		}
		return false;
	}
}
