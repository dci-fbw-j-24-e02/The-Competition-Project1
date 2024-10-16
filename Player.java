public class Player {
    private String name;
    private int score;

    public Player(){
        this.name = "New player";
        this.score = 0;
    }

    public Player(String name){
        this.name = name;
        this.score = score;
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

    @Override
    public String toString() {
        return "Score " + name + ": " + score + ";";
    }
}
