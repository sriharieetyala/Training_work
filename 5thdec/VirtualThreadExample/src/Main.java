//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("Virtual thread running: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Simulating work
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        });

        virtualThread.join(); // wait for completion
        System.out.println("Virtual thread finished.");
    }
}
