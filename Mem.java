import java.util.ArrayList;

public class Mem {
    private byte[][] memory;
    final int diskSize = 256;
    
    public Mem(){
        memory = new byte[diskSize][diskSize];
        
        for (int i = 0; i < diskSize-1; i++){
            for (int j = 0; j < diskSize-1; j++){
                memory[i][j] = 0;
            }
        }
        
        memory[0][diskSize-1] = sign(240); //Default halt
    }

    public static int unsign(byte a){ // takes (-128 to +127) and converts to (0 to 255)
        int b = (a & 0xFF);
        return b;
    }

    public static byte sign(int a){ // takes (0 to 255) and converts to (-128 to +127)
        byte b = (byte) a;
        return b;
    }

    public void setAddress(int partition, int address, byte value){

        if (address < diskSize && address >= 0 && partition < diskSize && partition >= 0)
            memory[partition][address] = sign(value);

    }

    public byte getAddress(int partition, int address){
        
        return memory[partition][address];

    }

    public void clear(int partition){

        for (int i = 0; i < diskSize-1; i++)

            memory[partition][i] = 0;

        System.out.println("Memory cleared on partition " + partition);

    }
    
    public void writeToMemory(int partition, int start, ArrayList<Integer> data){

        for (int i = 0; i < data.size()-1; i++)
            setAddress(partition, start+i, sign(data.get(i)));
        
    }

    public void readMemory(int partition, int start, int end){
        for (int i = start; i <= end; i++) {
            System.out.println(i + ": " + getAddress(partition, i));
        }
    }
}