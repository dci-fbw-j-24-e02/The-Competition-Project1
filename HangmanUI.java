public class HangmanUI {

    private String[] hangman;
    private int stage;

    public HangmanUI(){
        this.hangman = createHangman();
        stage = 0;
    }

    private String[] createHangman(){
        String[] man = new String[11];
        man[0] = "*****************";
        man[1] = "*  __________   *";
        man[2] = "*  |/           *";
        man[3] = "*  |            *";
        man[4] = "*  |            *";
        man[5] = "*  |            *";
        man[6] = "*  |            *";
        man[7] = "*  |            *";
        man[8] = "*  |            *";
        man[9] = "* ===           *";
        man[10] = "*****************";
        return man;
    }

    private void changeHangman(int stage){
        switch(stage){
            case 1:
                hangman[3] = "*  |        O   *";
                break;
            case 2:
                hangman[4] = "*  |        |   *";
                break;
            case 3:
                hangman[5] = "*  |        |   *";
                break;
            case 4:
                hangman[6] = "*  |       /    *";
                break;
            case 5:
                hangman[6] = "*  |       / \\  *";
                break;
            case 6:
                hangman[4] = "*  |       /|   *";
                break;
            case 7:
                hangman[4] = "*  |       /|\\  *";
                break;
            case 8:
                hangman[2] = "*  |        |   *";
                break;
        }
    }

    public void displayHangman(int incorrectGuesses){
         if(incorrectGuesses < stage){
             incorrectGuesses = stage;
         }
            while(stage != incorrectGuesses){
                stage++;
                changeHangman(stage);
            }

        for(String line: hangman){
            System.out.printf("%15s%s%n", "", line);
        }
    }


}
