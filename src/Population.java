import java.util.ArrayList;

// Collection of board states
public class Population {

    public ArrayList<Individual> individuals = new ArrayList<>();

    public Population(int size){
        for(int i = 0; i < size; i++){
            Individual individual = new Individual();
            individuals.add(individual);
        }
    }
    public Population(){
    }
}
