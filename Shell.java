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

            c = true;

        } else if (cmd.equals("ac")){

            System.out.println(os.getAC());

        } else if (cmd.equals("fn")){

            System.out.println(os.getAC() < 0);

        } else if (cmd.equals("fz")){

            System.out.println(os.getAC() == 0);

        } else if (cmd.equals("mc")){

            System.out.println(os.getMC());

        }
    }

    public void shlGET(){

        System.out.print("$$");
        int par = input.nextInt();

        System.out.print("$$");
        int adr = input.nextInt();

        System.out.println(os.ram.getAddress(par, adr));
        
        input.nextLine(); //clears buffer

    }

    public void shlSET(){

        System.out.print("$$");
        int par = input.nextInt();

        System.out.print("$$");
        int adr = input.nextInt();

        System.out.print("$$");
        byte val = Mem.sign(input.nextInt());

        os.ram.setAddress(par, adr, val);

        System.out.println("");

        input.nextLine(); //clears buffer

    }

    public void shlREAD(){

        System.out.print("$$");
        int par = input.nextInt();

        System.out.print("$$");
        int start = input.nextInt();

        System.out.print("$$");
        int end = input.nextInt();

        os.ram.readMemory(par, start, end);

        input.nextLine(); //clears buffer

    }

    public void shlWRITE(){

        System.out.print("$$");
        int par = input.nextInt();

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
            
        os.ram.writeToMemory(par, start, data);

        input.nextLine(); //clears buffer

    }

    public void shlCLEAR(){
        
        System.out.print("$$");
        int par = input.nextInt();
        
        os.ram.clear(par);

        input.nextLine(); //clears buffer
    }

    public void shlRUN(){

        os.runProgram();

    }
}