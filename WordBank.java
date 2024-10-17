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
        try {
            loadWords();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
}
