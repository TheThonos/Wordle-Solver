public enum Color {
    Gray ("\033[47m"),
    Yellow ("\033[103m"),
    Green ("\033[102m");

    public final String escape;

    Color(String escape){
        this.escape = escape;
    }
}