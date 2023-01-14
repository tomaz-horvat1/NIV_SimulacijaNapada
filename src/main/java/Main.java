import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        //frequencyAttack();
        markovAttack();

        DataEditor.splitFileByLength("src/main/resources/normalAttack1.txt","src/main/resources/normalAttackEnglish.txt","src/main/resources/updatingAttackEnglish.txt","src/main/resources/Frequency");
        DataEditor.splitFileByLength("src/main/resources/markovAttack1.txt","src/main/resources/markovAttack2.txt","src/main/resources/markovUpdatingAttack1.txt","src/main/resources/Markov");

    }

    private static void markovAttack(){
        ArrayList<String> passwords1 = PasswordReader.getPasswordsFromFile("src/main/resources/Database1.txt");
        ArrayList<ArrayList<CharNumPair>> markovList1 = MarkovAnalysis.markovAnalysis(passwords1);
        AttackSimulationMarkov markov1 = new AttackSimulationMarkov(markovList1, "src/main/resources/Database1.txt");
        markov1.NormalAttack("src/main/resources/markovAttack1.txt");
        FileSplitter.splitFileByLength("src/main/resources/markovAttack1.txt");

        ArrayList<String> passwords2 = PasswordReader.getPasswordsFromFile("src/main/resources/Database3.txt");
        ArrayList<ArrayList<CharNumPair>> markovList2 = MarkovAnalysis.markovAnalysis(passwords2);
        AttackSimulationMarkov markov2 = new AttackSimulationMarkov(markovList2, "src/main/resources/Database1.txt");

        markov2.NormalAttack("src/main/resources/markovAttack2.txt");
        FileSplitter.splitFileByLength("src/main/resources/markovAttack2.txt");

        markov2.UpdatingAttack("src/main/resources/markovUpdatingAttack1.txt");
        FileSplitter.splitFileByLength("src/main/resources/markovUpdatingAttack1.txt");
    }

    private static void frequencyAttack(){
        ArrayList<String> passwords1 = PasswordReader.getPasswordsFromFile("src/main/resources/Database1.txt");
        ArrayList<CharNumPair> charactersList1= FrequencyAnalysis.frequencyAnalysis(passwords1);

        AttackSimulationFrequency frequencyAttacker1 = new AttackSimulationFrequency(charactersList1, "src/main/resources/Database1.txt");
        frequencyAttacker1.NormalAttack("src/main/resources/normalAttack1.txt");
        FileSplitter.splitFileByLength("src/main/resources/normalAttack1.txt");

        //ArrayList<String> passwords2 = PasswordReader.getPasswordsFromFile("src/main/resources/Database2.txt");
        //ArrayList<CharNumPair> charactersList2= FrequencyAnalysis.frequencyAnalysis(passwords2);

        //AttackSimulationFrequency frequencyAttacker2 = new AttackSimulationFrequency(charactersList2, "src/main/resources/Database1.txt");
        //frequencyAttacker2.NormalAttack("src/main/resources/normalAttack2.txt");
        //FileSplitter.splitFileByLength("src/main/resources/normalAttack2.txt");

        ArrayList<CharNumPair> englishLanguageFrequency = generateEnglishFrequency();
        AttackSimulationFrequency frequencyAttacker3 = new AttackSimulationFrequency(englishLanguageFrequency, "src/main/resources/Database1.txt");
        frequencyAttacker3.NormalAttack("src/main/resources/normalAttackEnglish.txt");
        FileSplitter.splitFileByLength("src/main/resources/normalAttackEnglish.txt");
        frequencyAttacker3.updatingAttack("src/main/resources/updatingAttackEnglish.txt");
        FileSplitter.splitFileByLength("src/main/resources/updatingAttackEnglish.txt");
    }

    private static ArrayList<CharNumPair> generateEnglishFrequency(){
        ArrayList<CharNumPair> englishLanguageFrequency = new ArrayList<CharNumPair>();
        englishLanguageFrequency.add(new CharNumPair('e',11));
        englishLanguageFrequency.add(new CharNumPair('a',9));
        englishLanguageFrequency.add(new CharNumPair('r',8));
        englishLanguageFrequency.add(new CharNumPair('i',8));
        englishLanguageFrequency.add(new CharNumPair('o',7));
        englishLanguageFrequency.add(new CharNumPair('t',7));
        englishLanguageFrequency.add(new CharNumPair('n',7));
        englishLanguageFrequency.add(new CharNumPair('s',6));
        englishLanguageFrequency.add(new CharNumPair('l',6));
        englishLanguageFrequency.add(new CharNumPair('c',5));
        englishLanguageFrequency.add(new CharNumPair('u',4));
        englishLanguageFrequency.add(new CharNumPair('d',3));
        englishLanguageFrequency.add(new CharNumPair('p',3));
        englishLanguageFrequency.add(new CharNumPair('m',3));
        englishLanguageFrequency.add(new CharNumPair('h',3));
        englishLanguageFrequency.add(new CharNumPair('g',3));
        englishLanguageFrequency.add(new CharNumPair('b',2));
        englishLanguageFrequency.add(new CharNumPair('f',2));
        englishLanguageFrequency.add(new CharNumPair('y',2));
        englishLanguageFrequency.add(new CharNumPair('w',2));
        englishLanguageFrequency.add(new CharNumPair('k',1));
        englishLanguageFrequency.add(new CharNumPair('v',1));
        englishLanguageFrequency.add(new CharNumPair('x',1));
        englishLanguageFrequency.add(new CharNumPair('z',1));
        englishLanguageFrequency.add(new CharNumPair('j',1));
        englishLanguageFrequency.add(new CharNumPair('q',1));
        englishLanguageFrequency.add(new CharNumPair('E',1));
        englishLanguageFrequency.add(new CharNumPair('A',1));
        englishLanguageFrequency.add(new CharNumPair('R',1));
        englishLanguageFrequency.add(new CharNumPair('I',1));
        englishLanguageFrequency.add(new CharNumPair('O',1));
        englishLanguageFrequency.add(new CharNumPair('T',1));
        englishLanguageFrequency.add(new CharNumPair('N',1));
        englishLanguageFrequency.add(new CharNumPair('S',1));
        englishLanguageFrequency.add(new CharNumPair('L',1));
        englishLanguageFrequency.add(new CharNumPair('C',1));
        englishLanguageFrequency.add(new CharNumPair('U',1));
        englishLanguageFrequency.add(new CharNumPair('D',1));
        englishLanguageFrequency.add(new CharNumPair('P',1));
        englishLanguageFrequency.add(new CharNumPair('M',1));
        englishLanguageFrequency.add(new CharNumPair('H',1));
        englishLanguageFrequency.add(new CharNumPair('G',1));
        englishLanguageFrequency.add(new CharNumPair('B',1));
        englishLanguageFrequency.add(new CharNumPair('F',1));
        englishLanguageFrequency.add(new CharNumPair('Y',1));
        englishLanguageFrequency.add(new CharNumPair('W',1));
        englishLanguageFrequency.add(new CharNumPair('K',1));
        englishLanguageFrequency.add(new CharNumPair('V',1));
        englishLanguageFrequency.add(new CharNumPair('X',1));
        englishLanguageFrequency.add(new CharNumPair('Z',1));
        englishLanguageFrequency.add(new CharNumPair('J',1));
        englishLanguageFrequency.add(new CharNumPair('Q',1));
        englishLanguageFrequency.add(new CharNumPair('_',1));
        englishLanguageFrequency.add(new CharNumPair('@',1));
        englishLanguageFrequency.add(new CharNumPair('-',1));
        englishLanguageFrequency.add(new CharNumPair('/',1));
        englishLanguageFrequency.add(new CharNumPair('$',1));
        englishLanguageFrequency.add(new CharNumPair('!',1));
        englishLanguageFrequency.add(new CharNumPair('#',1));
        englishLanguageFrequency.add(new CharNumPair('&',1));
        englishLanguageFrequency.add(new CharNumPair('*',1));
        englishLanguageFrequency.add(new CharNumPair('?',1));
        englishLanguageFrequency.add(new CharNumPair(';',1));
        englishLanguageFrequency.add(new CharNumPair('.',1));
        englishLanguageFrequency.add(new CharNumPair(',',1));
        return englishLanguageFrequency;
    }
}

