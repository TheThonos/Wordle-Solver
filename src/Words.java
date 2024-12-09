import java.net.URL;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
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

    public ArrayList<String> filter(LetterBox[] guess) {
        int currentWordIndex = 0;
        ArrayList<String> removedWords = new ArrayList<>();
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
                if (guess[y].color == Color.Yellow && guess[y].letter.equals(currentWord[y])) {
                    valid = false;
                    break;
                }

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
                removedWords.add(words.get(currentWordIndex));
                this.words.remove(currentWordIndex);
            } else {
                currentWordIndex++;
            }
        }

        return removedWords;
    }

    public String findBestWordMaksim() {
        String[] uncommonLetters = {"z", "w", "x", "q", "v"};
        String[] commonLetter = {"a", "e", "s", "o", "r", "i", "l", "t", "n", "u"};
        ArrayList<Word> scoredWords = new ArrayList<>();

        for (String word : this.words) {
            Word currentWord = new Word(word);

            int uncommonLettersContained = 0;

            for (String letter : uncommonLetters) {
                if (currentWord.word.contains(letter)) {
                    uncommonLettersContained++;
                }
            }

            currentWord.decreaseScore(5 * uncommonLettersContained);

            if (currentWord.word.contains("y")) {
                currentWord.decreaseScore(2);
            }

            if (!currentWord.word.contains("y") && uncommonLettersContained == 0) {
                currentWord.increaseScore(5);
            }

            int commonLettersContained = 0;

            for (String letter : commonLetter) {
                if (currentWord.word.contains(letter)) {
                    commonLettersContained++;
                }
            }

            currentWord.increaseScore(2 * commonLettersContained);

            // Penalty for double letters
            int amountOfDoubles = 0;

            ArrayList<String> letters = new ArrayList<>();
            for (String letter : word.split("")) {
                if (letters.contains(letter)) {
                    amountOfDoubles++;
                } else {
                    letters.add(letter);
                }
            }

            currentWord.decreaseScore(2 * amountOfDoubles);

            if (amountOfDoubles == 0) {
                currentWord.increaseScore(5);
            }

            scoredWords.add(currentWord);
        };

        for (int i = 0; i < scoredWords.size() - 1; i++) {
            for (int j = 0; j < scoredWords.size() - i - 1; j++) {
                if (scoredWords.get(j).score > scoredWords.get(j + 1).score) {
                    Word temp = scoredWords.get(j);
                    scoredWords.set(j, scoredWords.get(j + 1));
                    scoredWords.set(j + 1, temp);
                }
            }
        }

        return scoredWords.getLast().word;
    }

    public String findBestWordThomas() {
        int wordListLength = this.words.size();
        ArrayList<Double> averages = new ArrayList<>();
        ArrayList<String> wordss = (ArrayList<String>) this.words.clone();
        int best = 0;
        double bestAvg = Double.MAX_VALUE;
        for(int a = 0; a < wordListLength; a++){
            System.out.println(a + "/" + wordListLength);
            String guessWord = this.words.get(a);
            int totalResultNum = 0;

            for (String testAnswer : wordss) {
                char[] guessChars = guessWord.toCharArray();
                char[] answerChars = testAnswer.toCharArray();
                LetterBox[] guess = new LetterBox[5];
                for (int i = 0; i < 5; i++) {
                    if (guessChars[i] == answerChars[i]) {
                        guess[i] = new LetterBox("" + guessChars[i], Color.Green);
                        guessChars[i] = '-';
                        answerChars[i] = '-';
                    } else {
                        boolean yellow = false;
                        int yellowIndex = 0;
                        for (int index = 0; index < 5; index++) {
                            if (answerChars[index] == guessChars[i]) {
                                yellow = true;
                                yellowIndex = index;
                                break;
                            }
                        }

                        if (yellow) {
                            guess[i] = new LetterBox("" + guessChars[i], Color.Yellow);
                            answerChars[yellowIndex] = '-';
                        } else {
                            guess[i] = new LetterBox("" + guessChars[i], Color.Gray);
                        }
                        guessChars[i] = '-';
                    }
                }
                ArrayList<String> removedWords = this.filter(guess);
                totalResultNum += this.words.size();
                this.words.addAll(removedWords);
                if((double) (totalResultNum + (wordListLength - a)) / wordListLength > bestAvg){
                    break;
                }
            }
            double average = (double) totalResultNum / wordListLength;
            averages.add(average);
            if (average < bestAvg) {
                bestAvg = average;
                best = a;
            }
        }
        return this.words.get(best);
    }

    public void displayAllValidWords() {
        for (String word : this.words) {
            System.out.println(word);
        }
        System.out.println(this.words.size() + " words");
    }
}
