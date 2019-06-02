# AntColonyOptimization

Ant colony optimization is an algorithm inspired by the collective-intelligent behaviour of ants. In this project, it is applied to three instantiations of the traveling salesman problem which is a permutation problem minimizing the sum of the edge values in a complete non-directed graph traversion.

Implementation and tunable parameters


public class AntColonyOptimization


This class contains the main loop that runs until the termination condition that was chosen is reached. The default termination condition is to stop when the best solution does not improve anymore. Other possible conditions are a time limit or number of iterations limit. 
The main loop contains the operation of the following modules. 

public class Problem


Ab object of the class Problem contains all relevant information for a certain TSP that we want to solve. It takes this information from files that are read in. No tunable parameters in this class.  

public class Initialization


This class contains the methods to initialize the pheromone matrix and a first (heuristic|random) set of solutions. The pheromone matrix is shaped citiesXcities and the set of solutions is shaped antsXcities. As default, we initialize pheromone values with 1, but other values are also possible. Also, the choice of the number of ants requires a parameter tuning decision that should consider the number of cities.   

public class SolutionGeneration


For every ant, a new solution (permutation of cities) is constructed based on the earlier solution and the pheromone matrix. 
INSERT NEWS HERE 
Secondly, the greedy parameter that balances between ACO and a heuristic that greedily selects the shortest distance to an available city is tunable. 

public class Evaporation


Here, the whole pheromone matrix evaporates (multiplication with float between 0 and 1) since the pheromones of the ants vanish as time passes. Intuitively, the evaporation rate is the tunable parameter. 

public class Intensification


After evaporation, the entries of the matrix that belong to the best (n) solution(s) are intensified by adding a constant. This is also a tunable parameter. As default, it should not be bigger than 1-evaporationRate.

public class Evaluation


This class creates a Problem object that is to be solved. It applies the algorithm and creates a csv file which allows us to check the performance of our algorithm.
