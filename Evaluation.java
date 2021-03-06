package tsp;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class contains the main-experiment where we include the name of the file.
 * that contains the problem. 
 * @author Emilia, Rosa, Tula
 *
 */
public class Evaluation {
	
	/**
	 * Main method containing initialization of tuning parameters, reading in the problem and evaluating the results 
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		
		// initialized with some default values
		int ants =4;
		double pheromoneValue = 1.0;
		double evaporationParameter = 0.7;
		double intensificationParameter = 0.05;
		double pheromoneWeight = 1;
		double heuristicWeight = 1;
		double greedyParameter = 0.5;
		//String filename = "02euclidean.tsp";
		String filename = "test_1";

		
		Problem p = new Problem(filename);
		System.out.print("data");
		printMatrix(p.getTownsDistances());
		System.out.println(" ");
		System.out.println(" ");
		
		// construct the initializer that generates a random inti for every trial 
		Initialization init = new Initialization(p, ants, pheromoneValue);
		
		// construct an solution generator, intensifier and evaporator (save the parameter as attributes)
		Intensification intense = new Intensification(intensificationParameter, p, ants);
		Evaporation evap = new Evaporation(evaporationParameter);
		SolutionGeneration solultionGener = new SolutionGeneration(p.getTownsDistances(), ants, pheromoneWeight, heuristicWeight, greedyParameter);
		
		// construct the algorithm 
		AntColonyOptimization aco = new AntColonyOptimization(init, intense, evap, solultionGener);
		
		//save results
		String[][] results = new String[100][6];
		long time_spent = 0;
		long startTime;
		// execute the algorithms 100 times and compare to the documentation 
		int trials = 1;

		for (int i = 0; i < trials; i++) {
			
			System.out.println(i);
		
			// measure time before each search process
			startTime = System.currentTimeMillis();
			
			// start the searching (initialize new for each trial with the Initializer)
			int[] preferedSolution = aco.bestSolution();
			
			
			// stop time counting after the search and calculating the duration for this single trial
			time_spent = System.currentTimeMillis() - startTime;
			
			//random and improved
			results[i][0] = filename; // which problem was it
			results[i][1] = String.valueOf(aco.iterations); // how many iterations did we need
			results[i][2] = String.valueOf(ants);  // how many ants did we have
			results[i][3] = String.valueOf(aco.resultValue);  // which final value has the "best" value
			results[i][4] = String.valueOf(time_spent); // total time for one trial
			results[i][5] = String.valueOf(time_spent/ aco.iterations); // time per iteration
			
		}	
		
		FileWriter fileWriter = null;
        
        try {
            fileWriter = new FileWriter("data.csv");
            fileWriter.append("Filename, Iterations, Ants, BestValue, durationTotal, durationIteration");
            fileWriter.append("\n");

 
            for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 6; j++) {
	                fileWriter.append(results[i][j]);
	                if (j != 5) {
		                fileWriter.append(",");
					}
				}
                fileWriter.append("\n");
			}
              
            System.out.println("CSV file was created successfully !!!");
             
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
        }

	}
	
	/**
	 * Print matrix
	 * @param matrix
	 * void
	 */
	public static void printMatrix(int[][] matrix) {
		
		for (int i = 0; i < matrix.length; i++) {
			
			System.out.println("");

			for (int j = 0; j < matrix[0].length; j++) {
				System.out.printf("|%3d",matrix[i][j]);
				
			}
		}
	}
	
	/**
	 * Print matrix
	 */
	public static void printMatrix(double[][] matrix) {
		
		for (int i = 0; i < matrix.length; i++) {
			
			System.out.println("");

			for (int j = 0; j < matrix[0].length; j++) {
				System.out.printf("|%3s",matrix[i][j]);
				
			}
		}
	}
	
	/**
	 * Method that prints out the content of an array
	 * @param array
	 */
	public static void printArray(int[] array) {
		for (int j = 0; j < array.length; j++) {
			System.out.printf("|%3d",array[j]);
		}
	}
	
	/**
	 * Method that prints out the content of an array
	 * @param array
	 */
	public static void printArray(double[] array) {
		for (int j = 0; j < array.length; j++) {
			System.out.printf("|%3s",array[j]);
		}
	}
	
}
