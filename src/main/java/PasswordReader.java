import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PasswordReader {

    public static ArrayList<String> getPasswordsFromFile(String potDatoteke){
        BufferedReader reader;
        ArrayList<String> passwords = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(potDatoteke));
            String line = reader.readLine();

            while (line != null){
                String[] splitLine = line.split(":");
                if(splitLine.length == 2 && splitLine[1].length() > 3){
                    passwords.add(splitLine[1]);
                }


                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return passwords;
    }
}
