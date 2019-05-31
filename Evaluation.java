package tsp;

public class Evaluation {

	public static void main(String[] args) {
		
		
		int ants = 20;
		int pheromoneValue;
		int evaporationParameter;
		int intensificationParameter;
		int pheromoneWheigth;
		int heuristicWheight;
		int terminationCondition;
		int greedyParameter;
		
		
		Problem p = new Problem("01manhattan.tsp");
		System.out.print("data");
		printMatrix(p.getTownsDistances());
		System.out.println(" ");
		System.out.println(" ");


		
		Initialization init = new Initialization(p, ants);
		
		
		int[][] initialization = init.getinitRoutes();
		System.out.print("Initialized Routes");
		printMatrix(initialization);
		
			
	}
	
	
	
	/**
	 * Print matrix
	 */
	public static void printMatrix(int[][] matrix) {
		
		for (int i = 0; i < matrix.length; i++) {
			
			System.out.println("");

			for (int j = 0; j < matrix[0].length; j++) {
				System.out.printf("|%3d",matrix[i][j]);
				
			}
		}
	}
	
}
