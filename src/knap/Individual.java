package knap;

import java.util.ArrayList;

public class Individual {
	private ArrayList<Gene> chromesome;
	private ArrayList<Gene> genes2 = GA.genes;
	private int genes_num = genes2.size();
	private int fitness  = 0;
	private Type type = GA.type;
	private double weightlimit = GA.weightlimit;
	public static int count=0;
	ArrayList<Gene> all_the_genes = this.getAllGenes();
	private ArrayList<Gene> getAllGenes() {
		// TODO Auto-generated method stub
		all_the_genes = new ArrayList<Gene>();
		for(int i=0;i<genes2.size();i++){
			all_the_genes.add(geneCopy(genes2.get(i)));
		}
		return all_the_genes;
	}
	public Individual(){
		chromesome = new ArrayList<Gene>();
	}

	public Individual(ArrayList<Gene> chromesome){
		this.chromesome = chromesome;
	}
	public Gene geneCopy(Gene g1){
		Gene g2 = new Gene(g1.getPos(),g1.getX(),g1.getY());
		return g2;
	}
	/*create a individual with all the genes*/
	public Individual create_a_individual(){
//		chromesome = new ArrayList<Gene>();
		ArrayList<Gene> allgenes = getAllGenes();
		for (int i = 0; i < allgenes.size(); i++) {
			chromesome.add(allgenes.get(i));
			double ran = Math.random();
			if(ran<=0.3&&getWeight()<weightlimit){	
				chromesome.get(i).setPhenotype(true);
			}else{
				chromesome.get(i).setPhenotype(false);
			}
		}
		return this;
	}
	// full in the chromesome randomly
	public void randomInitial(ArrayList<Gene> allgenes){
		for (int i = 0; i < allgenes.size(); i++) {
			chromesome.add(allgenes.get(i));
			double ran = Math.random();
			if(ran<=0.3&&getWeight()<weightlimit){	
				chromesome.get(i).setPhenotype(true);
			}else{
				chromesome.get(i).setPhenotype(false);
			}
		}
	}
	public void init_null(){
//		chromesome = new ArrayList<Gene>();
		for (int i = 0; i < genes_num; i++) {
			chromesome.add(null);
		}
	}
//	
	public ArrayList<Gene> randomList(ArrayList<Gene> source){
		ArrayList<Gene> random = new ArrayList<Gene>();
		int index =(int)Math.random()*source.size();
		do{
			random.add(source.remove(index));
		}while(source.size()>0);
		return random;	
	}
	//get gene with the index i
	public Gene getGene(int i){
		return chromesome.get(i);
	}
	//set gene at the index i
	public void setGene(int i, Gene g){
		chromesome.set(i, g);
	}
	//add gene 
	public void addGene(Gene g){
		chromesome.add(g);
	}

	//get the fitness
	public double getFitness(){
		if (this.fitness==0) {
			if (this.type == Type.TSP) {
				return 1.0/getDistance();
			}else{
				if (getWeight()>weightlimit) {
					return 0.0000001;
				}
				return getProfit(); 
			}
		}
		return this.fitness;
	}
	
	//get the distance of all genes
	public double getDistance(){
		double result = 0;
		for (int i = 0; i < genes_num; i++) {

			Gene from = chromesome.get(i);
			Gene to;
			if (i+1<genes_num) {
				to = chromesome.get(i+1);
			}else{
				to = chromesome.get(0);
			}
			//			System.out.println(+":"+(to==null));
			//			if (from!=null&&to!=null) {
			result += from.distanceTo(to);
			//			}	
		}
		return result;
	}
	//get the profit of all genes
	public double getProfit(){
		double result = 0;
//		System.out.println(chromesome.size());
		for (int i = 0; i < chromesome.size(); i++) {
			
			Gene gene = chromesome.get(i);
			if (gene!=null&&gene.isPhenotype()) {
				result += gene.getY();
			}
		}
//		double temp = Math.round(result * 100000)/100000;
		return result;
	}
	//get all the weight
	public double getWeight(){
		double result = 0;
		for (int i = 0; i < chromesome.size(); i++) {
			Gene gene = chromesome.get(i);
			if (gene!=null&&gene.isPhenotype()) {
				result += gene.getX();
			}
		}
//		double temp = Math.round(result * 100000)/100000;
		return result;
	}
	//if the chromesome contains the gene g
	public boolean contains(Gene g){
		if (chromesome.contains(g)) {
			return true;
		}else{
			return false;
		}
	}
	public int getGenes_num() {
		return genes_num;
	}
	public void setGenes_num(int genes_num) {
		this.genes_num = genes_num;
	}
	public ArrayList<Gene> getChromesome() {
		return chromesome;
	}
	public void setChromesome(ArrayList<Gene> chromesome) {
		this.chromesome = chromesome;
	}
	
	
}
