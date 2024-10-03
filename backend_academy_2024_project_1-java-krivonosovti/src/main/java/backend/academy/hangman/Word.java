package backend.academy.hangman;


import java.io.PrintStream;
import java.util.List;

public class Word {
    public static final int THREE = 3;
    public static final int LOW_LAVEL = 6;
    public static final int MIDLE_LAVEL = 8;
    public static final int HIGH_LAVEL = 10;
    private String word;
    private String category;
    private int difficulty;
    private int maxMistakes;
    private List<String> hints;  // Список подсказок
    private PrintStream printStream;

    public Word(String word, String category, int difficulty, List<String> hints, PrintStream printStream) {
        this.word = word;
        this.category = category;
        this.difficulty = difficulty;
        this.maxMistakes = calculateMaxMistakes(difficulty);
        this.hints = hints;
    }

    private int calculateMaxMistakes(int difficulty) {
        switch (difficulty) {
            case 1: return LOW_LAVEL;  // Простое
            case 2: return MIDLE_LAVEL;  // Среднее
            case THREE: return HIGH_LAVEL; // Сложное
            default: return LOW_LAVEL; // По умолчанию
        }
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMaxMistakes() {
        return maxMistakes;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }
}
