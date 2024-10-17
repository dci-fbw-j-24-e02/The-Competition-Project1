import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiplayerGame {
    private WordBank wordBank;
    private Player player1;
    private Player player2;
    private Word word;
    private StatusBoard statusBoard;
    private int maxAttempts;
    private ExecutorService executorService;

    public MultiplayerGame(String player1Name, String player2Name){
        this.wordBank = new WordBank("words.txt");
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.executorService = Executors.newFixedThreadPool(2);
    }

    void setMaxAttempts(int diffLevel){
        switch (diffLevel){
            case 2: maxAttempts =  6; break;
            case 3: maxAttempts =  4; break;
            default:  maxAttempts =  8;
        }
    }

    public static void main(String[] args) {
        start();
    }

    static void start(){
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Welcome to the Hangman Game!");
            System.out.println("Enter Player1 name: ");
            String player1Name = sc.nextLine();
            System.out.println("Enter Player2 name: ");
            String player2Name = sc.nextLine();

            System.out.println("Choose difficulty level:");
            System.out.println("1. Easy (3-5 letters, max attempts: 8)");
            System.out.println("2. Medium (6-8 letters, max attempts: 6)");
            System.out.println("3. Hard (9+ letters, max attempts: 4)");

            int diffLevel = sc.nextInt();

            MultiplayerGame game = new MultiplayerGame(player1Name,player2Name);

            game.word = new Word(game.wordBank.getRandomWord((w) -> {
                switch (diffLevel) {
                    case 1: return w.length() >= 3 && w.length() <= 5; // Easy
                    case 2: return w.length() >= 6 && w.length() <= 8; // Medium
                    case 3: return w.length() >= 9;                    // Hard
                    default: return w.length() >= 3 && w.length() <= 5; // Default to Easy
                }
            }));

            game.statusBoard = new StatusBoard(game.word.getMaskedWord(),game.player1,game.player2);

            game.setMaxAttempts(diffLevel);
            game.playGame();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void playGame(){
        boolean isPlayer1Turn = true;
        Scanner sc = new Scanner(System.in);
        Player currentPlayer;
        String guess;
        boolean guessMaidInTime;


        while(!word.isGuessed() || player1.getCurrentAttempt() < maxAttempts || player2.getCurrentAttempt() < maxAttempts){
            statusBoard.displayStatusBoard();
            currentPlayer = isPlayer1Turn ? player1 : player2;
            System.out.println(currentPlayer.getName() + "'s turn. You have 10 seconds. Enter your guess: ");
            Timer timer = startTimer(currentPlayer);

            guess = sc.nextLine();
            timer.stopTimer();

            guessMaidInTime = !timer.isTimeOver();

            if (guessMaidInTime){
                if(!word.revealLetter(guess)){
                    currentPlayer.increaseAttempt();
                    System.out.println("Incorrect guess!");}
                else{
                    if (word.isGuessed()) {
                        System.out.println(currentPlayer.getCurrentAttempt() < maxAttempts ? "Congratulations! The word was: " + word.getWord() : "Game Over! The word was: " + word.getWord());
                        if (isPlayer1Turn) {
                            player1.increaseScore();
                        } else {
                            player2.increaseScore();
                        }
                    } else {
                        System.out.println("Congratulations! You guessed the letter and have one more attempt!");
                        isPlayer1Turn = !isPlayer1Turn;
                    }
                }
            }
            else{
                System.out.println(currentPlayer.getName() + " ran out of time!");
                currentPlayer.increaseAttempt();
            }

            isPlayer1Turn = !isPlayer1Turn;
        }
        System.out.println("Game Over! The word was: " + word.getWord());
        executorService.shutdown();
    }

    private Timer startTimer(Player player) {
        Timer timerTask = new Timer(10, player);

        executorService.submit(timerTask);
        return timerTask;
    }
}
