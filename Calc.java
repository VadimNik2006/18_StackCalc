//Калькулятор арифметических формул.
public class Calc extends Compf{
    private StackInt s;

    private static int char2int(char c){
        return(int)c - (int)'0';
    }

    @Override
    protected int symOther(char c){
        if(c < '0' || c > '9'){
            System.out.println("Недопустимый символ: " + c);

            System.exit(0);
        }

        return SYM_OTHER;
    }

    @Override
    protected void nextOper(char c){
        int second = s.pop();
        int first = s.pop();

        switch(c){
            case '+':
                s.push(first + second);
                break;
            case '-':
                s.push(first - second);
                break;
            case '*':
                s.push(first*second);
                break;
            case'/':
                s.push(first/second);
                break;
        }
    }

    @Override
    protected void nextOther(char c){
        s.push(char2int(c));
        //s.push(Character.getNumericValue(c));
    }

    public Calc(){
        s = new StackInt();
    }

    public final void compile(char[] str){
        super.compile(str);

        System.out.println("" + s.top());
    }
}