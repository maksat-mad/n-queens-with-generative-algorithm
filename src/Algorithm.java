//import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;


public class Algorithm {

    public static int counter = 0;

    /* Calculates fitness for individual
     * (+1 for each threat from column for the currently viewed queen, +1 for all threats together from diagonals fot the currently viewed queen)
     * @param one board state
     * @return calculated fitness value (best is 0)
     */
    protected static int getFitness(Individual iv) {
        int clashes = 0;
        int n = iv.list.size(); // Assuming iv.list contains 8 elements

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int queen1Row = i;
                int queen1Column = iv.list.get(i);
                int queen2Row = j;
                int queen2Column = iv.list.get(j);

                // Check for column clash
                if (queen1Column == queen2Column) {
                    clashes += 1;
                }

                // Check for diagonal clash
                int rowDiff = Math.abs(queen1Row - queen2Row);
                int colDiff = Math.abs(queen1Column - queen2Column);

                if (rowDiff == colDiff) {
                    clashes += 1;
                }
            }
        }

        return clashes;
    }


    /* Checks for diagonal clashes for each queen in the board state
     * @param board state; queen to be checked here; index of the queen to be checked here
     * @return boolean if the board state passed the test (true = passed test, no threats; false = didn't pass, a threat was found)
     */
    protected static Boolean checkDiagonals(Individual iv, int current, int indexOfCurrent){
        for (int i = 0; i < 8; i++) {
            if (i != indexOfCurrent) {
                int viewableElem = iv.list.get(i);
                int rowDiff = Math.abs(indexOfCurrent - i);
                int colDiff = Math.abs(current - viewableElem);

                // Check for diagonal threat
                if (rowDiff == colDiff) {
                    return false; // Diagonal threat found
                }
            }
        }
        return true; // No diagonal threats found
    }

    /* Sorts current individuals by fitness (0 is best) in ascending order and mates the best half of individuals with each other and adds a mutation to each new individual
     * @param a population of individuals
     * @return a new population based on the current one
     */
    public static Population nextGeneration(Population pop){
        int popSize = pop.individuals.size();

        pop.individuals.sort(Comparator.comparingInt(Algorithm::getFitness));

        Population halfPop = new Population();
        for (int i = popSize/2-1; i >=0; i--){
            halfPop.individuals.add(pop.individuals.get(i));
        }
        Population newPop = new Population();
        for (int i = popSize/2-1; i >=0; i--) {
            if (i > 0) {
                Individual parent1 = halfPop.individuals.get(i);
                Individual parent2 = halfPop.individuals.get(i - 1);
                Individual nextGenIv = mateIv(parent1, parent2);
                mutateIv(nextGenIv);
                newPop.individuals.add(nextGenIv);

                Individual nextGenIv2 = mateIv(parent2, parent1);
                mutateIv(nextGenIv2);
                newPop.individuals.add(nextGenIv2);
            } else {
                Individual parent1 = halfPop.individuals.get(i);
                Individual parent2 = halfPop.individuals.get(popSize / 2 - 1);
                Individual nextGenIv = mateIv(parent1, parent2);
                mutateIv(nextGenIv);
                newPop.individuals.add(nextGenIv);

                Individual nextGenIv2 = mateIv(parent2, parent1);
                mutateIv(nextGenIv2);
                newPop.individuals.add(nextGenIv2);
            }
        }
        return newPop;
    }

    /* Takes 4 random values from one list and remaining 4 from the other and combines them into a new individual
     * @param 2 different board states
     * @return a combined board state
     */
    protected static Individual mateIv(Individual iv1, Individual iv2){
        final List<Integer> indices = new Random()
                .ints(0, 8)
                .distinct()
                .limit(4)
                .boxed()
                .collect(Collectors.toList());

        Individual newIndividual = new Individual();
        newIndividual.list.clear();

        for (int i = 0; i < 8; i++) {
            if (indices.contains(i)) {
                newIndividual.list.add(iv1.list.get(i));
            }
        }

        for (int value : iv2.list) {
            if (!newIndividual.list.contains(value)) {
                newIndividual.list.add(value);
            }
        }
        return newIndividual;
    }

    /* changes one queen location on the board
     * @param a board state
     * @return the same board state with one element changed
     */
    protected static Individual mutateIv(Individual iv){
        int index = (int)(Math.random()*8);
        int value = (int) (Math.random()*8);

        int indexValue = iv.list.get(index);
        int valueIndex = iv.list.indexOf(value);

        iv.list.set(index, value);
        iv.list.set(valueIndex, indexValue);

        return iv;
    }

    /* runs the full genetic algorithm and prints out results
     * @param a population of board states
     */
    public static void runAlgorithm(Population population) throws Exception{
        final List<Integer> solutionList = generation(population).list;
        System.out.println("Found suitable board state on generation " + (counter+1) + ": " + solutionList);
        System.out.println("Here is the found solution as a board where . marks an empty spot and, X marks a queen");
        printBoard(solutionList);
    }

    /* prints the boards
     * @param board state list
     */
    protected static void printBoard(List<Integer> state) {
        for (Integer queen:state) {
            for (int i = 0; i <8; i++){
                if (i==queen){
                    System.out.print("X ");
                }
                else System.out.print(". ");
            }
            System.out.println();
        }
    }

    /* generates new populations until a solution is found or 2000 generations is reached
     * @param the starting population
     * @return the solution board state
     */
    
    protected static Individual generation(Population population) throws Exception{
        if (counter >= 1000) {
            throw new Exception("Didn't find solution in 1000 generations");
        }
        for (Individual iv:population.individuals) {
            if (getFitness(iv) == 0){            	            	         
                return iv;
            }
        }
        counter += 1;
        return generation(nextGeneration(population));
    }

    public void printPopFitnesses(Population pop) {
        for (Individual individual : pop.individuals) {
            System.out.println(getFitness(individual));
        }
    }

    public static void printFitness(Individual iv){
        System.out.println(getFitness(iv));
    }

    protected static void printBestFitness(Population pop){
        int fitness = 200;
        for (Individual iv:pop.individuals) {
            int current = getFitness(iv);
            if (current <= fitness) {
                fitness = current;
            }
        }
        System.out.println(fitness);
    }
}
