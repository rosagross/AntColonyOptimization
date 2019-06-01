package tsp;

import java.util.Arrays;
import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;

public class SolutionGeneration {

	
	private int ants;

	private double pheromoneWeight;
	
	private double heuristicWeights;
	
	private double greedyParameter;
	
	private int[][] solutionsMatrix;
	
	private double[][] pheromoneMatrix;
	
	private int[][] distanceMatrix;
	
	private int[][] solutions;
	

	
	
	public SolutionGeneration(int[][] distanceMatrix, int ants, double pheromoneWeight, double heuristicWeight, double greedyParameter) {
		
		this.ants = ants;
		this.distanceMatrix = distanceMatrix;
		this.heuristicWeights = heuristicWeight;
		this.pheromoneWeight = pheromoneWeight;
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
	public int[][] solutionsMatrix(double[][] pheromoneMatrix) {
		
		int[][] solutionsMatrix = new int[ants][distanceMatrix.length - 1];
		int[] townsUnvisited;
		double[] probabilities;
		int counter = 0;
		int[] choice = new int[10000];
		int end;

		
		int[] solution = new int[distanceMatrix.length - 1];
	    Random random = new Random();

		
		//create solution for each ant
		for (int i = 0; i < ants; i++) {

			
		    int progress = 0;
			int lastTownIndex = - 1;
			probabilities = new double[distanceMatrix.length - 1];


			
			//prepare townsUnvisited
			townsUnvisited = new int[distanceMatrix.length - 1];
			for (int t = 0; t < distanceMatrix.length - 1; t++) {
				townsUnvisited[t] = t + 1;
			}
			
			while (townsUnvisited.length > 0) {
				
				double normalization = 0.0;
				//calculate probability for each town that is left
				for (int j = 0; j < townsUnvisited.length; j++) {
					
					//collect probabilities for all left towns in array
					probabilities[j] = calculateProb(townsUnvisited.length, lastTownIndex +1 , townsUnvisited[j],  pheromoneMatrix);
					normalization += probabilities[j];
				}	
				
					
				for (int j2 = 0; j2 < probabilities.length; j2++) {
					probabilities[j2] =  probabilities[j2] * (1.0/normalization);
				}
				

//				System.out.println();
//				Evaluation.printArray(probabilities);
//				System.out.println();
					

					for (int j2 = 0; j2 < probabilities.length; j2++) {
						
					
						if (j2 == townsUnvisited.length -1) {
							end = choice.length;
						} else {
							end = (int) Math.round(probabilities[j2]*10000);
						}
						
	
						//add current town choices, occurrence of current town depends and probability
						for (int l = 0; l < end; l++) {
							choice[counter] = townsUnvisited[j2];
							
							
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
	private double calculateProb(double solutionLength, int i, int j,  double[][] pheromoneMatrix) {
		
		double solutionSum = 0.0;
		for (int k = 0; k < pheromoneMatrix.length; k++) {
			solutionSum += pheromoneMatrix[i][k];
		}
		
		double prob = (1.0 /solutionSum) * (Math.pow(pheromoneMatrix[i][j],pheromoneWeight)/Math.pow(distanceMatrix[i][j],heuristicWeights));
		
		
		
		
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
