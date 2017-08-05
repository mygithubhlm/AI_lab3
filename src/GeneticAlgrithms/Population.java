package GeneticAlgrithms;

public class Population {
//	个体
	private Individual[] individuals;
//	个体数
	private int sizeOfIndi;
//	适应度区间
	private double[] fitnessScale;
//	总的适应度
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
				set_Individual(i,individual);
			}
		}
	}
	
	//get individual with index i
	public Individual get_individual(int i){
		return individuals[i];
	}
	//set individual at index i
	public void set_Individual(int i, Individual individual){
		individuals[i]=individual;
	}
	//get the Individual with the best fitness
	public Individual getFitnessIndividual(){
		Individual fittest = individuals[0];
		//		System.out.println(individuals.get(0).getGenes_num());
		for(int i=0; i<individuals.length;i++){
			if (fittest.getFitness()<individuals[i].getFitness()) {
				fittest = individuals[i];
			}
		}
		return fittest;
	}
	public double get_avg_fitness(){
//		Individual fittest = individuals[0];
		double total=0;
		//		System.out.println(individuals.get(0).getGenes_num());
		for(int i=0; i<individuals.length;i++){
			
				total += individuals[i].getFitness();
		}
		return total/individuals.length;
	}
	public double get_avg_distance(){
//		Individual fittest = individuals[0];
		double total=0;
		//		System.out.println(individuals.get(0).getGenes_num());
		for(int i=0; i<individuals.length;i++){
			
				total += individuals[i].getDistance();
		}
		return total/individuals.length;
	}
//	得到最大距离
	public Double getBiggestDistance(){
		Individual fittest = individuals[0];
		//		System.out.println(individuals.get(0).getGenes_num());
		for(int i=0; i<individuals.length;i++){
			if (fittest.getDistance()<individuals[i].getDistance()) {
				fittest = individuals[i];
			}
		}
		return fittest.getDistance();
	}
	//calculate tatal fitness
	public double getTotalFitness(){
		double result = 0;
		for (int i = 0; i < individuals.length; i++) {
			result += individuals[i].getFitness();
		}
		return result;
	}
//	得到总的距离
	public double getTotalDisance(){
		double result = 0;
		for (int i = 0; i < individuals.length; i++) {
			result += individuals[i].getDistance();
		}
		return result;
	}
	//calculate fitness scale
	public double[] getFitnessScale(double bigdistance){
		double total = 0;
		fitnessScale  = new double[sizeOfIndi];
		for(int i=0;i<sizeOfIndi;i++){
			//			double a = individuals[i].getFitness();
			fitnessScale[i] = bigdistance - individuals[i].getDistance();
			total+=fitnessScale[i];
		}
		for(int i=0;i<sizeOfIndi;i++){
			//			double a = individuals[i].getFitness();
			fitnessScale[i] = fitnessScale[i]/total;
		}
		return fitnessScale;
	}
	//calculate fitness scale
	public double[] getFitnessScale(){
		double total = getTotalFitness();
		fitnessScale  = new double[sizeOfIndi];
		for(int i=0;i<sizeOfIndi;i++){
			//				double a = individuals[i].getFitness();
			fitnessScale[i] = individuals[i].getFitness()/total;
			
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
