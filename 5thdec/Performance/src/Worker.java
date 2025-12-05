public class Worker implements Runnable {
    @Override
    public void run() {
        add(1,2);
    }
    public static int add(int a,int b){
        add(a,b);
        return a+b;
    }
}

