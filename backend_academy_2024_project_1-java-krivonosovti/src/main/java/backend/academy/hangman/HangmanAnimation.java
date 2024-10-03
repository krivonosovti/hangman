package backend.academy.hangman;

import org.checkerframework.checker.optional.qual.Present;
import java.io.PrintStream;

public class HangmanAnimation {
    public static final int MAX_ATTEMPTS = 6;
    private int maxAttempts = MAX_ATTEMPTS;
    private int wrongAttempts = 0;

    private PrintStream printStream;

    public HangmanAnimation(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    // Метод для задания максимального количества попыток на основе сложности

    public void addWrongAttempt() {
        wrongAttempts++;
        visualizeHangman(maxAttempts);
    }

    public int getRemainingAttempts() {
        return maxAttempts - wrongAttempts;
    }

    public boolean isGameOver() {
        return wrongAttempts >= maxAttempts;
    }

    private void visualizeHangman(int maxAttempts) {

        String[] baseStages = new String[]{
            "   _______\n  |/       |\n  |\n  |\n  |\n  |\n  |\n _|_",               // 1. Пустая виселица
            "   _______\n  |/       |\n  |       O\n  |\n  |\n  |\n  |\n _|_",         // 2. Голова
            "   _______\n  |/       |\n  |       O\n  |       |\n  |\n  |\n  |\n _|_", // 3. Туловище
            "   _______\n  |/       |\n  |       O\n  |      /|\n  |\n  |\n  |\n _|_", // 4. Одна рука
            "   _______\n  |/       |\n  |       O\n  |      /|\\\n  |\n  |\n  |\n _|_", // 5. Две руки
            "   _______\n  |/       |\n  |       O\n  |      /|\\\n  |       |\n  |\n _|_", // 6. Туловище до ног
            "   _______\n  |/       |\n  |       O\n  |      /|\\\n  |      /|\n  |\n _|_", // 7. Одна нога
            "   _______\n  |/       |\n  |       O\n  |      /|\\\n  |      /|\\\n  |\n _|_", // 8. Две ноги
            "   _______\n  |/       |\n  |       O\n  |      /|\\\n  |      /|\\\n  |     /\n _|_", // 9. Одна ступня
            "   _______\n  |/       |\n  |       O\n  |      /|\\\n  |      /|\\\n  |     / \\\n _|_", // 10. Две ступни (виселица завершена)
        };
        // Количество этапов рисования
        int totalStages = baseStages.length;

        // Вычисляем, сколько этапов отображать в зависимости от maxAttempts
        int stagesToShow = Math.min(wrongAttempts * totalStages / maxAttempts, totalStages - 1);

        // Отрисовываем соответствующий этап виселицы
        printStream.println(baseStages[stagesToShow]);
    }

}
