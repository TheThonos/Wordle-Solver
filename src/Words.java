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
        for (int i = 0; i < this.words.size(); i++) {
            String[] currentWord = this.words.get(i).split("");
            for (int y = 0; y < 5; y++) {
                boolean exitLoop = false;
                switch (guess[y].color) {
                    case Green:
                        if (!currentWord[y].equals(guess[y].letter)) {
                            this.words.remove(y);
                            exitLoop = true;
                            break;
                        }

                    case Yellow:
                        if (currentWord[y].equals(guess[y].letter)) {
                            this.words.remove(y);
                            exitLoop = true;
                            break;
                        }

                        if (!Arrays.stream(currentWord).toList().contains(guess[y].letter)) {
                            this.words.remove(y);
                            exitLoop = true;
                            break;
                        }

                        break;
                    case Gray:

                        break;

                }
                if(exitLoop){
                    break;
                }
            }
        }
    }

    public void findBestWord() {}

    public void getAllValidWords() {}
}
