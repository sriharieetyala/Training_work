//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
       Worker w1=new Worker();
       Thread t1=new Thread(w1);
       t1.start();
    }

}