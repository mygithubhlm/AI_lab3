package knapsack;

import java.util.ArrayList;

import GeneticAlgrithms.Individual;
import simpleGa.FitnessCalc;

public class Population {
	private ArrayList<Individual> individuals;
	private int sizeOfIndi;
	private double[] fitnessScale;
	public double totalfitness = 0;

	public Population(int sizeOfIndi,boolean initialize){
		this.sizeOfIndi = sizeOfIndi;
		individuals = new ArrayList<Individual>();
		//initialize the individuals
		for(int k = 0; k<sizeOfIndi; k++){
			individuals.add(null);
		}
		if(initialize){
			for(int i=0;i<sizeOfIndi;i++){

				ArrayList<Gene> chro = create_individual();
				Individual individual = new Individual(chro);
				set_Individual(i,individual);
			}
			for(int i =0;i<individuals.size();i++){
				for(int k = 0; k<individuals.get(i).getGenes_num(); k++){
					System.out.print(individuals.get(i).getGene(k).isPhenotype()+",");
				}
				System.out.println();
			}

		}else{
			for (int i = 0; i < sizeOfIndi; i++) {
				//				individuals.add(null);
			}
		}
	}
	//
	ArrayList<Gene> create_individual(){
		ArrayList<Gene> chromesome = new ArrayList<Gene>();
		for (int i = 0; i < GA.genes.size(); i++) {
			chromesome.add(GA.genes.get(i));
			double ran = Math.random();
			if(ran<=0.5){
				chromesome.get(i).setPhenotype(true);
			}else{
				chromesome.get(i).setPhenotype(false);
			}
		}
		return chromesome;
	}
	//get individual with index i
	public Individual get_individual(int i){
		return individuals.get(i);
	}
	//set individual at index i
	public void set_Individual(int i, Individual individual){
		//System.out.println(i+" : indi :");
		individuals.set(i, individual);
	}
	//get the Individual with the best fitness
	public Individual getFitnessIndividual(){
		Individual fittest = individuals.get(0);
		//		System.out.println(individuals.get(0).getGenes_num());
		for(int i=0; i<individuals.size();i++){
			if (fittest.getFitness()<individuals.get(i).getFitness()) {
				fittest = individuals.get(i);
			}
		}
		return fittest;
	}
	//calculate tatal fitness
	public double getTotalFitness(){
		double result = 0;
		for (int i = 0; i < individuals.size(); i++) {
			result += individuals.get(i).getFitness();
		}
		return result;
	}
	//calculate fitness scale
	public double[] getFitnessScale(){
		double totalFitness = getTotalFitness();
		fitnessScale  = new double[sizeOfIndi];
		for(int i=0;i<sizeOfIndi;i++){
			double a = individuals.get(i).getFitness();
			fitnessScale[i] = a /totalFitness;
		}
		return fitnessScale;
	}
	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}
	public void setIndividuals(ArrayList<Individual> individuals) {
		this.individuals = individuals;
	}
	public int getSizeOfIndi() {
		return sizeOfIndi;
	}
	public void setSizeOfIndi(int sizeOfIndi) {
		this.sizeOfIndi = sizeOfIndi;
	}
}
