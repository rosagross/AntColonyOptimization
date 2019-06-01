package tsp;

import java.util.Arrays;

public class Intensification {
	
	private double intensificationParameter;
	private double[][] pheromoneMatrix;
	private int[][] distances;
	
	public Intensification(double intensificationParameter, Problem p) {
		this.intensificationParameter = intensificationParameter;
		this.pheromoneMatrix = initWithOnes(p.getNumberTowns());
		this.distances = p.getTownsDistances();
	}
	
	/**
	 * Initialization for the pheromoneMatrix
	 * @param size
	 * @return the pheromoneMatrix
	 */
	private double[][] initWithOnes(int i) {
		double[][] matrix = new double[i][i];

		// Fill each row with 1.0
		for (double[] row: matrix)
		    Arrays.fill(row, 1.0);
		return matrix;
	}


	/**
	 * Method that intensifies the values of the pheromone matrix 
	 * @param pheromoneMatrix
	 * @return
	 */
	public double[][] intensify(double[][] pheromoneMatrix, int[][] solutions) {
		
		// find the best solution of the solution array (contains city indexes)
		int[] bestSolution = findBestSolution(solutions);
		
		// TODO: wo starten wir?? Es muss noch ein weiterer wert veraendert werden (der von der startstadt aus geht)
		// now we manipulate the pheromone matrix 
		pheromoneMatrix[0][bestSolution[0]] += this.intensificationParameter;
		for (int i = 0; i < bestSolution.length -1; i++) {
			pheromoneMatrix[bestSolution[i]][bestSolution[i+1]] += this.intensificationParameter;
		}
		pheromoneMatrix[bestSolution[bestSolution.length - 1]][0] += this.intensificationParameter;

		
		return pheromoneMatrix;
	}
	
	/**
	 * Method finds the best solution based on the distance matrix.
	 * @return best solution, so the shortest way
	 */
	public int[] findBestSolution(int[][] solutions) {
		// store all the calculated distances in one array
		int[] distValues = new int[solutions.length]; 
		
		for (int i = 0; i < solutions.length; i++) {
			distValues[i] = evalSolution(solutions[i]); // don't know if method already exists..
		}

//		Evaluation.printArray(distValues);
//		System.out.println();
		// find index array minimum of all the distance values
		int indexMin = getMin(distValues);
		
		return solutions[indexMin];
	}

	/**
	 * Finds the index of the minimum in an array
	 * @param inputArray
	 * @return index of minimum
	 */
	private static int getMin(int[] inputArray) {
		// Method for getting the minimum value
		int minValue = inputArray[0]; 
		int indexMin = 0;
		for(int i = 1; i < inputArray.length; i++){ 
			if(inputArray[i] < minValue){ 
		        minValue = inputArray[i]; 
		        indexMin = i;
		    } 
		} 
		return indexMin; 
		  
	}
	
	public int evalSolution(int[] solutions) {
		
		int distValue  = distances[0][solutions[0]];
		for (int i = 0; i < solutions.length - 1; i++) {
			distValue += distances[solutions[i]][solutions[i + 1]];
		}
		distValue += distances[solutions[solutions.length-1]][0];
		
		return distValue;
		
	}
}
