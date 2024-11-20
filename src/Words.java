import java.net.URL;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
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
}
