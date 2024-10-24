package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WordSelectorTest {
    PrintStream printStream = System.out;

    private WordSelector wordSelector;

    @BeforeEach
    public void setUp() {
        wordSelector = new WordSelector(printStream);
    }


    @Test
    public void testLoadWordsFromValidFile() throws IOException {

        // Arrange (Given)
        String fileContent =
            "apple;fruits;1;Hint1;Hint2;Hint3\n" +
                "banana;fruits;2;Hint1;Hint2;Hint3;Hint4\n" +
                "carrot;vegetables;1;Hint1;Hint2;Hint3\n";
        try (FileWriter writer = new FileWriter("src/test/resources/dummyFile.txt")) {
    writer.write(fileContent);

}

        // Act (When)
        wordSelector.loadWords("src/test/resources/dummyFile.txt");  // Используем поддельный файл

        // Assert (Then)
        Map<String, List<Word>> wordMap = wordSelector.getWordMap();
        assertEquals(2, wordMap.size(), "Должны быть загружены две категории (fruits и vegetables).");

        List<Word> fruits = wordMap.get("fruits");
        assertNotNull(fruits, "Категория 'fruits' должна существовать.");
        assertEquals(2, fruits.size(), "В категории 'fruits' должно быть два слова.");

        Word apple = fruits.get(0);
        assertEquals("apple", apple.getWord(), "Первое слово должно быть 'apple'.");
        assertEquals(1, apple.getDifficulty(), "Сложность слова 'apple' должна быть 1.");
        assertEquals(3, apple.getHints().size(), "У слова 'apple' должно быть 3 подсказки.");

        Word banana = fruits.get(1);
        assertEquals("banana", banana.getWord(), "Второе слово должно быть 'banana'.");
        assertEquals(2, banana.getDifficulty(), "Сложность слова 'banana' должна быть 2.");
        assertEquals(4, banana.getHints().size(), "У слова 'banana' должно быть 4 подсказки.");
    }

    @Test
    public void testLoadWordsHandlesInvalidFormat() throws IOException {

        // Arrange (Given)
        String fileContent =
            "apple;fruits;notAnInt;Hint1;Hint2;Hint3\n" +  // Invalid difficulty
                "banana;fruits;2\n" +  // Missing hints
                "carrot;;1;Hint1;Hint2;Hint3\n";  // Missing category

        try (FileWriter writer = new FileWriter("src/test/resources/dummyFile.txt")) {
    writer.write(fileContent);

}

        // Act (When)
        wordSelector.loadWords("src/test/resources/dummyFile.txt");  // Используем поддельный файл

        // Assert (Then)
        Map<String, List<Word>> wordMap = wordSelector.getWordMap();
        assertEquals(1, wordMap.size(), "Должна быть загружена только одна категория.");

        List<Word> fruits = wordMap.get("fruits");
        assertNull(fruits, "Категория 'fruits' не должна существовать.");
        //assertEquals(1, fruits.size(), "В категории 'fruits' должно быть одно слово.");
    }

    @Test
    public void testGetWordByCategoryAndDifficulty() throws IOException {
        // Arrange (Given)
        String fileContent =
            "apple;fruits;1;Hint1;Hint2;Hint3\n" +
                "banana;fruits;2;Hint1;Hint2;Hint3;Hint4\n" +
                "carrot;vegetables;1;Hint1;Hint2;Hint3\n";

        try (FileWriter writer = new FileWriter("src/test/resources/dummyFile.txt")) {
    writer.write(fileContent);
}

        wordSelector.loadWords("src/test/resources/dummyFile.txt");

        // Act (When)
        Word word = wordSelector.getWord("fruits", 2);

        // Assert (Then)
        assertNotNull(word, "Слово не должно быть null.");
        assertEquals("banana", word.getWord(), "Должно быть выбрано слово 'banana'.");
        assertEquals(4, word.getHints().size(), "Слово 'banana' должно иметь 4 подсказки.");
    }

    @Test
    public void testGetCategories() throws IOException {
        // Arrange (Given)
        String fileContent =
            "apple;fruits;1;Hint1;Hint2;Hint3\n" +
                "banana;fruits;2;Hint1;Hint2;Hint3;Hint4\n" +
                "carrot;vegetables;1;Hint1;Hint2;Hint3\n";
        try (FileWriter writer = new FileWriter("src/test/resources/dummyFile.txt")) {
    writer.write(fileContent);
}

        wordSelector.loadWords("src/test/resources/dummyFile.txt");

        // Act (When)
        List<String> categories = wordSelector.getCategories();

        // Assert (Then)
        assertEquals(2, categories.size(), "Должно быть две категории.");
        assertTrue(categories.contains("fruits"), "Категория 'fruits' должна присутствовать.");
        assertTrue(categories.contains("vegetables"), "Категория 'vegetables' должна присутствовать.");
    }

}

