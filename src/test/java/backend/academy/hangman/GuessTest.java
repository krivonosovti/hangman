package backend.academy.hangman;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class GuessTest {
    PrintStream printStream = System.out;
    @Test
    public void testValidSingleLetterInput() {
        // Arrange (Given)
        String input = "а\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Guess player = new Guess(printStream);

        // Act (When)
        char guess = player.makeGuess();

        // Assert (Then)
        assertEquals('а', guess, "Введенная буква должна быть 'а'.");
    }

    @Test
    public void testInvalidInputAndRetry() {
        // Arrange (Given)
        String input = "1\nб\n"; // Сначала введем некорректное значение "1", потом корректное "b"
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Guess player = new Guess(printStream);

        // Act (When)
        char guess = player.makeGuess();

        // Assert (Then)
        assertEquals('б', guess, "После некорректного ввода игрок должен ввести корректную букву.");
    }

    @Test
    public void testSpecialCharacterInput() {
        // Arrange (Given)
        String input = "!\n"; // Вводим специальный символ '!'
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Guess player = new Guess(printStream);

        // Act (When)
        char guess = player.makeGuess();

        // Assert (Then)
        assertEquals('!', guess, "Символ '!' должен приниматься как специальный символ для подсказки.");
    }

    @Test
    public void testMultiCharacterInput() {
        // Arrange (Given)
        String input = "абс\nс\n"; // Вводим сначала несколько символов, затем корректную букву "c"
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Guess player = new Guess(printStream);

        // Act (When)
        char guess = player.makeGuess();

        // Assert (Then)
        assertEquals('с', guess, "Игрок должен ввести одну букву после некорректного ввода нескольких символов.");
    }
}
