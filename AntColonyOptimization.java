package tsp;

/**
 * This class implements the general ACO algorithm and puts all the module together.
 * 
 * @author Rosa, Emilia, Tula
 *
 */
public class AntColonyOptimization {

	/*
	 * In this variables we save the information about how many iterations we needed 
	 * so we can calculate the time per iteration later (duration is measured in Evaluation class)
	 */
	public double iterations = 0;
	public double resultValue = 0;
	
	
	private Initialization init;
	private Intensification intense;
	private Evaporation evap;
	private SolutionGeneration solutionGeneration;
	
	/**
	 * Constructor constructs an ACO instance
	 * @param init
	 * @param intense
	 * @param evap
	 * @param solutionGeneration
	 */
	public AntColonyOptimization(Initialization init, Intensification intense, Evaporation evap, SolutionGeneration solutionGeneration) {
		
		this.init = init;
		this.evap = evap;
		this.intense = intense;
		this.iterations = 0;
		this.solutionGeneration = solutionGeneration;
	}
		
	/**
	 * The algorithm that returns the best solution under the given conditions.
	 * @return the array that contains the best/shortest way
	 */
	public int[] bestSolution(){ 
		
	
		double[][] newPheromones = init.getPheromoneMatrix();
		int[][] solutions = init.getinitRoutes();
		
//		Evaluation.printMatrix(solutions);
		
		
		
		do {
			newPheromones = evap.evaporate(newPheromones);
//			System.out.println();
//			Evaluation.printMatrix(newPheromones);
//			System.out.println();

			newPheromones = intense.intensify(newPheromones, solutions);
			
//			System.out.println();
//			Evaluation.printMatrix(newPheromones);
//			System.out.println();

			solutions = solutionGeneration.solutionsMatrix(newPheromones);
	
//			System.out.println();
//			Evaluation.printMatrix(solutions);
//			System.out.println();
			
			this.iterations ++;
			
		} while (terminate());
		
		this.iterations = 0;
		
		
		// the objective function with that we calculate the distance of the solution
		
		this.resultValue = intense.evalSolution(intense.findBestSolution(solutions));
		int[] bestSolution = intense.findBestSolution(solutions);
		
		return bestSolution;
	}

	/**
	 * If the termination condition is reached, terminate with searching better solutions.
	 * The termination condition is a number of iterations.
	 * @return boolean that indicates if the termination condition is reached
	 */
	private boolean terminate() {
		
		if (iterations == 100) {
			return false;
		}
		return true;
		
	}
	
	/**
	 * Getter for the nr of iterations needed
	 * @return number of iterations
	 */
	public double getIterations() {
		return this.iterations;
	}

	/**
	 * Getter for the value of the resulting order of city-visits
	 * @return resulting city permutation 
	 */
	public double getResultVal() {
		return this.resultValue;
	}	

}
