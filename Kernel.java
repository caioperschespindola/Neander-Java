import java.util.ArrayList;

public class Kernel {
    private byte ac = Mem.sign(0); //Acumulator register
    
    Mem ram;
    
    private int pc; //Current line to be executed
    private int mc; //Current memory partition
    private int fc; //Function call register
    
    private boolean flagN = (ac < 0);
    private boolean flagZ = (ac == 0);
    
    private boolean running;
    
    public static final byte NOP = (byte) 0; //No operation
    public static final byte STA = (byte) 16; //Stores AC in address
    public static final byte LDA = (byte) 32; //Loads address to AC
    public static final byte ADD = (byte) 48; //Adds address to AC
    public static final byte OR = (byte) 64; //Bitwise OR with AC and address
    public static final byte AND = (byte) 80; //Bitwise AND with AC and address
    public static final byte NOT = (byte) 96; //Bitwise NOT on AC
    public static final byte JMP =  (byte) 128; //Loads address to PC
    public static final byte JN = (byte) 144; //Loads address to PC if AC is negative
    public static final byte JZ = (byte) 160; //Loads address to PC if AC is zero
    public static final byte JPA = (byte) 176; //Loads first address to MC and second to PC
    public static final byte DEF = (byte) 192; //Defines a function with a name that starts at an address
    public static final byte RET = (byte) 208; //Loads address stored in FC back to PC
    public static final byte FF = (byte) 224;
    public static final byte HLT = (byte) 240; //Stops execution

    public static final byte[] OPCODES = new byte[]{NOP, STA, LDA, ADD, OR, AND, NOT, JMP, JN, JZ, JPA, DEF, RET, FF, HLT};
    private static ArrayList<byte[]> functions = new ArrayList<>();


    public Kernel(){
        ram = new Mem();
    }

    public byte getAC(){
        return ac;
    }

    public int getMC(){
        return mc;
    }

    public void runProgram(){
        int third;
        int next;
        byte current;

        running = true;
        pc = 0;
        ac = 0;
        mc = 0;
        
        for (pc = 0; running == true; pc++){
            
            if (pc == 255) //Auto halt to avoid index error
                {cmdHLT();}

            current = ram.getAddress(mc, pc);   
            next = Mem.unsign(ram.getAddress(mc, pc+1)); //Address for 2-byte commands
            third = Mem.unsign(ram.getAddress(mc, pc+2)); //Address for 3-byte commands
            
            updateNZ();
            
            execute(current, next, third);

        }
    }

    private void updateNZ(){
        if (ac == 0){
            flagZ = true;
        } if (ac < 0){
            flagN = true;
        } if (ac > 0) {
            flagZ = false;
            flagN = false;
        }
    }

    private void execute(byte current, int next, int third){
        switch (current){
            case NOP:
                break;
            case STA:
                cmdSTA(next);
                break;
            case LDA:
                cmdLDA(next);
                break;
            case ADD:
                cmdADD(next);
                break;
            case OR:
                cmdOR(next);
                break;
            case AND:
                cmdAND(next);
                break;
            case NOT:
                cmdNOT();
                break;
            case JMP:
                cmdJMP(next);
                break;
            case JN:
                cmdJN(next);
                break;
            case JZ:
                cmdJZ(next);
                break;
            case JPA:
                cmdJPA(next, third);
                break;
            case DEF:
                cmdDEF(next, third);
                break;
            case RET:
                cmdRET();
                break;
            case HLT:
                cmdHLT();
                break;
            default:
                functionCheck(current);
                break;
        }
    }

    private void cmdSTA(int address){

        ram.setAddress(mc, address, ac);
        pc = pc+1;

    }

    private void cmdLDA(int address){

        ac = ram.getAddress(mc, address);
        pc = pc+1;

    }

    private void cmdADD(int address){

        ac = Mem.sign(ac + ram.getAddress(mc, address));
        pc = pc+1;

    }

    private void cmdOR(int address){

        ac = Mem.sign(ac | ram.getAddress(mc, address));
        pc = pc+1;

    }

    private void cmdAND(int address){

        ac = Mem.sign(ac & ram.getAddress(mc, address));
        pc = pc+1;

    }

    private void cmdNOT(){

        ac = Mem.sign(~(ac));

    }

    private void cmdJMP(int address){

        pc = address;

    }

    private void cmdJN(int address){

        if (flagN){
            cmdJMP(address);
        }
    }

    private void cmdJZ(int address){

        if (flagZ){
            cmdJMP(address);
        }
    }

    private void cmdJPA(int address_1, int address_2){

        cmdJMP(address_2);
        mc = address_1;

    }

    private void cmdDEF(int address_1, int address_2){

        functions.add(new byte[]{Mem.sign(address_1), Mem.sign(address_2)});
        pc = pc+2;

    }

    private void cmdRET(){

        cmdJMP(fc);

    }

    private void cmdHLT(){

        running = false;

    }

    private void functionCheck(int code){

        for (int f = 0; f < functions.size(); f++){

            if (functions.get(f)[0] == code){

                fc = pc+1;
                cmdJMP(functions.get(f)[1]);

            }
        }
    }
}