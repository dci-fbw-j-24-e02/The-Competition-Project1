import java.io.IOException;
import java.util.Scanner;

public class MultiplayerGame {
    private WordBank wordBank;
    private Player player1;
    private Player player2;
    private Word word;
    private StatusBoard statusBoard;
    private int maxAttempts;
    private  Timer timer;

    public MultiplayerGame(String player1Name, String player2Name){
        this.wordBank = new WordBank("words.txt");
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.timer = new Timer(30);
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
            System.out.println("Enter Player1 name:");
            String player1Name = sc.nextLine();
            System.out.println("Enter Player2 name:");
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


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void playGame(){
        boolean isPlayer1Turn = true;
        Scanner sc = new Scanner(System.in);
        Player currentPlayer;
        String guess;

        while(!word.isGuessed() || player1.getCurrentAttempt() < maxAttempts || player2.getCurrentAttempt() < maxAttempts){
            statusBoard.displayStatusBoard();
            currentPlayer = isPlayer1Turn ? player1 : player2;
            System.out.println(currentPlayer.getName() + "'s turn. Enter your guess: ");

             guess = sc.nextLine();

             if(!word.revealLetter(guess)){
                 currentPlayer.increaseAttempt();
                 System.out.println("Incorrect guess!");
             }

            isPlayer1Turn = !isPlayer1Turn;
        }

    }

}
