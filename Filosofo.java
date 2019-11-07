
class Philosopher implements Runnable {

    private Object leftFork;
    private Object rightFork;

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void doAction(String action) throws InterruptedException {
        System.out.println(
                Thread.currentThread().getName() + " " + action);
        Thread.sleep((int) (Math.random() * 100));

    }

    @Override
    public void run() {
        try {
            while (true) {
                doAction("Pensando");
                synchronized (leftFork) {
                    doAction("Pega Garfo esquerdo");
                    synchronized (rightFork) {
                        doAction("Pega garfo direito ");
                        doAction("Come");
                        doAction("Abaixa o garfo direito");
                    }
                    doAction("Abaixa o garfo esquerdo ");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
