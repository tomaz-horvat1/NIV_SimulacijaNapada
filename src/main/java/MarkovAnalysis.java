import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MarkovAnalysis {
    static char[] possibleCharacters = " !#$&*,-./0123456789;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();

    static public ArrayList<ArrayList<CharNumPair>> markovAnalysis(ArrayList<String> gesla){
        ArrayList<ArrayList<CharNumPair>> result = new ArrayList<ArrayList<CharNumPair>>(Collections.singleton(new ArrayList<CharNumPair>()));
        for (char character:possibleCharacters) {
            result.get(0).add(new CharNumPair(character,0));
            ArrayList<CharNumPair> noviArray = new ArrayList<CharNumPair>();
            for (char characterDrugi:possibleCharacters) {
                noviArray.add(new CharNumPair(characterDrugi, 0));
            }

            result.add(noviArray);
        }


        for (String password:gesla) {
            int index = Arrays.binarySearch(possibleCharacters, password.charAt(0));
            if (index < 0) {continue;}
            result.get(0).get(index).numValue += 1;
            for (int i = 1; i < password.length(); i++){
                int indexPrejsnjega = Arrays.binarySearch(possibleCharacters, password.charAt(i - 1));
                index = Arrays.binarySearch(possibleCharacters, password.charAt(i));
                if (index < 0) {break;}
                result.get(indexPrejsnjega).get(index).numValue += 1;
            }
        }

        for (int i = 0; i < result.size();i++){
            result.set(i, FrequencyAnalysis.sortByFrequency(result.get(i)));
        }

        return result;
    }
}
