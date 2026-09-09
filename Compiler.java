import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler{

    private static final String[] opcodes = {"NOP","STA","LDA","ADD","OR","AND","NOT", "JMP" , "JN" , "JZ" , "HLT"};
    private static final byte[] bytecodes = {  0  , 16  , 32  , 48  , 64 , 80  , 96  , -128  , -112 , -96  , -16  };

    public static void main(String[] args) {
        String filename = args[0];
        File src = new File(filename);

        String data = "";
        String[] data_array;
        ArrayList<String[]> data_matrix = new ArrayList<>();

        if (!src.exists()){ //Checks if the given filename exists
            System.out.println("No file found with name " + filename);
            System.exit(1);
        }

        try (Scanner input = new Scanner(src)){ //Reads all file data into a String array
            while(input.hasNextLine()){
                data += input.nextLine();
            }
            
        } catch (Exception e) { //Catches and prints file reading exceptions
            System.out.println(e);
            System.exit(1);
        }

        data_array = data.split("[\n]"); //Tokenizes data by line
        for(String line : data_array){ //Tokenizes data by word
            data_matrix.add(line.toUpperCase().split("[\s]"));
        }


    }
}