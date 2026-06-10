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

        while (!ui.c){
            ui.prompt();
        }
        
    }

    public void prompt(){

        System.out.print("$");
        String cmd = input.nextLine();
        System.out.println("");
        
        if (cmd.equals("get")){
            
            shlGET();}

        else if (cmd.equals("set")){
            
            shlSET();}

        else if (cmd.equals("read")){
            
            shlREAD();}

        else if (cmd.equals("write")){
            
            shlWRITE();}

        else if (cmd.equals("run")){

            shlRUN();

        } else if (cmd.equals("clear")){
            
            shlCLEAR();

        } else if (cmd.equals("quit")){

            shlQUIT();

        } else if (cmd.equals("ac")){

            shlAC();

        } else if (cmd.equals("fn")){

            shlFN();

        } else if (cmd.equals("fz")){

            shlFZ();

        }
    }

    public void shlGET(){

        System.out.print("$$");
        int adr = input.nextInt();

        if (adr < 128)
            System.out.println(Mem.unsign(os.ram.getAddress(adr)));
        else {
            System.out.println(os.ram.getAddress(adr));
        }

        input.nextLine(); //clears buffer

    }

    public void shlSET(){

        System.out.print("$$");
        int adr = input.nextInt();

        System.out.print("$$");
        byte val = Mem.sign(input.nextInt());

        os.ram.setAddress(adr, val);

        System.out.println("");

        input.nextLine(); //clears buffer

    }

    public void shlREAD(){

        System.out.print("$$");
        int start = input.nextInt();

        System.out.print("$$");
        int end = input.nextInt();

        os.ram.readMemory(start, end);

        input.nextLine(); //clears buffer

    }

    public void shlWRITE(){

        System.out.print("$$");
        int start = input.nextInt();

        int num;
        ArrayList<Integer> data = new ArrayList<Integer>();
            
        while (true) { 
            System.out.print("$$$");
            num = input.nextInt();
                
            if (num < 0) {break;}

            data.add(num);
        }
            
        os.ram.writeToMemory(start, data);

        input.nextLine(); //clears buffer

    }

    public void shlRUN(){

        long time = System.nanoTime();

        os.runProgram();

        System.out.println("Run Time: " + ((System.nanoTime() - time)/1000000) + " ms");

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
}