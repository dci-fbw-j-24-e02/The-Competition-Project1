import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordBank {
    private List<String> wordList = new ArrayList<>();
    private final String filePath;

    public WordBank(String filePath) {
        this.filePath = filePath;
    }

    public void loadWords() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            wordList.add(line.trim());
        }
        reader.close();
    }

    public String getRandomWord(WordFilter filter) {
        List<String> filteredWords = new ArrayList<>();

        for (String word : wordList) {
            if (filter.filter(word)) {
                filteredWords.add(word);
            }
        }

        if (filteredWords.isEmpty()) {
            throw new IllegalArgumentException("No words available for the selected difficulty level.");
        }

        filteredWords.forEach(System.out::println);

          Random random = new Random();

          return filteredWords.get(random.nextInt(filteredWords.size()));
    }

    public void addWord(String word) throws IOException {
        if (wordList.contains(word.toLowerCase())) {
            System.out.println("Word already exists in the word bank!");
            return;
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(word.toLowerCase());
        writer.newLine();
        writer.close();

        wordList.add(word.toLowerCase());
        System.out.println("Word added successfully!");
    }

    public static void main(String[] args) {
        WordBank wordBank = new WordBank("words.txt");
        try {
            wordBank.loadWords();
            wordBank.wordList.forEach(System.out::println);

            System.out.println("Choose difficulty level:");
            System.out.println("1. Easy (3-5 letters, max attempts: 8)");
            System.out.println("2. Medium (6-8 letters, max attempts: 6)");
            System.out.println("3. Hard (9+ letters, max attempts: 4)");
            Scanner sc = new Scanner(System.in);
            int diffLevel = sc.nextInt();

            Word word = new Word(wordBank.getRandomWord((w) -> {
                switch (diffLevel) {
                    case 1: return w.length() >= 3 && w.length() <= 5; // Easy
                    case 2: return w.length() >= 6 && w.length() <= 8; // Medium
                    case 3: return w.length() >= 9;                    // Hard
                    default: return w.length() >= 3 && w.length() <= 5; // Default to Easy
                }
            }));


            System.out.println("Chosen word: "+ word.getWord());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
