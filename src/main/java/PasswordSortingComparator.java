import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PasswordSortingComparator implements Comparator<String> {
    ArrayList<Character> charList = new ArrayList<Character>();
    ArrayList<Integer> order = new ArrayList<Integer>();

    public PasswordSortingComparator(ArrayList<CharNumPair> frequencyList) {
        for (int i = 0; i < frequencyList.size(); i++) {
            int index = Collections.binarySearch(charList, frequencyList.get(i).charValue);
            charList.add(-index - 1, frequencyList.get(i).charValue);
            order.add(-index - 1, i);
        }
    }

    @Override
    public int compare(String o1, String o2) {
        if (o1.length()!=o2.length()) {
            return o1.length()-o2.length(); //overflow impossible since lengths are non-negative
        }
        int index1 = Collections.binarySearch(charList, o1.charAt(0));
        int index2 = Collections.binarySearch(charList, o2.charAt(0));
        if (index1 >= 0 && index2 >= 0){
            int orderString1 = order.get(index1);
            int orderString2 = order.get(index2);
            if (orderString1 == orderString2) {return 0;}
            if (orderString1 < orderString2){
                return -1;
            }
            else return 1;
        }

        else if(index1 < 0 && index2 >= 0) {return -1;}
        else if(index1 >= 0 && index2 < 0) {return 1;}
        else return Character.compare(o1.charAt(0), o2.charAt(0));
    }
}
