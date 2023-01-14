import java.util.ArrayList;
import java.util.Collections;

public class FrequencyAnalysis {

    static String possibleCharacters = " !#$&*,-./0123456789;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";

    static public ArrayList<CharNumPair> frequencyAnalysis(ArrayList<String> gesla){
        ArrayList<CharNumPair> result = new ArrayList<CharNumPair>();
        ArrayList<Character> presentCharacters = new ArrayList<Character>();
        for (String password:gesla) {
            for (char character:password.toCharArray()) {
                int index = Collections.binarySearch(presentCharacters, character);

                if (index >= 0){
                    result.get(index).numValue += 1;
                }
                else {
                    if(possibleCharacters.contains(String.valueOf(character))){
                        result.add( -index  - 1, new CharNumPair(character, 1));
                        presentCharacters.add(-index - 1, character);
                    }

                }
            }
        }

        result = sortByFrequency(result);
        return result;
    }

    public static ArrayList<CharNumPair> sortByFrequency(ArrayList<CharNumPair> countedArray){
        int numberOfCharacters = 0;
        ArrayList<CharNumPair> sortedArray = new ArrayList<CharNumPair>();
        long maxCount = 0;
        int maxIndex = 0;
        while (countedArray.size() != 0){
            maxCount = 0;
            maxIndex = 0;
            for (int index = 0; index < countedArray.size(); index++) {
                if (countedArray.get(index).numValue > maxCount){
                    maxCount = countedArray.get(index).numValue;
                    maxIndex = index;
                }
            }
            sortedArray.add(sortedArray.size(), countedArray.get(maxIndex));
            numberOfCharacters += countedArray.get(maxIndex).numValue;
            countedArray.remove(maxIndex);

        }
        for (CharNumPair pair:sortedArray) {
            pair.numValue = (int)((float)pair.numValue/(float)numberOfCharacters*(Math.sqrt(numberOfCharacters)+100));

        }

        return sortedArray;
    }

    public static ArrayList<CharNumPair> sortByFrequencyNoNormalise(ArrayList<CharNumPair> countedArray){
        int numberOfCharacters = 0;
        ArrayList<CharNumPair> sortedArray = new ArrayList<CharNumPair>();
        long maxCount = 0;
        int maxIndex = 0;
        while (countedArray.size() != 0){
            maxCount = 0;
            maxIndex = 0;
            for (int index = 0; index < countedArray.size(); index++) {
                if (countedArray.get(index).numValue > maxCount){
                    maxCount = countedArray.get(index).numValue;
                    maxIndex = index;
                }
            }
            sortedArray.add(sortedArray.size(), countedArray.get(maxIndex));
            numberOfCharacters += countedArray.get(maxIndex).numValue;
            countedArray.remove(maxIndex);

        }

        return sortedArray;
    }
}
