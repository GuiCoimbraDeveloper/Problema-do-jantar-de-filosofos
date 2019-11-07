
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class problemafilosofo {

    static class Fork {

        public Semaphore fork = new Semaphore(1);
        public int id;

        Fork(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public boolean take() {
            return fork.tryAcquire();
        }

        public void putDown() {
            fork.release();
        }
    }

    static class Philosopher extends Thread {

        private Fork fork_low;
        private Fork fork_high;
        private int id;

        Philosopher(Fork fork_low, Fork fork_high, int id) {
            this.fork_low = fork_low;
            this.fork_high = fork_high;
            this.id = id;
        }

        public void run() {
            System.out.println("Oi! eu sou " + id);
            while (true) {
                try {
                    System.out.println("Filosof#" + id + " Estou pensando");
                    Thread.sleep(ThreadLocalRandom.current().nextInt(0, 5000));
                    eat();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void eat() {
            if (fork_low.take()) {
                if (fork_high.take()) {
                    try {
                        System.out.println("Filosof#" + id + " pegou os garfos");
                        int sleepTime = ThreadLocalRandom.current().nextInt(0, 2000);
                        System.out.println("Filosof#" + id + " comeu por " + sleepTime);
                        Thread.sleep(sleepTime);
                        fork_low.putDown();
                        fork_high.putDown();
                        System.out.println("Filosof#" + id + " soltou os garfos");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    fork_low.putDown();
                }
            }
        }
    }

    static Philosopher[] philosopher = new Philosopher[5];

    public static void main(String[] args) {
        Fork[] fork = new Fork[5];
        for (int i = 0; i < fork.length; i++) {
            fork[i] = new Fork(i);
        }

        for (int i = 0; i < philosopher.length; i++) {
            philosopher[i] = new Philosopher(fork[i], fork[(i + 1) % 5], i);
            philosopher[i].start();
        }
    }

}
