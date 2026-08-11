import java.util.ArrayList;
import java.util.Scanner;

public class Shell{

    private Scanner input = new Scanner(System.in);
    private boolean c; //condition for closing the program

    static Shell ui;
    Kernel os;

    public Shell(){
        os = new Kernel();
    }

    public static void main(String args[]){

        ui = new Shell();

        ui.c = false;

        System.out.println("Neander v1.3\nCompiled .jar for the Neander Virtual Machine.\nCreated by Prof. Raul F. Weber\nImplemented by Caio Persch Espindola\n\n");

        while (!ui.c){
            try {

                ui.prompt();

            } catch(Exception e) {

                System.out.println("Error: " + e.getMessage());

            }
        }
        
    }

    public void prompt(){

        System.out.print(">");
        String cmdfull = input.nextLine();
        String[] cmd = cmdfull.split("[\s]");
        System.out.println("");
        
        if (cmd[0].equals("get")){
            
            shlGET(cmd[1]);}

        else if (cmd[0].equals("set")){
            
            shlSET(cmd[1], cmd[2]);}

        else if (cmd[0].equals("read")){
            
            shlREAD(cmd[1], cmd[2]);}

        else if (cmd[0].equals("write")){
            
            shlWRITE(cmd[1]);}

        else if (cmd[0].equals("run")){

            shlRUN();

        } else if (cmd[0].equals("clear")){
            
            shlCLEAR();

        } else if (cmd[0].equals("quit")){

            shlQUIT();

        } else if (cmd[0].equals("ac")){

            shlAC();

        } else if (cmd[0].equals("fn")){

            shlFN();

        } else if (cmd[0].equals("fz")){

            shlFZ();

        } else if (cmd[0].equals("opcodes")) {
            
            shlOPCODES();

        } else if (cmd[0].equals("help")) {
            
            shlHELP();

        }
    }

    public void shlGET(String address){

        int adr = Integer.parseInt(address);

        if (adr < 128)
            System.out.println(Mem.unsign(os.ram.getAddress(adr)));
        else {
            System.out.println(os.ram.getAddress(adr));
        }

    }

    public void shlSET(String address, String value){

        int adr = Integer.parseInt(address);
        byte val = Mem.sign(Integer.parseInt(value));

        os.ram.setAddress(adr, val);

    }

    public void shlREAD(String start, String end){

        int st = Integer.parseInt(start);
        int nd = Integer.parseInt(end);

        os.ram.readMemory(st, nd);

    }

    public void shlWRITE(String start){

        int st = Integer.parseInt(start);

        int num;
        ArrayList<Integer> data = new ArrayList<Integer>();

        int i = st;
            
        while (true) { 
            System.out.print(i + " >> ");
            num = input.nextInt();
                
            if (num < 0) {break;}

            data.add(num);

            i++;
        }
        
        os.ram.writeToMemory(st, data);

        input.nextLine(); //clears buffer

    }

    public void shlRUN(){

        long time = System.nanoTime();

        os.runProgram(time);

        System.out.println("Run Time: " + (double)((System.nanoTime() - time)/1000000.0) + " ms");

    }

    public void shlCLEAR(){

        os.ram.clear();

    }

    public void shlQUIT(){

        c = true;

    }

    public void shlAC(){

        System.out.println(os.getAC());

    }

    public void shlFN(){

        System.out.println(os.getAC() < 0);

    }

    public void shlFZ(){

        System.out.println(os.getAC() == 0);

    }

    public void shlOPCODES(){

        System.out.println("""
            NOP =  0;  //No operation
            STA = 16;  //Stores AC in address
            LDA = 32;  //Loads address to AC
            ADD = 48;  //Adds address to AC
            OR  = 64;  //Bitwise OR with AC and address
            AND = 80;  //Bitwise AND with AC and address
            NOT = 96;  //Bitwise NOT on AC
            JMP = 128; //Loads address to PC
            JN  = 144; //Loads address to PC if AC is negative
            JZ  = 160; //Loads address to PC if AC is zero
            HLT = 240; //Stops execution""");

    }

    public void shlHELP(){

        System.out.println("""
            Neander Shell commands:

            get:     receives a memory address and return the value stored in that address.
            set:     receives an address and a value and updates the memory accordingly.
            read:    receives a start address and an end address and returns memory addresses and values in that range.
            write:   receives a starting address and an arbitrary ammount of values, and writes them in memory. stops if a negative number is entered.
            run:     runs the program and displays its run time.
            clear:   sets all memory addresses to zero. WARNING: this will also remove the default halt commands on addreses 127 and 255.
            ac:      returns the current value stored in the accumulator.
            fn:      returns the current value stored in the Negative Flag.
            fz:      returns the current value stored in the Zero Flag.
            opcodes: displays all opcodes.
            help:    displays all shell commands.
            quit:    ends the Neander Program.""");

    }
}