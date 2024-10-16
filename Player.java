public class Player {
    private String name;
    private int score;
    private HangmanUI hangman;
    private int currentAttempt;

    public Player(){
        this.name = "New player";
        this.score = 0;
        this.hangman = new HangmanUI();
        this.currentAttempt = 0;
    }

    public Player(String name){
        this.name = name;
        this.score = score;
        this.hangman = new HangmanUI();
        this.currentAttempt = 0;
    }

    public String getName(){
        return name;
    }

    public void increaseScore(){
        score++;
        updateHangmanStatus();
    }

    public int getScore(){
        return score;
    }

    public void increaseAttempt(){
        currentAttempt++;
    }

    public int getCurrentAttempt() { return currentAttempt;}

    public String[] getHangmanStatus(){
         return this.hangman.getHangman();
    }

    private void updateHangmanStatus(){
        this.hangman.updateHangman(this.score);
    }

    @Override
    public String toString() {
        return "Score " + name + ": " + score + ";";
    }
}
