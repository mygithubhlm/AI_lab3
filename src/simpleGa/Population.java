package simpleGa;

public class Population {
    Individual[] individuals;
    /*
     * 构造方法
     */
    // 创建一个种群
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // 初始化种群
        if (initialise) {
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
        for(int i =0;i<individuals.length;i++){
			for(int k = 0; k<individuals[i].size(); k++){
				double ran = Math.random();
				
				System.out.print(individuals[i].getGene(k)+",");
			}
			System.out.println();
		}
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}