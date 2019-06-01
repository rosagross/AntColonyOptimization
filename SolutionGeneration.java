package tsp;

import java.util.Arrays;
import java.util.Random;

public class SolutionGeneration {

	
	private int ants;

	private double pheromoneWeight;
	
	private double heuristicWeights;
	
	private double greedyParameter;
	
	private int[][] solutionsMatrix;
	
	private double[][] pheromoneMatrix;
	
	private int[][] distanceMatrix;
	
	private int[][] solutions;
	

	
	
	public SolutionGeneration(int[][] distanceMatrix, int ants, double pheromoneWeight, double heuristicWeight, double greedyParameter, double[][] pheromoneMatrix, int[][] solutions) {
		
		this.ants = ants;
		this.pheromoneMatrix = pheromoneMatrix;
		this.distanceMatrix = distanceMatrix;
		this.heuristicWeights = heuristicWeight;
		this.pheromoneWeight = pheromoneWeight;
		this.solutions = solutions;
		this.greedyParameter = greedyParameter;
		
		
	}
	
	/**
	 * 
	 * @return matrix containing solution paths
	 */
	public int[][] getSolutionsMatrix(){
		return solutionsMatrix;
	}
	
	
	
	/**
	 * Calculate soultion path for each ant according to the amount of pheromone  and the distances
	 * @return solutionsMatrix
	 */
	public int[][] solutionsMatrix(int[][] distances, double[][] pheromoneMatrix) {
		
		int[][] solutionsMatrix = new int[solutions.length][distances.length - 1];
		int[] townsUnvisited;
		double[] probabilities;
		int counter = 0;
		int[] choice = new int[100];
		int end;

		
		int[] solution = new int[distances.length - 1];
	    Random random = new Random();

		
		//create solution for each ant
		for (int i = 0; i < ants; i++) {

			
		    int progress = 0;
			int lastTownIndex = - 1;
			probabilities = new double[distances.length - 1];


			
			//prepare townsUnvisited
			townsUnvisited = new int[distances.length - 1];
			for (int t = 0; t < distances.length - 1; t++) {
				townsUnvisited[t] = t + 1;
			}
			
			while (townsUnvisited.length > 0) {
				
				
				//calculate probability for each town that is left
				for (int j = 0; j < townsUnvisited.length; j++) {
					

					//collect probabilities for all left towns in array
					probabilities[j] = calculateProb(townsUnvisited.length, lastTownIndex +1 , townsUnvisited[j], distances,  pheromoneMatrix);
					
					if (j == townsUnvisited.length -1) {
						end = choice.length;
					} else {
						end = (int) Math.round(probabilities[j]*100);
					}
					

					//add current town choices, occurrence of current town depends and probability
					for (int l = 0; l < end; l++) {
						choice[counter] = townsUnvisited[j];
						
						
						if (counter < choice.length - 1) {
							counter++;
						} else {
							break;
						}
					}	
				}
				
		
				//choose town from choice randomly				
				solution[progress] = choice[random.nextInt(choice.length - 1)];

				for (int j = 0; j < townsUnvisited.length; j++) {
					if (townsUnvisited[j] == solution[progress]) {
						lastTownIndex = j;
						
						//delete chosen town from list of unvisited towns
						swap(townsUnvisited, lastTownIndex, townsUnvisited.length - 1);
						townsUnvisited = Arrays.copyOf(townsUnvisited, townsUnvisited.length-1);

						break;
					}
				}
			

				//shorten probabilities
				probabilities = Arrays.copyOf(probabilities, probabilities.length-1);
				
				//set counters for next round
				counter = 0;
				progress++;
			}
			
			//copy solution in solutionMatrix
			for (int j = 0; j < solution.length; j++) {
				solutionsMatrix[i][j] = solution[j];
			}
		}
			
		
		return solutionsMatrix;
	}
	
	
	
	/**
	 * Calculate probability depending on pheromoneValue, pheromoneWeight and heuristicWeights
	 * @param i
	 * @param j
	 * @return probability
	 */
	private double calculateProb(double solutionLength, int i, int j, int[][] distances,  double[][] pheromoneMatrix) {
		
		double prob = (1.0 /solutionLength) * (Math.pow(pheromoneMatrix[i][j],pheromoneWeight)/Math.pow(distances[i][j],heuristicWeights));
		return prob;
	}
	
	private int[] swap(int[] array,int i, int j) {
		int dummy;
		dummy = array[i];
		array[i] = array[j];
		array[j] = dummy;
		
		return array;
	}
}
