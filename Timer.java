public class Timer extends Thread {
    private int timeLeft;
    private boolean running;

    public Timer(int timeLeft) {
        this.timeLeft = timeLeft;
        this.running = false;
    }

    @Override
    public void run() {
        running = true;
        while (timeLeft > 0 && running) {
            try {
                Thread.sleep(1000);
                timeLeft--;
                System.out.println("Time left: " + timeLeft + " seconds");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        if (timeLeft == 0) {
            System.out.println("Time's up!");
        }
    }

    public void stopTimer() {
        running = false;
    }
}
