import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Words words = new Words();
        final int guesses = 6;
        for (int i = guesses; i > 0; i--) {
            LetterBox[] wordFilter = getInputs();
            words.filter(wordFilter);
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
        for(int i = 0; i < 5; i++){
            System.out.print(colors[i].escape + " " + chars[i] + " ");
            wordFilter[i] = new LetterBox(chars[i], colors[i]);
        }
        System.out.print("\033[0m\n");
        return wordFilter;
    }

    private static Color[] getColors() {
        System.out.print("Colors: ");
        Color[] colors = {getColorFromChar(scanner.next()), getColorFromChar(scanner.next()), getColorFromChar(scanner.next()), getColorFromChar(scanner.next()), getColorFromChar(scanner.next())};
        return colors;
    }

    private static String[] getChars(){
        System.out.print("Guess: ");
        String str = scanner.nextLine().toUpperCase();
        return str.split("");
    }
}