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

    // takes (-128 to +127) and converts to (0 to 255)
    public static int unsign(byte a){
        int b = (a & 0xFF);
        return b;
    }

     // takes (0 to 255) and converts to (-128 to +127)
    public static byte sign(int a){
        byte b = (byte) a;
        return b;
    }

    // assigns the given value to the address in the memory
    public void setAddress(int address, byte value){
        if (address < diskSize && address >= 0){
            memory[address] = sign(value);
        } else {
            throw new ArrayIndexOutOfBoundsException("Index " + address + " out of bounds for length " + diskSize);
        }
    }

    // returns the value stored in that address
    public byte getAddress(int address){
        return memory[address];
    }

    // sets all memory addresses to zero
    public void clear(){
        for (int i = 0; i < diskSize-1; i++){memory[i] = 0;}
        System.out.println("Memory cleared");
    }
    
    // writes each value in the given list to memory
    public void writeToMemory(int start, ArrayList<Integer> data){
        for (int i = 0; i < data.size()-1; i++){
            setAddress(start+i, sign(data.get(i)));
        }
    }

    // prints all the addresses between the two indexes
    public void readMemory(int start, int end){
        for (int i = start; i <= end; i++) {
            if (i<128)
                System.out.println(i + ": " + unsign(getAddress(i)));
            else
                System.out.println(i + ": " + getAddress(i));
        }
    }
}