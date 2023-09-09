import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// State of board as list of 8 integer
// each element in the list counts as a row on chess board
// each value of those elements is the chess board square in that row (column)
public class Individual {

    public List<Integer> list = new ArrayList<Integer>();

//    public Individual() {
//        for (int i = 8; i > 0; i--) {
//            list.add((int) (Math.random() * 8));
//        }
//        System.out.println(list);
//    }

    public Individual() {
        List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        Collections.shuffle(values);
        list.addAll(values);
//        System.out.println(list);
    }
}
