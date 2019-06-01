package tsp;

import java.util.Random;

/**
 * The class initialization creates a random route for every ant.
 *  
 * @author 
 */

public class Initialization {

	/**
	 * amount of ants
	 */
	private int ants;
	
	/**
	 * routes per ant
	 */
	private int[][]initRoutes;
	
	
	/**
	 * 2d array containing the pheromone values
	 */
	private double pheromoneValue;
	
	
	/**
	 * 2d array containing the pheromone values
	 */
	private double[][]pheromoneMatrix;
	
	
	private Problem p;
	
	
	/**
	 * constructor contains the amount of ants and creates the route for each ant
	 * @param p
	 * @param ants
	 */
	public Initialization(Problem p, int ants, double pheromoneValue) {

		this.ants = ants;
		//this.initRoutes = createRoutes(p);
		
		
		this.pheromoneValue = pheromoneValue;
		this.pheromoneMatrix = initPheromoneMatrix(p);
		SolutionGeneration solultionGener = new SolutionGeneration(p.getTownsDistances(), ants, 0, 1, 1);
		this.initRoutes = solultionGener.solutionsMatrix(pheromoneMatrix);
		this.p = p;
			
	}

	
	
	/**
	 * getter for initialized routes
	 * 	 
	 * @return routes
	 */
	public int[][] getinitRoutes() {
		
		return this.initRoutes;	
	}
	
	/**
	 * getter for pheromone matirx
	 * 	 
	 * @return routes
	 */
	public double[][] getPheromoneMatrix() {
		
		return this.pheromoneMatrix;	
	}
	
	
	/**
	 * Create different combinations randomly
	 * @param Problem
	 * @return initialized routes
	 */
	public int[][] createRoutes(Problem p){
		
		int[][]initRoute = new int[ants][p.getNumberTowns()-1];
		
		//create base array with numbers for each town
		int[] towns = new int[p.getNumberTowns() - 1];
		for (int i = 0; i < towns.length; i++) {
			towns[i] = i + 1;
		}
		
		//create way for every ant
		int[] shuffledTowns = new int[towns.length];
		for (int i = 0; i < ants; i++) {
			shuffledTowns = shuffleArray(towns);
			for (int j = 0; j < shuffledTowns.length; j++) {

				initRoute[i][j] = shuffledTowns[j];
			}
		}
		return initRoute;	
	}
	
	/**
	 * initialize pheromoneMatrix
	 */
	private double[][] initPheromoneMatrix(Problem p) {
		
		double[][] pheromoneMatrix = new double[p.getNumberTowns()][p.getNumberTowns()];
		
		for (int i = 0; i < p.getNumberTowns(); i++) {
			for (int j = 0; j < p.getNumberTowns(); j++) {
				if (p.getTownsDistances()[i][j] > 0) {
					pheromoneMatrix[i][j] = this.pheromoneValue;
				}
			}
		}
		return pheromoneMatrix;
	}
	
	
	/**
	 * Method for shuffling the base array
	 * @param array
	 */
	private static int[] shuffleArray(int[] array) {
	    int index, temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--) {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
		return array;
	}
}
