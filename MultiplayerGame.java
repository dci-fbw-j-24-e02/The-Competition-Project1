import java.io.IOException;
import java.util.InputMismatchException;
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
            String player1Name = nameValidator(sc.nextLine());
            System.out.println("Enter Player2 name: ");
            String player2Name = nameValidator(sc.nextLine());

            int diffLevel;
            while(true) {
                System.out.println("Choose difficulty level:");
                System.out.println("1. Easy (3-5 letters, max attempts: 8)");
                System.out.println("2. Medium (6-8 letters, max attempts: 6)");
                System.out.println("3. Hard (9+ letters, max attempts: 4)");

                try {
                    diffLevel = sc.nextInt();
                    break;
                }catch(InputMismatchException e){
                    System.out.println("Input should be a number");
                    sc.nextLine();
                }
            }
            MultiplayerGame game = new MultiplayerGame(player1Name,player2Name);

            int finalDiffLevel = diffLevel;
            game.word = new Word(game.wordBank.getRandomWord((w) -> {
                switch (finalDiffLevel) {
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


        while(!word.isGuessed() && (player1.getCurrentAttempt() < maxAttempts || player2.getCurrentAttempt() < maxAttempts)){
            statusBoard.displayStatusBoard();
            currentPlayer = isPlayer1Turn ? player1 : player2;
            System.out.println("\n" + currentPlayer.getName() + "'s turn. You have 30 seconds. Enter your guess: ");
            Timer timer = startTimer(currentPlayer);

            guess = sc.nextLine().toLowerCase();
            timer.stopTimer();

            guessMaidInTime = !timer.isTimeOver();

            if (guessMaidInTime){
                if(!word.revealLetter(guess)){
                    currentPlayer.increaseAttempt(maxAttempts);
                    System.out.println("Incorrect guess!\n");
                    currentPlayer.addIncorrectGuess(guess);
                    if(currentPlayer.getCurrentAttempt() >= maxAttempts){
                        System.out.println("Sorry! " + currentPlayer.getName() + ", you lose :(\n");
                    }
                }
                else{
                    if (word.isGuessed()) {
                        System.out.println(currentPlayer.getCurrentAttempt() < maxAttempts ? "Congratulations, " + currentPlayer.getName() + ", you won! The word was: " + word.getWord() : "Game Over! The word was: " + word.getWord().toUpperCase());
                        for(int i = 0; i < 5; i++) {
                            currentPlayer.increaseScore();
                        }
                        statusBoard.updateStatusBoard(word.getMaskedWord());

                    } else {
                        System.out.println("Congratulations! You guessed the letter and have one more attempt!\n");
                        currentPlayer.increaseScore();
                        statusBoard.updateStatusBoard(word.getMaskedWord());
                        isPlayer1Turn = !isPlayer1Turn;
                    }
                }
            }
            else{
                System.out.println(currentPlayer.getName() + " ran out of time!");
                currentPlayer.increaseAttempt(maxAttempts);
                if(currentPlayer.getCurrentAttempt() >= maxAttempts){
                    System.out.println("Sorry! " + currentPlayer.getName() + ", you lose :(\n");
                    //only next player should be able to guess
                }
            }

            isPlayer1Turn = !isPlayer1Turn;
        }
        System.out.println("Game Over! The word was: " + word.getWord().toUpperCase() + "\n");
        statusBoard.displayStatusBoard();
        executorService.shutdown();
    }

    private Timer startTimer(Player player) {
        Timer timerTask = new Timer(30, player);

        executorService.submit(timerTask);
        return timerTask;
    }

    static private String nameValidator(String name){
        if(name.length() < 20){
            return name;
        }else{
            return name.substring(0, 19);
        }
    }
}
