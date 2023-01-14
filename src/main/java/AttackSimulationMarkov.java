import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AttackSimulationMarkov {
    ArrayList<ArrayList<CharNumPair>> markovChainList;
    char[] characterOrder = " !#$&*,-./0123456789;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray(); //Presledk označuje začetno stanje
    String filePath;

    public AttackSimulationMarkov(ArrayList<ArrayList<CharNumPair>> markovChainList, String filePath) {
        this.markovChainList = markovChainList;
        this.filePath = filePath;
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

    public void UpdatingAttack(String generatedFileName){
        ArrayList<String> numberOfAttemptsList = new ArrayList<String>();

        ArrayList<String> passwordsList = PasswordReader.getPasswordsFromFile(filePath);
        Collections.sort(passwordsList, new PasswordSortingComparator(markovChainList.get(0)));

        if (getAttemptNumberForPassword(passwordsList.get(0)) != null){
            updateMarkovArray(passwordsList.get(0));
            numberOfAttemptsList.add(getAttemptNumberForPassword(passwordsList.get(0)).toString());
        }

        for (int i = 1; i < passwordsList.size(); i++){
            if (passwordsList.get(i).length() > passwordsList.get(i - 1).length()){
                sortMarkovByFrequency(0);
                Collections.sort(passwordsList, new PasswordSortingComparator(markovChainList.get(0)));
            }
            else if (passwordsList.get(i).charAt(0) != passwordsList.get(i - 1).charAt(0)){
                sortMarkovByFrequency(1);
            }

            BigInteger attemptNumber = getAttemptNumberForPassword(passwordsList.get(i));
            if (attemptNumber == null){continue;}
            updateMarkovArray(passwordsList.get(i));
            numberOfAttemptsList.add(attemptNumber.toString());
        }

        Collections.sort(numberOfAttemptsList, new LengthFirstComparator());
        writeArrayToFile(generatedFileName,numberOfAttemptsList);
    }

    private void updateMarkovArray(String password){
        int index = getIndexOfChar(markovChainList.get(0), password.charAt(0));
        markovChainList.get(0).get(index).numValue += 1;
        for (int i = 1; i < password.length(); i++) {
            int indexPrejsnjega = Arrays.binarySearch(characterOrder, password.charAt(i - 1));
            if (indexPrejsnjega >= 0){
                index = getIndexOfChar(markovChainList.get(indexPrejsnjega), password.charAt(i));
                markovChainList.get(indexPrejsnjega).get(index).numValue += 1;
            }
            else{break;}

        }
    }

    private void sortMarkovByFrequency(int index){
        if (index == 0){
            markovChainList.set(0, FrequencyAnalysis.sortByFrequencyNoNormalise(markovChainList.get(0)));
        }
        for (int i = 1; i < markovChainList.size(); i++){
            markovChainList.set(i, FrequencyAnalysis.sortByFrequencyNoNormalise(markovChainList.get(i)));
        }
    }

    private BigInteger getAttemptNumberForPassword(String password){
        BigInteger attemptNumber = new BigInteger("1");
        BigInteger previousAttempts = new BigInteger("1");

        int index = getIndexOfChar(markovChainList.get(0), password.charAt(0));

        for (int i = password.length() - 1; i >= 1; i--){

            int indexPrejsnjega = Arrays.binarySearch(characterOrder, password.charAt(i - 1));
            if (indexPrejsnjega >= 0) {
                index = getIndexOfChar(markovChainList.get(indexPrejsnjega), password.charAt(i));
            }
            else {
                attemptNumber = new BigInteger("-1");
                break;
            }

            if (index != -1){
                attemptNumber = attemptNumber.add(new BigInteger(String.valueOf(index)).multiply(previousAttempts));
                previousAttempts = previousAttempts.multiply(new BigInteger(String.valueOf(markovChainList.size())));
            }
            else {
                attemptNumber = new BigInteger("-1");
                break;
            }
        }



        if (!attemptNumber.equals(new BigInteger("-1"))){

            index = getIndexOfChar(markovChainList.get(0), password.charAt(0));
            if (index == -1){return null;}
            attemptNumber = attemptNumber.multiply(new BigInteger(String.valueOf(index + 1)));

            attemptNumber = attemptNumber.add(previousAttempts);
            return attemptNumber;
        }
        return null;
    }

    private int getIndexOfChar(ArrayList<CharNumPair> array, char character){
        int result = -1;
        for (int i = 0; i < array.size(); i++){
            if (array.get(i).charValue == character){
                result = i;
                break;
            }
        }
        return result;
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
