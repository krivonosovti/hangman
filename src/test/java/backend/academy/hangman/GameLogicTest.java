package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    private GameLogic gameLogic;
    private ByteArrayOutputStream outContent;
    @BeforeEach
    void setUp() throws IOException {
        outContent = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outContent);

        // Arrange (Given)
        String fileContent =
            "apple;Фрукты;1;Hint1;Hint2;Hint3\n" +
                "banana;Фрукты;2;Hint1;Hint2;Hint3;Hint4\n" +
                "carrot;Овощи;1;Hint1;Hint2;Hint3\n";

        // Записываем в DummyFile
        try (FileWriter writer = new FileWriter("src/test/resources/dummyFile.txt")) {
            writer.write(fileContent);
        }

        // Создаем GameLogic и загружаем слова через WordSelector
        gameLogic = new GameLogic("src/test/resources/dummyFile.txt", printStream);
    }

    @Test
    void testSelectCategories_ValidInput() {
        String input = "1\n"; // Пользователь ввел категорию "1"
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String selectedCategory = gameLogic.selectCategories(scanner);
        assertEquals("Овощи", selectedCategory); // Проверяем, что выбрана категория "Фрукты"
    }

    @Test
    void testSelectCategories_InvalidInputThenValid() {
        String input = "abc\n2\n"; // Неверный ввод, а затем ввод категории "2"
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String selectedCategory = gameLogic.selectCategories(scanner);
        assertEquals("Фрукты", selectedCategory); // Проверяем, что выбрана категория "Овощи"
    }

    @Test
    void testSelectCategories_UnexpectableInputThenValid() {
        String input = "13\n2\n"; // Неверный ввод, а затем ввод категории "2"
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String selectedCategory = gameLogic.selectCategories(scanner);
        assertEquals("Фрукты", selectedCategory); // Проверяем, что выбрана категория "Овощи"
    }

    @Test
    void testSelectDifficulty_ValidInput() {
        String input = "2\n"; // Пользователь выбрал сложность "2"
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Integer selectedDifficulty = gameLogic.selectDifficulty(scanner);
        assertEquals(2, selectedDifficulty); // Проверяем, что выбрана сложность "2"
    }

    @Test
    void testSelectDifficulty_InvalidInputThenValid() {
        String input = "abc\n3\n"; // Неверный ввод, затем выбор сложности "3"
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Integer selectedDifficulty = gameLogic.selectDifficulty(scanner);
        assertEquals(3, selectedDifficulty); // Проверяем, что выбрана сложность "3"
    }

    @Test
    void testSelectDifficulty_UnexpectableInputThenValid() {
        String input = "10\n3\n"; // Неверный ввод, затем выбор сложности "3"
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Integer selectedDifficulty = gameLogic.selectDifficulty(scanner);
        assertEquals(3, selectedDifficulty); // Проверяем, что выбрана сложность "3"
    }


    @Test
    void testSelectDifficulty_Random() {
        String input = "\n"; //  Ввод пустой строки
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String selectedCategory = gameLogic.selectCategories(scanner);
        assertNotNull(selectedCategory);
    }

    @Test
    void testSelectCategorie_Random() {
        String input = "\n"; //  Ввод пустой строки
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));


    }

    @Test
    void testInvalidFilePathShouldThrowRuntimeException() throws IOException {

        String invalidPath = anyString();
        PrintStream printStream = System.out;

        assertThrows(RuntimeException.class, () -> {
            new GameLogic(invalidPath, printStream);
        });
    }
}
