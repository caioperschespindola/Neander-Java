

public class Kernel {
    private byte ac = Mem.sign(0); //Acumulator register
    
    Mem ram;
    
    private int pc; //Current line to be executed
    
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
    public static final byte HLT = (byte) 240; //Stops execution

    public Kernel(){
        ram = new Mem();
    }

    public byte getAC(){
        return ac;
    }

    public void runProgram(long time){
        
        int next;
        byte current;

        running = true;
        pc = 0;
        ac = 0;
        
        for (pc = 0; running == true; pc++){

            current = ram.getAddress(pc);
            
            if (pc+1 >= ram.diskSize){

                next = 0; //Avoids index error

            } else {

                next = Mem.unsign(ram.getAddress(pc+1)); //Address for 2-byte commands

            }
            
            updateNZ();
            
            execute(current, next);

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

    private void execute(byte current, int next){
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
            case HLT:
                cmdHLT();
                break;
            default:
                break;
        }
    }

    private void cmdSTA(int address){
        ram.setAddress(address, ac);
        pc = pc+1;
    }

    private void cmdLDA(int address){
        ac = ram.getAddress(address);
        pc = pc+1;
    }

    private void cmdADD(int address){
        ac = Mem.sign(ac + ram.getAddress(address));
        pc = pc+1;
    }

    private void cmdOR(int address){
        ac = Mem.sign(ac | ram.getAddress(address));
        pc = pc+1;
    }

    private void cmdAND(int address){
        ac = Mem.sign(ac & ram.getAddress(address));
        pc = pc+1;
    }

    private void cmdNOT(){
        ac = Mem.sign(~(ac));
    }

    private void cmdJMP(int address){
        pc = address - 1;
    }

    private void cmdJN(int address){
        if (flagN){
            pc = address -1;
        } else {
            pc++;
        }
    }

    private void cmdJZ(int address){
        if (flagZ){
            pc = address -1;
        } else {
            pc++;
        }
    }

    private void cmdHLT(){
        running = false;
    }
}