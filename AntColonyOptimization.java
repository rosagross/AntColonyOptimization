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
	public double iterations;
	public double resultValue;
	
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
	 * The algortihm that returns the best solution under the given conditions.
	 * @return the array that contains the best/shortest way
	 */
	public int[] bestSolution(Problem p, int ants, double pheromoneValue, double evaporationParameter, double intensificationParameter, double pheromoneWeight, double heuristicWeight, double greedyParameter){ 
		
	
		int[][] distances = p.getTownsDistances();
		double[][] newPheromones = init.getPheromoneMatrix();
		int[][] solutions = init.getinitRoutes();
		
		
		do {
			newPheromones = evap.evaporate(newPheromones);
			newPheromones = intense.intensify(newPheromones, solutions);
			solutions = solutionGeneration.solutionsMatrix(distances, newPheromones);

			
			this.iterations ++;
			
		} while (terminate());
		
		// the objective function with that we calculate the distance of the solution
		this.resultValue = calcValue();
		int[] bestSolution = Intensification.findBestSolution(solutions);
		
		return bestSolution;
	}

	//TODO: function that calculate the distance of the solution. Probably already implemented in the
	// solution generation function???
	private double calcValue() {
		return 0;
	}

	/**
	 * If the termination condition is reached, terminate with searching better solutions.
	 * The termination condition could be a time limit or a number of iterations...        TODO
	 * @return boolean that indicates if the termination condition is reached
	 */
	private boolean terminate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Getter for the nr of iterations needed
	 * @return
	 */
	public double getIterations() {
		return this.iterations;
	}

	/**
	 * Getter for the value of the resulting order of city-visits
	 * @return
	 */
	public double getResultVal() {
		return this.resultValue;
	}
		
		

}
