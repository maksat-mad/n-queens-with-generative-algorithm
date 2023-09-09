import java.util.ArrayList;
import java.util.List;

public class TestGA extends Algorithm {
	public static List<Integer> generationCounts = new ArrayList<>();
    public static void main(String[] args) throws Exception {
    	Population pop = new Population(100);
    	runAlgorithm(pop);
    	
// Use this input code for Issue 3:
    	
//    	for (int i = 0; i<1000; i++) {
//    		Population pop = new Population(100);
//    		generation(pop);
//    		generationCounts.add(counter+1);
//    		counter = 0;
//
//    	}
//    	System.out.println(calculateAverage(generationCounts));
//    	System.out.println(calculatePercent(generationCounts) + "%");
//    	generationCounts.removeAll(generationCounts);
    	
    }
    
    private static float calculatePercent(List <Integer> ints) {    	
    	int countBelow = 0;
    	if (!ints.isEmpty()) {
    		for (Integer value:ints) {
    			if (value < 100) {
    				countBelow += 1;
    			}
    		}
    	}
    	float per = (float)countBelow/(float)(ints.size())*100;    	
    	return per;
    }
    
    private static double calculateAverage(List <Integer> ints) {
    	  Integer sum = 0;
    	  if(!ints.isEmpty()) {
    	    for (Integer value : ints) {
    	        sum += value;
    	    }
    	    return sum.doubleValue() / ints.size();
    	  }
    	  return sum;
    }

    /*
     * HINT!
     * These methods help you run Algorithm.java methods separately to make it easier to debug them one-by-one.
     * You are strongly recommended to use these while debugging, especially for issue 3
     * You may want to change the randomised input there with your own hard-coded input (maybe a list).
     */
    public static void runCheckDiagonals() {
        Individual iv = new Individual();
        System.out.println(iv.list);
        printBoard(iv.list);
        for (int i = 0; i<iv.list.size();i++) {
            System.out.println(checkDiagonals(iv, iv.list.get(i), i));
        }
        System.out.println();
    }

    public static void runGetFitness() {
        Individual iv = new Individual();
        System.out.println(iv.list);
        printBoard(iv.list);
        printFitness(iv);
        System.out.println();
    }

    public static void runMateIv() {
        Individual iv1 = new Individual();
        System.out.println(iv1.list);
        Individual iv2 = new Individual();
        System.out.println(iv2.list);
        System.out.println(mateIv(iv1, iv2).list);
        System.out.println();
    }

    public static void runMutateIv() {
        Individual iv = new Individual();
        System.out.println(iv.list);
        System.out.println(mutateIv(iv).list);
        System.out.println();
    }

    public static void runNextGeneration() {
        Population pop = new Population(10);
        for (Individual iv : pop.individuals) {
            System.out.println(iv.list);
        }

        Population nextPop = nextGeneration(pop);
        System.out.println();
        for (Individual iv : nextPop.individuals) {
            System.out.println(iv.list);
        }
        System.out.println();
    }

}
