package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	 * Distances between all cities in 2d array
	 */
	private int[][] townsDistances;
	

	
	/**
	 * The Constructor of a problem needs the filename
	 * @param filename
	 */
	public Problem(String filename) {
		this.filename = filename;
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
	 * The Constructor of a problem needs the filename
	 * @param filename
	 */
	public int[][] townsDistances(){
		
		int[][] townsDistances = new int[countLines()][countTowns()];
		try (BufferedReader br = new BufferedReader(new FileReader(this.filename))){ 

			for (int i = 0; i < countLines(); i++) {

				//each line is converted to int array with distances
				String townsLine = br.readLine();
				String[] town = townsLine.split( Pattern.quote( " " ) );
				
				
				for (int j = 0; j < countTowns(); j++) {
				
					townsDistances[i][j] = Integer.parseInt(town[j]);			
				}

			}
		} catch (Exception e) {
			System.out.println("Problem with br");
		}
		
		System.out.print(countLines());
		System.out.print(countTowns());


		
		for (int i = 0; i < countLines(); i++) {
		
			System.out.println("");

			for (int j = 0; j < countTowns(); j++) {
				System.out.printf("|%3d",townsDistances[i][j]);
				

				
			}
		}
		
		return townsDistances;
	}
	
	
	public int countLines()  {

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
		
	public int countTowns()  {
		
		String[] towns = null;
		try(BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
		
			String townsLine = br.readLine();
			towns = townsLine.split( Pattern.quote( " " ) );
		} catch (Exception e) {
			System.out.println("Problem with BufferedReader");
		}
		return towns.length;
	}



	
	
		
		


}
