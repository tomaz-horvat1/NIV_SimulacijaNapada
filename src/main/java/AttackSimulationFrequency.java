import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AttackSimulationFrequency {
    ArrayList<CharNumPair> frequencyList;
    String filePath;
    ArrayList<Character> characterList;
    char[] usedCharacterArray = "!#$&*,-./0123456789;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();;
    int[] usedFrequencyArray = new int[75];

    public AttackSimulationFrequency(ArrayList<CharNumPair> frequencyList, String filePath) {
        this.frequencyList = frequencyList;
        this.filePath = filePath;
        characterList = new ArrayList<Character>();

        Arrays.sort(usedCharacterArray);
        for (int i = 0; i < this.frequencyList.size(); i++) {
            if (this.frequencyList.get(i).numValue >= 1) {
                characterList.add(frequencyList.get(i).charValue);
            }
            else{
                while(characterList.size() != frequencyList.size()){
                    frequencyList.remove(i);
                }
                break;
            }
        }
        for (char character:"abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ_@-/$!#&*?;.,".toCharArray()) {
            if (characterList.contains(character) == false){
                characterList.add(character);
                frequencyList.add(new CharNumPair(character, 1));
            }
        }

    }

    public void NormalAttack(String generatedFileName){
        ArrayList<String>  passwordsList = PasswordReader.getPasswordsFromFile(filePath);
        ArrayList<String> numberOfAttemptsList = new ArrayList<String>();
        for (String password:passwordsList) {
            BigInteger attemptNumber = getAttemptNumberForPassword(password);
            if (attemptNumber == null){ continue;}
            numberOfAttemptsList.add(attemptNumber.toString());
        }
        Collections.sort(numberOfAttemptsList, new LengthFirstComparator());

        writeArrayToFile(generatedFileName,numberOfAttemptsList);
    }


    public void updatingAttack(String generatedFileName){
        ArrayList<String> numberOfAttemptsList = new ArrayList<String>();

        /*ArrayList<CharNumPair> updatingFrequencyList = new ArrayList<>();

        for (CharNumPair pair:frequencyList) {
            updatingFrequencyList.add(new CharNumPair(pair.charValue, pair.numValue));
        }*/

        ArrayList<String> passwordsList = PasswordReader.getPasswordsFromFile(filePath);
        Collections.sort(passwordsList, new PasswordSortingComparator(frequencyList));
        updateUsedFrequencyArray(passwordsList.get(0));

        if (getAttemptNumberForPassword(passwordsList.get(0)) != null) {
            numberOfAttemptsList.add(getAttemptNumberForPassword(passwordsList.get(0)).toString());
        }

        for (int i = 1; i < passwordsList.size(); i++){
            if (passwordsList.get(i).length() > passwordsList.get(i - 1).length()){
                updateFrequncyList(0);
                //Collections.sort(passwordsList, new PasswordSortingComparator(frequencyList));
            }
            else if (passwordsList.get(i).charAt(0) != passwordsList.get(i - 1).charAt(0)){
                updateFrequncyList(i);

                //Collections.sort(passwordsList, new PasswordSortingComparator(frequencyList));
            }
            updateUsedFrequencyArray(passwordsList.get(i));
            BigInteger attemptNumber = getAttemptNumberForPassword(passwordsList.get(i));
            if (attemptNumber == null){ continue;}
            numberOfAttemptsList.add(attemptNumber.toString());
        }
        Collections.sort(numberOfAttemptsList, new LengthFirstComparator());
        writeArrayToFile(generatedFileName,numberOfAttemptsList);
    }

    private BigInteger getAttemptNumberForPassword(String password){
        BigInteger attemptNumber = new BigInteger("1");
        BigInteger previousAttempts = new BigInteger("1");
        for (int i = password.length() - 1; i >= 0; i--){

            int index = characterList.indexOf(password.charAt(i));

            if (index != -1){
                attemptNumber = attemptNumber.add(new BigInteger(String.valueOf(index)).multiply(previousAttempts));
                previousAttempts = previousAttempts.multiply(new BigInteger(String.valueOf(characterList.size())));
            }
            else {
                attemptNumber = new BigInteger("-1");
                break;
            }
        }


        if (!attemptNumber.equals(new BigInteger("-1"))){

            previousAttempts = previousAttempts.divide(new BigInteger(String.valueOf(characterList.size())));
            attemptNumber = attemptNumber.add(previousAttempts);
            return attemptNumber;
        }
        return null;
    }

    private void updateUsedFrequencyArray(String password){
        for (char character:password.toCharArray()) {
            int index = Arrays.binarySearch(usedCharacterArray, character);
            if (index >= 0){
                usedFrequencyArray[index] += 1;
            }
        }
    }

    private void updateFrequncyList(int firstIndex){
        for (int i = 0; i < usedCharacterArray.length; i++) {
            for (CharNumPair pair:frequencyList) {
                if (pair.charValue == usedCharacterArray[i]){
                    pair.numValue += usedFrequencyArray[i];
                    usedFrequencyArray[i] = 0;
                    break;
                }
            }
        }

        for (int i = firstIndex; i < frequencyList.size() - 1; i++){
            if (frequencyList.get(i).numValue < frequencyList.get(i + 1).numValue){
                for (int j = i + 1; j > firstIndex; j--){
                    if (frequencyList.get(j).numValue > frequencyList.get(j-1).numValue){
                        Collections.swap(frequencyList, j, j-1);
                        Collections.swap(characterList, j, j-1);
                    }
                    else{
                        break;
                    }
                }
            }
        }
    }

    private void writeArrayToFile(String generatedFileName, ArrayList<String> numberOfAttempts){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(generatedFileName, false));
            for (int i = 0; i < numberOfAttempts.size(); i++){
                writer.write(String.valueOf(i) + ";" + numberOfAttempts.get(i));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


}
