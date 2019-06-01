package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;


/**
 * The class Problem defines the problem that we have to solve.
 *  
 * @author 
 */
public class Problem {
	

	/**
	 * Name of the Problem
	 */
	private String filename;
	
	/**
	 * amount of towns
	 */
	private int numberTowns;

	
	/**
	 * Distances between all cities in 2d array
	 */
	private int[][] townsDistances;
	
	

	
	/**
	 * The Constructor of a problem needs the filename
	 * @param filename
	 */
	public Problem(String filename) {
		this.filename = filename;
		this.numberTowns = countTowns();		
		this.townsDistances = townsDistances();
	}
	
	
	/**
	 * Getter for townsDistances
	 * 
	 */
	public int[][] getTownsDistances() {
		return this.townsDistances;	
	}
	
	/**
	 * Getter for numberTowns
	 * 
	 */
	public int getNumberTowns() {
		return this.numberTowns;	
	}
	
	
	/**
	 * Generate distanceMatrix from file
	 * @return distanceMatrix
	 */
	public int[][] townsDistances(){
		
		int[][] townsDistances = new int[this.numberTowns][this.numberTowns];
		try (BufferedReader br = new BufferedReader(new FileReader(this.filename))){ 

			for (int i = 0; i < this.numberTowns; i++) {

				//each line is converted to int array with distances
				String townsLine = br.readLine();
				String[] town = townsLine.split( Pattern.quote( " " ) );
				
				
				for (int j = 0; j < this.numberTowns; j++) {
				
					townsDistances[i][j] = Integer.parseInt(town[j]);			
				}

			}
		} catch (Exception e) {
			System.out.println("Problem with br");
		}
		
	
		
		return townsDistances;
	}
	
	
	/**
	 * Count towns of TSP
	 * @return amount towns
	 */
	public int countTowns()  {

		int line = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(this.filename)))  {
			
			line = 0;
			// count amount of PSUs
			while (br.readLine() != null) {
				line ++;
			}
		} catch (Exception e) {
			System.out.println("Problem with BufferedReader");
		}
		return line;
	}
	
	

}

