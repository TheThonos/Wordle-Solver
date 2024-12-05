public class Word {
    public String word;
    public int score;

    public Word(String word) {
        this.word = word;
        this.score = 0;
    }

    public void decreaseScore(int amount) {
        this.score -= amount;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }
}
