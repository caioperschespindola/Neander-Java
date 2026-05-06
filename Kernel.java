public class Kernel {
    byte ac = Mem.sign(0);
    
    Mem ram;
    
    private int pc;
    
    private boolean flagN = (ac < 0);
    private boolean flagZ = (ac == 0);
    
    private boolean running;
    
    public static final byte NOP = (byte) 0;
    public static final byte STA = (byte) 16;
    public static final byte LDA = (byte) 32;
    public static final byte ADD = (byte) 48;
    public static final byte OR = (byte) 64;
    public static final byte AND = (byte) 80;
    public static final byte NOT = (byte) 96;
    public static final byte JMP =  (byte) 128;
    public static final byte JN = (byte) 144;
    public static final byte JZ = (byte) 160;
    public static final byte HLT = (byte) 240;

    public Kernel(){
        ram = new Mem();
    }

    public byte getAC(){
        return ac;
    }

    public void runProgram(){
        int next;
        byte current;

        running = true;
        pc = 0;
        ac = 0;
        
        for (pc = 0; running == true; pc++){
            current = ram.getAddress(pc);
            
            if (pc+1 >= ram.diskSize){
                next = 0;
            } else {
                next = Mem.unsign(ram.getAddress(pc+1));
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
        }
    }

    private void cmdSTA(int address){
        ram.setAddress(ac, address);
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
        pc = ram.getAddress(address);
    }

    private void cmdJN(int address){
        if (flagN){
            pc = ram.getAddress(address);
        }
    }

    private void cmdJZ(int address){
        if (flagZ){
            pc = ram.getAddress(address);
        }
    }

    private void cmdHLT(){
        running = false;
    }
}