import java.util.Stack;

//Стековый компилятор формул.
public class Compf extends Stack<Character>{
    //Типы символов (скобки, знаки операций, иное).
    protected final static int SYM_LEFT = 0,
            SYM_RIGHT = 1,
            SYM_OPER = 2,
            SYM_OTHER = 3;

    /*
    public Compf(){
        //
    }
    */

    private String number = "";

    private int symType(char c){
        switch(c){
            case '(':
            case '[':
            case '{':
                return SYM_LEFT;
            case ')':
            case ']':
            case '}':
                return SYM_RIGHT;
            case '+':
            case '-':
            case '*':
            case '/':
                return SYM_OPER;
            default:
                return symOther(c);
        }
    }

    private void processSymbol(char c){
        int type = symType(c);

        if(type != SYM_OTHER)
            processNumber();

        switch(type){
            case SYM_LEFT:
                push(c);
                break;
            case SYM_RIGHT:
                processSuspendedSymbols(c);
                pop();
                break;
            case SYM_OPER:
                processSuspendedSymbols(c);
                push(c);
                break;
            case SYM_OTHER:
                number += c;
                break;
        }
    }

    private void processNumber(){
        if(!number.equals("")){
            nextNumber(Integer.parseInt(number));
            number = "";
        }
    }

    private void processSuspendedSymbols(char c){
        while(precedes(peek(), c))
            nextOper(pop());
    }

    private int priority(char c){
        return c == '+' || c == '-' ? 1 : 2;
    }

    private boolean precedes(char a, char b){
        if(symType(a) == SYM_LEFT) return false;
        if(symType(b) == SYM_RIGHT) return true;

        return priority(a) >= priority(b);
    }

    protected int symOther(char c){
        if (c < 'a' || c > 'z'){
            System.out.println("Недопустимый символ: " + c);
            System.exit(0);
        }

        return SYM_OTHER;
    }

    protected void nextOper(char c){
        System.out.print("" + c + " ");
    }

    protected void nextNumber(int number){
        System.out.print("" + number + " ");
    }

    public void compile(char[] str){
        processSymbol('(');

        for(int i = 0; i < str.length; i++)
            processSymbol(str[i]);

        processSymbol(')');

        System.out.print("\n");
    }
}

