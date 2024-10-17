public class Timer extends Thread {
    private int seconds;
    private boolean running;
    private boolean timeOver;
    private Player player;

    public Timer(int seconds, Player player) {
        this.seconds = seconds;
        this.player = player;
        this.running = false;
        this.timeOver = false;
    }

    public boolean isTimeOver() {
        return timeOver;
    }

    @Override
    public void run() {
        running = true;
        timeOver = false;
        try {
            for (int i = 0; i < seconds; i++) {
                if (!running) break;
                Thread.sleep(1000); // Wait for 1 second
            }
            timeOver = running;
         //   System.out.println(player.getName() + " ran out of time!");
        } catch (InterruptedException e) {
            running = false;
            System.out.println("Timer interrupted");
        }
    }

    public void stopTimer() {
        running = false; // Stop the timer

    }

    public void checkTimer() {
        running = false; // Stop the timer

    }
}
