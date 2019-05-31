package tsp;

public class Evaluation {

	public static void main(String[] args) {
		
		
		int ants;
		int pheromoneValue;
		int evaporationParameter;
		int intensificationParameter;
		int pheromoneWheigth;
		int heuristicWheight;
		int terminationCondition;
		int greedyParameter;
		
		
		Problem p = new Problem("01manhattan.tsp");
		p.printMatrix(p.getTownsDistances());
		
		
		
		
	}
	
}
