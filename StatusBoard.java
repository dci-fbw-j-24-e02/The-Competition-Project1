public class StatusBoard {
    private String currentWord;
    private Player player1;
    private Player player2;

    public StatusBoard(String currentWord, Player player1, Player player2){
        this.currentWord = convertWord(currentWord);
        this.player1 = player1;
        this.player2 = player2;
    }

    public void updateStatusBoard(String currentWord){
        this.currentWord = convertWord(currentWord);
    }

    public void displayStatusBoard(){
        String format = "%-20s %s %30s %-20s %s%n";
        String lettersPlayer1 = "";
        String lettersPlayer2 = "";
        for(Character ch: player1.getIncorrectGuesses()){
            lettersPlayer1 += ch + " ";
        }
        for(Character ch: player2.getIncorrectGuesses()){
            lettersPlayer2 += ch + " ";
        }

        System.out.printf(format, player1.getName(), player1.getHangmanStatus()[0], "", player2.getName(), player2.getHangmanStatus()[0]);
        System.out.printf(format, "Score: "+ player1.getScore(), player1.getHangmanStatus()[1], "","Score: "+ player2.getScore(), player2.getHangmanStatus()[1]);
        System.out.printf(format, "Incorrect letters: ", player1.getHangmanStatus()[2], "","Incorrect letters: ", player2.getHangmanStatus()[2]);
        System.out.printf(format, lettersPlayer1, player1.getHangmanStatus()[3], "",lettersPlayer2, player2.getHangmanStatus()[3]);
        for(int i = 4; i < 10; i++){
            System.out.printf(format, "", player1.getHangmanStatus()[i], "","", player2.getHangmanStatus()[i]);
        }
        System.out.printf(format,"", player1.getHangmanStatus()[10], currentWord, "", player2.getHangmanStatus()[10]);

    }
    private String convertWord(String word){
        char[] letters = word.trim().toUpperCase().toCharArray();
        String newWord = "";
        for(char ch: letters){
         newWord += ch + " ";
        }
        return newWord.trim();
    }
}