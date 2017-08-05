package knap;

import java.util.ArrayList;

import simpleGa.FitnessCalc;

public class Population {
	private Individual[] individuals;
	private int sizeOfIndi;
	private double[] fitnessScale;
	public double totalfitness = 0;

	public Population(int sizeOfIndi,boolean initialize){
		this.sizeOfIndi = sizeOfIndi;
		individuals = new Individual[sizeOfIndi];
		//initialize the individuals
		if(initialize){
			for(int i=0;i<sizeOfIndi;i++){
//				ArrayList<Gene> chro = create_individual();
				Individual individual = new Individual();
				individual = individual.create_a_individual();
//				System.out.println(individual.getGenes_num());
				set_Individual(i,individual);
			}
		}else{
			for (int i = 0; i < sizeOfIndi; i++) {
//								individuals.add(null);
			}
		}
	}
	//get individual with index i
	public Individual get_individual(int i){
		return individuals[i];
	}
	//set individual at index i
	public void set_Individual(int i, Individual individual){
		//System.out.println(i+" : indi :");
		individuals[i]=individual;
//		System.out.println("Fit:"+individual.getFitness());
	}
	//get the Individual with the best fitness
	public Individual getFitnessIndividual(){
		Individual fittest = new Individual();
//		if (individuals[0]!=null) {
//			fittest = individuals[0];
//		}
//		Individual fittest = individuals[0];
		//		System.out.println(individuals.get(0).getGenes_num());
		for(int i=0; i<individuals.length;i++){
//			if (individuals[i]!=null) {
//				System.out.println(fittest.getFitness());
//				System.out.println(individuals[i].getFitness());
			if (fittest.getFitness()<individuals[i].getFitness()) {
				fittest = individuals[i];
			}
//			}
		}
		return fittest;
	}
	//calculate tatal fitness
	public double getTotalFitness(){
		double result = 0;
		for (int i = 0; i < individuals.length; i++) {
//			System.out.print(i+",");
//			if(individuals[i]!=null){
				result += individuals[i].getFitness();	
//			}
			
		}
//		System.out.println();
		return result;
	}
	//calculate fitness scale
	public double[] getFitnessScale(){
		double totalFitness = getTotalFitness();
		fitnessScale  = new double[sizeOfIndi];
		for(int i=0;i<individuals.length;i++){
//			if (individuals[i]!=null) {
				
			
//			System.out.println(individuals[i]==null);
			double a = individuals[i].getFitness();
			fitnessScale[i] = a /totalFitness;
//			}
		}
		return fitnessScale;
	}
	public Individual[] getIndividuals() {
		return individuals;
	}
	public void setIndividuals(Individual[] individuals) {
		this.individuals = individuals;
	}
	public int getSizeOfIndi() {
		return sizeOfIndi;
	}
	public void setSizeOfIndi(int sizeOfIndi) {
		this.sizeOfIndi = sizeOfIndi;
	}
}
