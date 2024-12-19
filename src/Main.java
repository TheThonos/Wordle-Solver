import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("Welcome to Wordle Word Finder");
        System.out.println("To get started enter a 5 letter word when prompted for a guess");
        System.out.println("Then enter the colors of each letter as shown by wordle");
        System.out.println("\tg: green, y: yellow, any other letter for gray\n");
        scanner = new Scanner(System.in);
        Words words = new Words();
        String bestMaksimWord = words.findBestWordMaksim();
        System.out.println("Best word according to Maksim: " + bestMaksimWord);
        System.out.println("Best word: roate");
        final int guesses = 6;
        for (int i = guesses; i > 0; i--) {
            LetterBox[] wordFilter = getInputs();

            int count = 0;
            for (LetterBox item : wordFilter) {
                if (item.color == Color.Green) {
                    count ++;
                }
            }

            if (count == 5) {
                break;
            }

            words.filter(wordFilter);
            if (words.words.isEmpty()) {
                break;
            }
            words.displayAllValidWords();
            int predictedTime = words.words.size() / 4;
            if(predictedTime > 30){
                System.out.println("Finding optimal word may take around " + predictedTime + " seconds.");
                System.out.print("Would you like to calculate it anyway? (Y/n) ");
                String choice = scanner.nextLine().toLowerCase();
                if (!choice.equals("n") && !choice.equals("no")) {
                    String bestThomasWord = words.findBestWordThomas();
                    System.out.println("Best word according to Thomas: " + bestThomasWord);
                }
            } else {
                String bestThomasWord = words.findBestWordThomas();
                System.out.println("Best word according to Thomas: " + bestThomasWord);
            }
            bestMaksimWord = words.findBestWordMaksim();
            System.out.println("Best word according to Maksim: " + bestMaksimWord);
        }
    }

    private static Color getColorFromChar(String colorChar){
        return switch(colorChar) {
            case "y" -> Color.Yellow;
            case "g" -> Color.Green;
            default -> Color.Gray;
        };
    }

    private static LetterBox[] getInputs(){
        String[] chars = getChars();
        Color[] colors = getColors();
        LetterBox[] wordFilter = new LetterBox[5];
        for (int i = 0; i < 5; i++) {
            System.out.print(colors[i].escape + " " + chars[i] + " ");
            wordFilter[i] = new LetterBox(chars[i], colors[i]);
        }
        System.out.print("\033[0m\n");
        return wordFilter;
    }

    private static Color[] getColors() {
        System.out.print("Colors: ");
        String[] colorStrings = scanner.nextLine().split("");
        Color[] colors = new Color[5];
        if (colorStrings.length != 5){
            System.out.println("That's not 5 colors!");
            return getColors();
        }
        for(int i = 0; i < 5; i++) {
            colors[i] = getColorFromChar(colorStrings[i]);
        }
        return colors;
    }

    private static String[] getChars() {
        System.out.print("Guess: ");
        String[] str = scanner.nextLine().toLowerCase().split("");;

        if(str.length != 5) {
            System.out.println("That's not 5 letters!");
            return getChars();
        }
        return str;
    }
}