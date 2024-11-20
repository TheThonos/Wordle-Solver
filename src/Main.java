import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;

public class Main {
    private static Scanner scanner;

    enum Color {
        Gray ("\033[47m"),
        Yellow ("\033[103m"),
        Green ("\033[102m");

        public final String escape;

        Color(String escape){
            this.escape = escape;
        }
    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        getInputs();
        Words words = new Words();
    }

    private static Color getColorFromChar(String colorChar){
        return switch(colorChar) {
            case "y" -> Color.Yellow;
            case "g" -> Color.Green;
            default -> Color.Gray;
        };
    }

    private static void getInputs(){
        char[] chars = getChars();
        Color[] colors = getColors();
        for(int i = 0; i < 5; i++){
            System.out.print(colors[i].escape + " " + chars[i] + " ");
        }
        System.out.print("\033[0m\n");
    }

    private static Color[] getColors() {
        System.out.print("Colors: ");
        Color[] colors = {getColorFromChar(scanner.next()), getColorFromChar(scanner.next()), getColorFromChar(scanner.next()), getColorFromChar(scanner.next()), getColorFromChar(scanner.next())};
        return colors;
    }

    private static char[] getChars(){
        System.out.print("Guess: ");
        String str = scanner.nextLine().toUpperCase();
        return str.toCharArray();
    }

    public interface LibC extends Library {
        int SYSTEM_OUT_FD = 0;
        int ISIG = 1, ICANNON = 2, ECHO = 10, TCSAFLUSH = 2, IXON = 2000, ICRNL = 400, IEXTEN = 100000, OPOST = 1, VMIN = 6, VTIME = 5, TIOCGWINSZ = 0x5413;

        LibC INSTANCE = Native.load("c", LibC.class);

        @Structure.FieldOrder(value = {"c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc"})
        class Termios extends Structure {
            public int c_iflag;
            public int c_oflag;
            public int c_cflag;
            public int c_lflag;
            public byte[] c_cc = new byte[19];
        }

        int tcgetattr(int fd, Termios termios);

        int tcsetattr(int fd, int optional_actions, Termios termios);
    }
}