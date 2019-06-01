package tsp;

//in Evaporation, we reduce every entry of the pheromone matrix by 
//multiplying with a fixed number between 0 and 1
public class Evaporation {

	//evaporation rate
	private double evaporationParameter;
	//pheromone matrix that is evaporated
	private double[][] evaporated;
	
	/*
	 * In the constructor, we call the evaporation method and set the evaporation parameter
	 * @param old_pheromones
	 * @param evaporationParameter
	 */
	public Evaporation(double[][] old_pheromones, double evaporationParameter) {
		this.evaporated = evaporate(old_pheromones, evaporationParameter);
		this.evaporationParameter = evaporationParameter;
	}
	
	/*
	 * Getter for evaporated pheromone Matrix
	 */
	public double[][] getEvaporated() {
		return this.evaporated;
	}
	
	
	/*this is how to evaporate a pheromone matrix :)
	* @param pheromone_matrix
	* @param evaporationParameter
	*/
	public double[][] evaporate(double[][] pheromone_matrix, double evaporationParameter) {
		for(int i = 0; i < pheromone_matrix.length; i++) {
			for(int j = 0; j < pheromone_matrix[1].length; j++) {
				pheromone_matrix[i][j] = pheromone_matrix[i][j] * evaporationParameter;
			}
		}
		return pheromone_matrix;
	}
}
