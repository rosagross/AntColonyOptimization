package tsp;

import java.util.Arrays;

/**
 * This class intensifies the pheromone values corresponding to the best solutions (shortest paths) for the problem 
 * in every iteration.
 * @author Emilia, Rosa, Tula
 *
 */

public class Intensification {

	/**
	 * Constant that is added to every value of the pheromone matrix that is intensified. 
	 */
    private double intensificationParameter;
    
    /**
     * Two-dimensional pheromone matrix indicating the quality of going from one city to another
     */
    private double[][] pheromoneMatrix;
    
    /**
     * distance matrix containing distances from every city to every other city
     */
    private int[][] distances;
    
    /**
     * number of ants (i. e. of solutions)
     */
    private int ants;
    
    /**
     * The constructor contains the number of ants, the problem p and the intensification parameter.  
     * @param intensificationParameter
     * @param Problem
     * @param Number of Ants
     */
    public Intensification(double intensificationParameter, Problem p, int ants) {
        this.intensificationParameter = intensificationParameter;
        this.pheromoneMatrix = initWithOnes(p.getNumberTowns());
        this.distances = p.getTownsDistances();
        this.ants = ants;
    }
    
    /**
    * Initialization for the pheromoneMatrix
    * @param size, is int since we want shape iXi
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
    * Method that intensifies the values of the pheromone matrix if they correspond to the best solutions
    * @param pheromoneMatrix, solutions
    * @return pheromoneMatrix
    */
    public double[][] intensify(double[][] pheromoneMatrix, int[][] solutions) {
    
        // find the best solutions of the solution array (contains city indexes)
        //int[][] bestSolutions = new int[(int) (ants*0.25)][distances.length];
        
        int[][] bestSolution = findBestSolutions(solutions);
    
        // now we manipulate the pheromone matrix
        for (int j = 0; j < bestSolution.length; j++) {
            pheromoneMatrix[0][bestSolution[j][0]] += this.intensificationParameter;
            for (int i = 0; i < bestSolution[0].length - 1; i++) {
                pheromoneMatrix[bestSolution[j][i]][bestSolution[j][i+1]] += this.intensificationParameter;
            }
            pheromoneMatrix[bestSolution[j][bestSolution[0].length - 1]][0] += this.intensificationParameter;
        }
        
        return pheromoneMatrix;
    }
    
    /**
    * Method finds the total best solution based on the distance matrix.
    * @param solutions
    * @return best solution, so the shortest way
    */
    public int[] findBestSolution(int[][] solutions) {
    // store all the calculated distances in one array
    int[] distValues = new int[solutions.length];
    
    for (int i = 0; i < solutions.length; i++) {
        distValues[i] = evalSolution(solutions[i]); // don't know if method already exists..
    }
    
    // Evaluation.printArray(distValues);
    // System.out.println();
    // find index array minimum of all the distance values
    
    int indexMin = getMin(distValues);
    
    return solutions[indexMin];
    }
    
    /**
    * Method finds 1/4 of the best solutions based on the distance matrix.
    * @param solutions
    * @return best solutions, so the 25% shortest ways
    */
    public int[][] findBestSolutions(int[][] solutions) {
    // store all the calculated distances in one array
    int[] distValues = new int[solutions.length];
    
    for (int i = 0; i < solutions.length; i++) {
        distValues[i] = evalSolution(solutions[i]); // don't know if method already exists..
    }
    
    // Evaluation.printArray(distValues);
    // System.out.println();
    
    // we want to return several solutions (1/4 of the best)
    int[][] solutionsIndexes = new int[(int) (this.ants*0.25)][solutions[0].length];
    
    // find index array minimum of all the distance values
    int indexMin;
    for (int i = 0; i < solutionsIndexes.length; i++) {
        // get the minimum of the saved distances
        indexMin = getMin(distValues);
        solutionsIndexes[i] = solutions[indexMin];
        // set place of minimum to an extremely high value
        distValues[indexMin] = Integer.MAX_VALUE;
    }
    
    return solutionsIndexes;
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
    
    /**
     * Method that evaluates a solution
     * @param one solution
     * @return total distance (value) of the solution
     */
    public int evalSolution(int[] solutions) {
    
        int distValue = distances[0][solutions[0]];
        for (int i = 0; i < solutions.length - 1; i++) {
            distValue += distances[solutions[i]][solutions[i + 1]];
        }
        distValue += distances[solutions[solutions.length-1]][0];
        
        return distValue;
    
    }
}
