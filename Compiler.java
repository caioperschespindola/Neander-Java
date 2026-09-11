import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler{

    private static final String[] opcodes = {"NOP","STA","LDA","ADD","OR","AND","NOT", "JMP" , "JN" , "JZ" , "HLT"};
    private static final byte[] bytecodes = {  0  , 16  , 32  , 48  , 64 , 80  , 96  , -128  , -112 , -96  , -16  };

    static byte translate(String cmd){
        byte byte_code = 0;
        for (int i = 0; i < 11; i++){
            if (cmd.toUpperCase().equals(opcodes[i])){
                byte_code = bytecodes[i];
                break;
            }
        }

        return byte_code;
    }

    public static void main(String[] args) {
        String filename = args[0];
        File src = new File(filename);

        String data = "";
        String[] data_array;
        ArrayList<String[]> data_matrix = new ArrayList<>();
        ArrayList<Byte> compiled_code = new ArrayList<>();

        if (!src.exists()){ //Checks if the given filename exists
            System.err.println("Error: No file found with name " + filename);
            System.exit(1);
        }

        try (Scanner input = new Scanner(src)){ //Reads all file data into a String array
            while(input.hasNextLine()){
                data += input.nextLine();
            }
            
        } catch (Exception e) { //Catches and prints file reading exceptions
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }

        data_array = data.split("[\\n]"); //Tokenizes data by line

        for (String line : data_array){
            System.out.println(line);
        }

        for(String line : data_array){ //Tokenizes data by word
            data_matrix.add(line.split("[\s]"));
        }

        // for (String[] line : data_matrix){
        //     System.out.println(line[0]);
        //     System.out.println(line[1]);
        // }

        for (String[] line : data_matrix){ // reads all the words and appends the corresponding bytes to the list
            byte cmd = translate(line[0]);

            if (line.length > 1){ // 2-byte commands

                byte adr = (byte) Integer.parseInt(line[1]);

                compiled_code.add(cmd);
                compiled_code.add(adr);

            } else { // 1-byte commands
                compiled_code.add(cmd);
            }
        }

        try {
            FileWriter output = new FileWriter(".ndrbin");

            for (byte b : compiled_code){
                output.write(b + "\n");
            }

            output.close();

        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            System.exit(3);
        }

        System.out.println("Compilation Successful");
        System.exit(0);
    }
}