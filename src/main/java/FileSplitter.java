import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class FileSplitter {

    public static void splitFileByLength(String filePath){
        BufferedReader reader;
        BufferedWriter writer;
        String passwordLength = "1";
        BigInteger limit = new BigInteger("0");

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            writer = new BufferedWriter(new FileWriter(filePath.split("\\.")[0] + "/" + passwordLength + ".txt", false));


            while (line != null){

                if(new BigInteger(line.split(";")[1]).compareTo(limit) == -1){
                    writer.write(line);
                    writer.newLine();
                    line = reader.readLine();
                }
                else{
                    writer.close();
                    passwordLength = String.valueOf(Integer.valueOf(passwordLength) + 1);
                    limit = limit.add(new BigInteger("1")).multiply(new BigInteger("75")).subtract(new BigInteger("1"));
                    writer = new BufferedWriter(new FileWriter(filePath.split("\\.")[0] + "/" + passwordLength + ".txt", false));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
