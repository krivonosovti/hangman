package backend.academy.hangman;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class WordTest {
    PrintStream printStream = System.out;

    @Test
    public void testWordInitialization() {
        // Arrange (Given)
        String expectedWord = "test";
        String expectedCategory = "category1";
        int expectedDifficulty = 2;
        List<String> expectedHints = Arrays.asList("Hint1", "Hint2", "Hint3", "Hint4");

        // Act (When)
        Word word = new Word(expectedWord, expectedCategory, expectedDifficulty, expectedHints,printStream);

        // Assert (Then)
        assertEquals(expectedWord, word.getWord(), "Слово должно быть 'test'.");
        assertEquals(expectedCategory, word.getCategory(), "Категория должна быть 'category1'.");
        assertEquals(expectedDifficulty, word.getDifficulty(), "Сложность должна быть 2.");
        assertEquals(expectedHints, word.getHints(), "Подсказки должны совпадать.");
    }

    @Test
    public void testCalculateMaxMistakesForEasyDifficulty() {
        // Arrange (Given)
        String wordStr = "test";
        String category = "category1";
        int difficulty = 1;  // Легкий уровень сложности
        List<String> hints = Arrays.asList("Hint1", "Hint2", "Hint3");

        // Act (When)
        Word word = new Word(wordStr, category, difficulty, hints, printStream);

        // Assert (Then)
        assertEquals(6, word.getMaxMistakes(), "Для легкого уровня сложности должно быть 6 допустимых ошибок.");
    }

    @Test
    public void testCalculateMaxMistakesForMediumDifficulty() {
        // Arrange (Given)
        String wordStr = "test";
        String category = "category1";
        int difficulty = 2;  // Средний уровень сложности
        List<String> hints = Arrays.asList("Hint1", "Hint2", "Hint3", "Hint4");

        // Act (When)
        Word word = new Word(wordStr, category, difficulty, hints, printStream);

        // Assert (Then)
        assertEquals(8, word.getMaxMistakes(), "Для среднего уровня сложности должно быть 8 допустимых ошибок.");
    }

    @Test
    public void testCalculateMaxMistakesForHardDifficulty() {
        // Arrange (Given)
        String wordStr = "test";
        String category = "category1";
        int difficulty = 3;  // Сложный уровень сложности
        List<String> hints = Arrays.asList("Hint1", "Hint2", "Hint3", "Hint4", "Hint5");

        // Act (When)
        Word word = new Word(wordStr, category, difficulty, hints, printStream);

        // Assert (Then)
        assertEquals(10, word.getMaxMistakes(), "Для сложного уровня сложности должно быть 10 допустимых ошибок.");
    }

    @Test
    public void testGetHints() {
        // Arrange (Given)
        String wordStr = "test";
        String category = "category1";
        int difficulty = 1;
        List<String> expectedHints = Arrays.asList("Hint1", "Hint2", "Hint3");

        // Act (When)
        Word word = new Word(wordStr, category, difficulty, expectedHints, printStream);

        // Assert (Then)
        assertEquals(expectedHints, word.getHints(), "Подсказки должны совпадать с ожидаемыми.");
    }
}
