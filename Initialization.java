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
	 * constructor contains the amount of ants and creates the route for each ant
	 * @param p
	 * @param ants
	 */
	public Initialization(Problem p, int ants) {

		this.ants = ants;
		this.initRoutes = createRoutes(p);
			
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
	 * Create different combinations randomly
	 * @param Problem
	 * @return initialized routes
	 */
	public int[][] createRoutes(Problem p){
		
		int[][]initRoute = new int[ants][p.getNumberTowns()];
		
		//create base array with numbers for each town
		int[] towns = new int[p.getNumberTowns()];
		for (int i = 0; i < towns.length; i++) {
			towns[i] = i;
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
