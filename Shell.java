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
            
        while (true) { 
            System.out.print(">>");
            num = input.nextInt();
                
            if (num < 0) {break;}

            data.add(num);
        }
        
        os.ram.writeToMemory(st, data);

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