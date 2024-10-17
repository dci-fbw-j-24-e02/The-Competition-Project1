import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private HangmanUI hangman;
    private int currentAttempt;
    private List<Character> incorrectGuesses;
    private List<String> incorrectWords;

    public Player(){
        this.name = "New player";
        this.score = 0;
        this.hangman = new HangmanUI();
        this.currentAttempt = 0;
        this.incorrectGuesses = new ArrayList<>();
        this.incorrectWords = new ArrayList<>();
    }

    public Player(String name){
        this.name = name;
        this.score = score;
        this.hangman = new HangmanUI();
        this.currentAttempt = 0;
        this.incorrectGuesses = new ArrayList<>();
        this.incorrectWords = new ArrayList<>();
        incorrectWords.add("");
    }

    public String getName(){
        return name;
    }

    public void increaseScore(){
        score++;
    }

    public int getScore(){
        return score;
    }

    public void increaseAttempt(int maxAttempts){
        currentAttempt++;
        updateHangmanStatus(maxAttempts);
    }

    public int getCurrentAttempt() { return currentAttempt;}

    public List<Character> getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public List<String> getIncorrectWords() {
        return incorrectWords;
    }

    public void addIncorrectGuess(String guess){
        if(guess.length() > 1){
            if(incorrectWords.get(0).equals("")){
                incorrectWords.remove(0);
            }
            incorrectWords.add(guess.toUpperCase());
        }else{
            incorrectGuesses.add(guess.toUpperCase().charAt(0));
        }
    }

    public String[] getHangmanStatus(){
        return this.hangman.getHangman();
    }


    private void updateHangmanStatus(int maxAttempts){
        int currentStage = this.currentAttempt;
        switch(maxAttempts) {
            case 6 -> {
                switch (this.currentAttempt) {
                    case 1 -> currentStage = 1;
                    case 2 -> currentStage = 3;
                    case 3 -> currentStage = 5;
                    case 4 -> currentStage = 6;
                    case 5 -> currentStage = 7;
                    case 6 -> currentStage = 8;
                    default -> currentStage = this.currentAttempt;
                }
            }
            case 4 -> {
                switch (this.currentAttempt) {
                    case 1 -> currentStage = 3;
                    case 2 -> currentStage = 5;
                    case 3 -> currentStage = 7;
                    case 4 -> currentStage = 8;
                    default -> currentStage = currentAttempt;
                }
            }
        }
        for(int i = hangman.getStage(); i <= currentStage; i++) {
            this.hangman.updateHangman(i);
        }
    }

    @Override
    public String toString() {
        return "Score " + name + ": " + score + ";";
    }
}
