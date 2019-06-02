package tsp;

import java.util.Arrays;
import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;

/**
 * This class generates new solutions for every ant.
 * @author Emilia, Tula, Rosa
 */
public class SolutionGeneration {

	/**
	 * number determining how many ants (i. e. solutions) we have
	 */
	private int ants;
	
	

	/**
	 * parameter that decides how important the pheromones are
	 */
	private double pheromoneWeight;
	
	
	
	/**
	 * parameter that decides how important the heuristic is
	 */
	private double heuristicWeights;
	
	
	
	/**
	 * double [0, 1] that determines with what probability we select the best value to go to the next city 
	 */
	private double greedyParameter;
	
	
	
	/**
	 * matrix shaped antsXcities containing possible solutions
	 */
	private int[][] solutionsMatrix;
	
	
	
	/**
	 * matrix containing current pheromone values
	 */
	private double[][] pheromoneMatrix;
	
	
	
	/**
	 * matrix containing the distances from each city to any other city
	 */
	private int[][] distanceMatrix;
	
	
	
	/**
	 * matrix containing all solutions
	 */
	private int[][] solutions;
	
	
	
	/**
	 * random object needed to find random city and random double
	 */
	private Random random = new Random();
	

	
	/**
	 * Constructor for solution generation. Object of this class has the following params:
	 * @param distanceMatrix
	 * @param ants
	 * @param pheromoneWeight
	 * @param heuristicWeight
	 * @param greedyParameter
	 */
	public SolutionGeneration(int[][] distanceMatrix, int ants, double pheromoneWeight, double heuristicWeight, double greedyParameter) {
		
		this.ants = ants;
		this.distanceMatrix = distanceMatrix;
		this.heuristicWeights = heuristicWeight;
		this.pheromoneWeight = pheromoneWeight;
		this.greedyParameter = greedyParameter;
	}
	
	/**
	 * Getter for matrix containing solution paths
	 * @return matrix containing solution paths
	 */
	public int[][] getSolutionsMatrix(){
		return solutionsMatrix;
	}
	
	/**
	 * Calculate solution path for each ant according to the amount of pheromone  and the distances
	 * @param pheromone matrix
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
						probabilities[indexProbabilities - 1] = 0;
						townsUnvisited[a][indexLastUnvisitedTowns - 1] = 0;
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
	 * @param coming from city index i
	 * @param going to city index j
	 * @return probability
	 */
	private double calculateProb(int i, int j,  double[][] pheromoneMatrix) {
		double prob = Math.pow(pheromoneMatrix[i][j], pheromoneWeight)/Math.pow(distanceMatrix[i][j], heuristicWeights);
		return prob;
	}
	
	
	
	/**
	 * Method to swap array[i] and array[j]
	 * @param array
	 * @param index i
	 * @param index j
	 * @return new array
	 */
	private int[] swap(int[] array,int i, int j) {
		int dummy;
		dummy = array[i];
		array[i] = array[j];
		array[j] = dummy;
		
		return array;
	}
	
	
	
	/**
	 * Method to get 'yes' with probability p and 'no' otherwise
	 * @param p
	 * @return random boolean
	 */
	public boolean getRandomBoolean(double p){
        return random.nextDouble() < p;
    }
	
	
	
	/**
     * Method to find maximum in array
     * @param inputArray
     * @return index of maximum town
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
