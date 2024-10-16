public class StatusBoard {
    private String currentWord;
    private Player player1;
    private Player player2;
    
    public StatusBoard(String currentWord, Player player1, Player player2){
        this.currentWord = currentWord;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void updateStatusBoard(String currentWord){
        this.currentWord = currentWord;
    }

    public void displayStatusBoard(){
        String format = "%-20s %s %20s %-20s %s%n";
        System.out.printf(format, player1.getName(), player1.getHangmanStatus()[0], "", player2.getName(), player2.getHangmanStatus()[0]);
        System.out.printf(format, "Score: "+ player1.getScore(), player1.getHangmanStatus()[1], "","Score: "+ player2.getScore(), player2.getHangmanStatus()[1]);
        for(int i = 2; i < 10; i++){
            System.out.printf(format, "", player1.getHangmanStatus()[i], "","", player2.getHangmanStatus()[i]);
        }
        System.out.printf(format,"", player1.getHangmanStatus()[10], currentWord, "", player2.getHangmanStatus()[10]);

    }
}
