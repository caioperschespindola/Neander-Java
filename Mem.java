import java.util.ArrayList;

public class Mem {
    private byte[] memory;
    final int diskSize = 256;
    
    public Mem(){
        memory = new byte[diskSize];
        
        for (int i = 0; i < diskSize-1; i++){memory[i] = 0;}
        
        int halfway = diskSize / 2;
        memory[halfway-1] = sign(240); //Default halt
        memory[diskSize-1] = sign(240); //Default halt
    }

    public static int unsign(byte a){ // takes (-128 to +127) and converts to (0 to 255)
        int b = (a & 0xFF);
        return b;
    }

    public static byte sign(int a){ // takes (0 to 255) and converts to (-128 to +127)
        byte b = (byte) a;
        return b;
    }

    public void setAddress(int address, byte value){
        if (address < diskSize && address >= 0)
            memory[address] = sign(value);
    }

    public byte getAddress(int address){
        return memory[address];
    }

    public void clear(){
        for (int i = 0; i < diskSize-1; i++){memory[i] = 0;}
        System.out.println("Memory cleared");
    }
    
    public void writeToMemory(int start, ArrayList<Integer> data){
        for (int i = 0; i < data.size()-1; i++){
            setAddress(start+i, sign(data.get(i)));
        }
    }

    public void readMemory(int start, int end){
        for (int i = start; i <= end; i++) {
            System.out.println(i + ": " + getAddress(i));
        }
    }
}