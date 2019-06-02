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
	
	private Random random = new Random();
	

	
	
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
		int[][] townsUnvisited;
		double[] probabilities;
		int counter = 0;
		int[] choice = new int[1000];
		int end;
		int indexLastUnvisitedTowns = distanceMatrix.length - 1;
		int indexProbabilities = distanceMatrix.length - 1;

		
	   

		
		
	    int progress = 0;
		int lastTownIndex = - 1;
		probabilities = new double[distanceMatrix.length - 1];


			
		//prepare townsUnvisited
		townsUnvisited = new int[ants][distanceMatrix.length - 1];
		for (int a = 0; a < ants; a++) {
			for (int j = 0; j < distanceMatrix.length - 1; j++) {
				townsUnvisited[a][j] = j + 1;
			}
		}
		
	
			
		for (int i = 0; i < distanceMatrix.length - 1; i++) {
			
			
			for (int a = 0; a < ants; a++) {
				
				
				for (int j = 0; j < indexLastUnvisitedTowns; j++) {
					
					if (i == 0) {
						lastTownIndex = 0;
					} else {
						lastTownIndex = solutionsMatrix[a][i-1];
					}
					
					
//					
					
					probabilities[j] = calculateProb(lastTownIndex, townsUnvisited[a][j],  pheromoneMatrix);
				}
				
//				Evaluation.printMatrix(townsUnvisited);
//				System.out.println();
				
				
				//normalize probabilities
				double normalization = 0.0;
				//normalize all probabilities
				for (int j = 0; j < indexLastUnvisitedTowns; j++) {
					//calculate normalization value
					normalization += Math.pow(pheromoneMatrix[lastTownIndex][townsUnvisited[a][j]],pheromoneWeight)/Math.pow(distanceMatrix[lastTownIndex][townsUnvisited[a][j]],heuristicWeights);	
				}
				for (int j2 = 0; j2 < indexProbabilities; j2++) {
					probabilities[j2] =  probabilities[j2] * (1.0/normalization);
				}
				 
//				Evaluation.printArray(probabilities);

				
				// with probability of q just take the next town with the next probability
                if (getRandomBoolean(greedyParameter)) {
                    solutionsMatrix[a][progress] = townsUnvisited[a][getMaxTown(probabilities)];
                } else { 
                	

				for (int j2 = 0; j2 < indexProbabilities; j2++) {
					
			

					if (j2 == indexProbabilities - 1) {
						end = choice.length;
					} else {
						//System.out.println(j2);
						end = (int) Math.round(probabilities[j2]*1000);
					}	
						
					

					//add current town choices, occurrence of current town depends and probability
					for (int l = 0; l < end; l++) {

						choice[counter] = townsUnvisited[a][j2];
						if (!(counter < choice.length - 1)) {
							break;
						} 
						counter++;
					}
					
					
				
				}
				
//				System.out.println();
//				Evaluation.printArray(choice);
//				System.out.println();

				//set counters for next round
				counter = 0;
			
				//choose town from choice randomly				
				solutionsMatrix[a][progress] = choice[random.nextInt(choice.length)];
            }
				
				
                
                
			
				
				for (int j = 0; j < indexLastUnvisitedTowns; j++) {
					if (townsUnvisited[a][j] == solutionsMatrix[a][progress]) {
						
						
						//delete chosen town from list of unvisited towns
						swap(townsUnvisited[a], j, indexLastUnvisitedTowns - 1);
						break;
				
					}
				}
				
				
				
					
			}
			indexLastUnvisitedTowns--;
			indexProbabilities--;
			progress++;

		}
			
		
		return solutionsMatrix;
	}
	
	
	
	/**
	 * Calculate probability depending on pheromoneValue, pheromoneWeight and heuristicWeights
	 * @param i
	 * @param j
	 * @return probability
	 */
	private double calculateProb(int i, int j,  double[][] pheromoneMatrix) {
		
		
		double prob = Math.pow(pheromoneMatrix[i][j],pheromoneWeight)/Math.pow(distanceMatrix[i][j],heuristicWeights);
		
		
		
		
		return prob;
	}
	
	private int[] swap(int[] array,int i, int j) {
		int dummy;
		dummy = array[i];
		array[i] = array[j];
		array[j] = dummy;
		
		return array;
	}
	
	public boolean getRandomBoolean(double p){
        return random.nextDouble() < p;
    }
	
	/**
     * find maximum
     * @param inputArray
     * @return
     */
    private static int getMaxTown(double[] inputArray) {
        double maxValue = inputArray[0];
        int indexMax = 0;
        for(int i = 1; i < inputArray.length; i++){
        	if(inputArray[i] > maxValue){
        		maxValue = inputArray[i];
        		indexMax = i;
        	}
        }
        return indexMax;
        
    }
}
