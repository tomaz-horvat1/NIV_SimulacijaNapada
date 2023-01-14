import java.io.*;
import java.math.BigInteger;

public class DataEditor {

    public static void splitFileByLength(String filePathDatabase, String filePathOriginal, String filePathUpdating, String destination){
        BufferedReader readerDatabase;
        BufferedReader readerOriginal;
        BufferedReader readerUpdating;
        BufferedWriter writer;
        String passwordLength = "0";
        BigInteger limit = new BigInteger("0");


        String writeLine = "";
        try {
            readerDatabase = new BufferedReader(new FileReader(filePathDatabase));
            readerOriginal = new BufferedReader(new FileReader(filePathOriginal));
            readerUpdating = new BufferedReader(new FileReader(filePathUpdating));
            String lineDatabase = readerDatabase.readLine();
            String lineOriginal = readerOriginal.readLine();
            String lineUpdating = readerUpdating.readLine();
            writer = new BufferedWriter(new FileWriter(destination + "/" + passwordLength + ".txt", false));


            while (lineDatabase != null){

                if(new BigInteger(lineDatabase.split(";")[1]).compareTo(limit) == -1){
                    writeLine = lineDatabase + ";" + lineOriginal.split(";")[1] + ";" + lineUpdating.split(";")[1];
                    BigInteger database = new BigInteger(lineDatabase.split(";")[1]);
                    BigInteger original = new BigInteger(lineOriginal.split(";")[1]);
                    BigInteger updating = new BigInteger(lineUpdating.split(";")[1]);
                    if (!original.equals(database)){
                        writeLine += ";" + String.valueOf(updating.subtract(database).doubleValue()/(original.subtract(database)).multiply(new BigInteger(String.valueOf(original.compareTo(database)))).doubleValue());
                    }
                    else{writeLine += ";0";}
                    writeLine += ";"+updating.subtract(database);
                    writer.write(writeLine);
                    writer.newLine();
                    lineDatabase = readerDatabase.readLine();
                    lineOriginal = readerOriginal.readLine();
                    lineUpdating = readerUpdating.readLine();
                }
                else{
                    writer.close();
                    passwordLength = String.valueOf(Integer.valueOf(passwordLength) + 1);
                    limit = limit.add(new BigInteger("1")).multiply(new BigInteger("75")).subtract(new BigInteger("1"));
                    writer = new BufferedWriter(new FileWriter(destination + "/" + passwordLength + ".txt", false));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

