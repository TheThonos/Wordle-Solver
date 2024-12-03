import java.net.URL;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Words {
    public ArrayList<String> words;

    public Words() {
        this.words = new ArrayList<>();
        try {
            URL url = getClass().getResource("valid-wordle-words.txt");
            assert url != null;
            File file = new File(url.getPath());
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                assert words != null;
                words.add(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void filter(LetterBox[] guess) {
        int currentWordIndex = 0;
        while (currentWordIndex < this.words.size()) {
            String[] currentWord = this.words.get(currentWordIndex).split("");
            boolean valid = true;
            for (int y = 0; y < 5; y++) {
                if (guess[y].color == Color.Green) {
                    if (!guess[y].letter.equals(currentWord[y])) {
                        valid = false;
                        break;
                    } else {
                        currentWord[y] = "-";
                    }
                }
            }

            for (int y = 0; y < 5; y++) {
                if (guess[y].color == Color.Yellow && !Arrays.asList(currentWord).contains(guess[y].letter)) {
                    valid = false;
                    break;
                }

                if (guess[y].color == Color.Yellow && Arrays.asList(currentWord).contains(guess[y].letter)) {
                    int index = String.join("", currentWord).indexOf(guess[y].letter);

                    currentWord[index] = "-";
                }

                if (guess[y].color == Color.Gray && Arrays.asList(currentWord).contains(guess[y].letter)) {
                    valid = false;
                    break;
                }
            }

            if (!valid) {
                this.words.remove(currentWordIndex);
            } else {
                currentWordIndex++;
            }
        }
    }

    public void findBestWord() {}

    public void displayAllValidWords() {
        for (String word : this.words) {
            System.out.println(word);
        }
        System.out.println(this.words.size() + " words");
    }
}
