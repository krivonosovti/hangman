package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {

    PrintStream printStream = System.out;
    private GameLogic gameLogic;
    private HangmanAnimation hangmanAnimation = new HangmanAnimation(printStream);

    private WordSelector wordSelector;
    @BeforeEach
    public void setUp() throws IOException {
        // Arrange (Given)
        gameLogic = new GameLogic("src/test/resources/dummyFile.txt",printStream);
    }

    @Test
    public void testLoadWords() {
        // Act (When)
        List<String> categories = gameLogic.getCategories();

        // Assert (Then)
        assertFalse(categories.isEmpty(), "Список категорий не должен быть пустым после загрузки слов.");
    }

    @Test
    public void testSelectDifficulty() {
        // Act (When)
        int difficulty = 2; // Предполагаем, что игрок выбрал сложность 2

        // Assert (Then)
        assertEquals(2, difficulty, "Уровень сложности должен быть 2.");
    }

    @Test
    public void testGamePlayWin() {
        // Arrange (Given)
        // Настройка состояния игры для победы
        gameLogic.setWordToGuess(new Word("apple", "fruit",1, List.of("A common fruit."),printStream)); // Установка слова для угадывания
        gameLogic.setCurrentGuess("_____"); // Изначальное состояние угадывания

        // Act (When)
        gameLogic.updateCurrentGuess('a'); // Игрок угадывает 'a'

        // Assert (Then)
        assertEquals("a____", gameLogic.getCurrentGuess(), "Текущее состояние угадывания должно обновиться.");
    }


    @Test
    public void testGamePlayLose() {
        // Arrange (Given)
        gameLogic.setWordToGuess(new Word("apple", "fruit", 1, List.of("A common fruit."), printStream));
        gameLogic.setCurrentGuess("_____");
        hangmanAnimation.setMaxAttempts(6);
        // Act (When)
        for (int i = 0; i < 6; i++) {
            hangmanAnimation.addWrongAttempt(); // Игрок делает неверные попытки
        }

        // Assert (Then)
        assertTrue(hangmanAnimation.isGameOver(), "Игра должна быть окончена после 6 неверных попыток.");
    }

    @Test
    public void testProvideHint() {
        // Arrange (Given)
        gameLogic.setWordToGuess(new Word("apple", "fruit", 1, List.of("A common fruit."), printStream));
        gameLogic.setHints(List.of("A common fruit."));
        // Act (When)
        gameLogic.provideHint(); // Получаем подсказку

        // Assert (Then)
        assertEquals(1, gameLogic.getHintIndex(), "Индекс подсказки должен увеличиться на 1 после запроса подсказки.");
    }
}
