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
	
	private Intensification intense;
	private Evaporation evap;
	private SolutionGeneration soluionGeneration;
	
	/**
	 * Constructor constructs an ACO instance
	 * @param init
	 * @param intense
	 * @param evap
	 * @param solutionGeneration
	 */
	public AntColonyOptimization(Intensification intense, Evaporation evap, SolutionGeneration solutionGeneration) {
		this.evap = evap;
		this.intense = intense;
		this.iterations = 0;
		this.soluionGeneration = solutionGeneration;
	}
		
	/**
	 * The algortihm that returns the best solution under the given conditions.
	 * @return the array that contains the best/shortest way
	 */
	public int[] bestSolution(Problem p, int[][] initialized){ // in generated the nr of ants is included.
		int[][] solutions; // in this array the solutions can be stored, we get the solutions from the solution generator
		
	
		do {

			/*
			 *  Befehl um solution zu generieren, die pheromoneMatrix kann mittels getter-method von Intensification 
			 *  geholt werden. Sie wird durch den Konstruktor von Intensification mit 1sen initialisiert.
			 */
			// solutions = SolutionGeneration.generate(initialized) <-- weiss nicht genau wie die methode heisst;)
			
			//evaporate PheromoneMatrix
			evap.evaporate(pheromoneMatrix)
			
			// intensify the pheremone values that correspond to a good solution
			intense.intensify(evap.getEvaporated(), solutions);
			
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
