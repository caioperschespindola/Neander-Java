import java.util.ArrayList;
import java.util.Scanner;

public class Shell{
    Scanner input = new Scanner(System.in);
    boolean c;
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

            System.out.print("$$");
            int adr = input.nextInt();
            System.out.println(os.ram.getAddress(adr));

        } else if (cmd.equals("set")){

            System.out.print("$$");
            byte val = Mem.sign(input.nextInt());
            System.out.print("$$");
            int adr = input.nextInt();
            os.ram.setAddress(val, adr);
            System.out.println("");

        } else if (cmd.equals("read")){

            System.out.print("$$");
            int start = input.nextInt();
            System.out.print("$$");
            int end = input.nextInt();
            os.ram.readMemory(start, end);

        } else if (cmd.equals("write")){

            System.out.print("$$");
            int start = input.nextInt();

            int num;
            ArrayList<Integer> data = new ArrayList<Integer>();
            
            while (true) { 
                System.out.print("$$$");
                num = input.nextInt();
                
                if (num == -1) {break;}

                data.add(num);
            }
            
            os.ram.writeToMemory(start, data);

        } else if (cmd.equals("run")){

            os.runProgram();

        } else if (cmd.equals("clear")){
            
            os.ram.clear();

        } else if (cmd.equals("quit")){

            c = true;

        } else if (cmd.equals("ac")){

            System.out.println(os.getAC());

        } else if (cmd.equals("fn")){

            System.out.println(os.getAC() < 0);

        } else if (cmd.equals("fz")){

            System.out.println(os.getAC() == 0);

        }
    }
}