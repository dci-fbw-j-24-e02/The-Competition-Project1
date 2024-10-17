import java.util.HashSet;
import java.util.Set;

public class Word {
    private String word;
    private Set<Character> guessedLetters;
    private String maskedWord;
    private String guess;

    public Word(String word) {
        this.word = word;
        this.guessedLetters = new HashSet<>();
        this.maskedWord = "_".repeat(word.length());
    }

    public boolean revealLetter(String guess) {
        if (guess.length() != 1) {
            if(guess.equals(word)){
                maskedWord = word;
                return true;
            }
            return false; // Only single letter guesses are allowed
        }

        char letter = guess.charAt(0);
        guessedLetters.add(letter);

        boolean found = false;
        StringBuilder newMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                newMaskedWord.setCharAt(i, letter);
                found = true;
            }
        }

        maskedWord = newMaskedWord.toString();
        return found;
    }

    public boolean isGuessed() {
        return maskedWord.equals(word);
    }

    public String getMaskedWord() {
        return maskedWord;
    }

    public String getWord() {
        return word;
    }

    public int getLength() {
        return word.length();
    }
}
