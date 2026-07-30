#include <iostream>
#include <string>
#include <cmath>
#include <cstdint>
#include <vector>
using namespace std;

namespace Functions {
    unsigned char unsign(signed char a){
        return (a & 0xFF);
    }

    signed char sign(unsigned char a){
        signed char b = a;
        return b;
    }

    int convert(signed char a){
        int b = a;
        return b;
    }

    int convert(unsigned char a){
        int b = a;
        return b;
    }
}

namespace Opcodes {
    const signed char NOP = 0;
    const signed char STA = 16;
    const signed char LDA = 32;
    const signed char ADD = 48;
    const signed char OR = 64;
    const signed char AND = 80;
    const signed char NOT = 96;
    const signed char JMP = 128;
    const signed char JN = 144;
    const signed char JZ = 160;
    const signed char HLT = 240;
}

namespace Commands {
    const string GET = "get";
    const string SET = "set";
    const string READ = "read";
    const string WRITE = "write";
    const string RUN = "run";
    const string CLEAR = "clear";
    const string QUIT = "quit";
    const string AC = "ac";
    const string FN = "fn";
    const string FZ = "fz";
}

using namespace Functions;

class Mem{
    private:
        signed char memory[256];
    public:
        Mem(){
            for (int i = 0; i < 256; i++){memory[i] = 0;}
            memory[127] = 240;
            memory[255] = 240;
        }

        void setAddress(int address, signed char value){
            memory[address] = value;
        }

        signed char getAddress(int address){
            return memory[address];
        }

        void clear(){
            for (int i = 0; i < 256; i++){setAddress(i, 0);}
        }

        void writeMemory(int start, vector<signed char> data){
            for (int i = start; i < data.size(); i++){
                setAddress(start+i, data[i]);
            }
        }

        void readMemory(int start, int end){
            for (int i = start; i <= end; i++){
                if (i < 128){
                    cout << i << ": " << convert(unsign(getAddress(i))) << "\n";
                } else {
                    cout << i << ": " << convert(getAddress(i)) << "\n";
                }
            }
        }
};

class Kernel{
    private:
        signed char ac;
        int pc;
        bool flagN;
        bool flagZ;
        bool running;

        void execute(signed char current, int next){
            using namespace Opcodes;
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

        void updateNZ(){
            if (ac == 0){
                flagZ = true;
            } else if (ac < 0){
                flagN = true;
            } else {
                flagN = false;
                flagZ = false;
            }
        }

        void cmdSTA(int address){
            ram.setAddress(address, ac);
            pc++;
        }

        void cmdLDA(int address){
            ac = ram.getAddress(address);
            pc++;
        }

        void cmdADD(int address){
            ac += ram.getAddress(address);
            pc++;
        }

        void cmdOR(int address){
            ac = ac | ram.getAddress(address);
            pc++;
        }

        void cmdAND(int address){
            ac = ac & ram.getAddress(address);
            pc++;
        }

        void cmdNOT(){
            ac = !ac;
        }

        void cmdJMP(int address){
            pc = address;
        }

        void cmdJN(int address){
            if (flagN){pc = address;}
        }

        void cmdJZ(int address){
            if (flagZ){pc = address;}
        }

        void cmdHLT(){
            running = false;
        }

    public:
        Mem ram;

        Kernel(){
            Mem ram;
        }

        signed char getAC(){
            return ac;
        }

        void runProgram(){
            int next;
            signed char current;

            running = true;
            ac = 0;
            pc = 0;

            while (running){
                current = ram.getAddress(pc);

                if (pc+1 >= 256){
                    next = 0;
                } else {
                    next = unsign(ram.getAddress(pc+1));
                }

                updateNZ();

                execute(current, next);

                pc++;
            }
        }

};

class Shell{
    public:
        Kernel os;
        bool condition;

        Shell(){
            Kernel os;
            condition = false;
        }

        void prompt(){
            string cmd;

            cout << ">";
            cin >> cmd;
            cout << "\n";

            using namespace Commands;

            if (cmd == GET){

                shlGET();

            } else if (cmd == SET){

                shlSET();

            } else if (cmd == READ){

                shlREAD();

            } else if (cmd == WRITE){

                shlWRITE();

            } else if (cmd == RUN){

                shlRUN();

            } else if (cmd == CLEAR){

                shlCLEAR();

            } else if (cmd == QUIT){

                shlQUIT();

            } else if (cmd == AC){

                shlAC();

            } else if (cmd == FN){

                shlFN();

            } else if (cmd == FZ){

                shlFZ();

            }
        }

        void shlGET(){
            int adr;

            cout << ">>";
            cin >> adr;
            cout << "\n";

            if (adr < 128){
                cout << convert(unsign(os.ram.getAddress(adr))) << "\n";
            } else {
                cout << convert(os.ram.getAddress(adr)) << "\n";
            }

            cin.ignore();
        }

        void shlSET(){
            int adr;
            int val;

            cout << ">>";
            cin >> adr;
            cout << "\n";

            cout << ">>";
            cin >> val;
            cout << "\n";

            os.ram.setAddress(adr, val);

            cout << "\n";
            cin.ignore();
        }

        void shlREAD(){
            int start;
            int end;

            cout << ">>";
            cin >> start;
            cout << "\n";

            cout << ">>";
            cin >> end;
            cout << "\n";

            os.ram.readMemory(start, end);
            cin.ignore();
        }

        void shlWRITE(){
            vector<signed char> data;
            int start;
            int num;

            cout << ">>";
            cin >> start;
            cout << "\n";

            while (true){
                cout << ">>>";
                cin >> num;
                cout << "\n";

                if (num == -1){
                    break;
                } else {
                    data.push_back(num);
                }
            }

            os.ram.writeMemory(start, data);

            cin.ignore();
        }

        void shlRUN(){
            os.runProgram();
        }

        void shlCLEAR(){
            os.ram.clear();
        }

        void shlQUIT(){
            condition = true;
        }

        void shlAC(){
            cout << convert(os.getAC()) << "\n";
        }

        void shlFN(){
            cout << (convert(os.getAC()) < 0) << "\n";
        }

        void shlFZ(){
            cout << (convert(os.getAC()) == 0) << "\n";
        }
};

int main(){
    cout << "Neander v1.2 (c++)\nCompiled executable for the Neander Virtual Machine.\nCreated by Prof. Raul F. Weber\nImplemented by Caio Persch Espindola\n\n";
    
    Shell ui;

    while (!ui.condition){
        ui.prompt();
    }

    return 0;
}