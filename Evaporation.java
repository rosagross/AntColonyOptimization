package tsp;

/**
 * Class to evaporate whole pheromone matrix since the pheromone scent vanishes as time passes
 * @author Tula
 *
 */
public class Evaporation {

	/**
	 * evaporation rate, [0, 1]
	 */
	private double evaporationParameter;
	
	/**
	 * An object of the class Evaporation has the attribute evaporationParameter
	 * @param evaporationParameter
	 */
	public Evaporation(double evaporationParameter) {
		this.evaporationParameter = evaporationParameter;
	}
	
//	/**
//	 * Getter for evaporated pheromone Matrix
//	 * @return evaporated matrix
//	 */
//	public double[][] getEvaporated() {
//		return this.evaporated;
//	}
//	
	
	/**evaporation of pheromone matrix
	* @param pheromone_matrix
	* @param evaporationParameter
	* @return pheromone_matrix
	*/
	public double[][] evaporate(double[][] pheromone_matrix) {
		for(int i = 0; i < pheromone_matrix.length; i++) {
			for(int j = 0; j < pheromone_matrix[1].length; j++) {
				pheromone_matrix[i][j] = pheromone_matrix[i][j] * evaporationParameter;
			}
		}
		return pheromone_matrix;
	}
}
