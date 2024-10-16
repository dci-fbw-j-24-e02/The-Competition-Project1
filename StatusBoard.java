public class StatusBoard {
    private String currentWord;
    private String[] currentHangman1; // needs getCurrentHangman (as String[])
    private String[] currentHangman2; // needs getCurrentHangman (as String[])
    private Player player1;
    private Player player2;


    public StatusBoard(String currentWord, String[] currentHangman1, String[] currentHangman2, Player player1, Player player2){
        this.currentWord = currentWord;
        this.currentHangman1 = currentHangman1;
        this.currentHangman2 = currentHangman2;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void updateStatusBoard(String currentWord, String[] currentHangman1, String[] currentHangman2 , Player player1, Player player2){
        this.currentWord = currentWord;
        this.currentHangman1 = currentHangman1;
        this.currentHangman2 = currentHangman2;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void displayStatusBoard(){
        String format = "%-20s %s %20s %-20s %s%n";
        System.out.printf(format, player1.getName(), currentHangman1[0], "", player2.getName(), currentHangman2[0]);
        System.out.printf(format, "Score: "+ player1.getScore(), currentHangman1[1], "","Score: "+ player2.getScore(), currentHangman2[1] );
        for(int i = 2; i < 10; i++){
            System.out.printf(format, "", currentHangman1[i], "","", currentHangman2[i]);
        }
        System.out.printf(format,"",currentHangman1[10], currentWord, "", currentHangman2[10]);

    }
}
