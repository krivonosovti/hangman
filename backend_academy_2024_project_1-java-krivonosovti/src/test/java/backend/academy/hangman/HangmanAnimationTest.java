package backend.academy.hangman;

import org.junit.jupiter.api.Test;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class HangmanAnimationTest {

    PrintStream printStream = System.out;
    @Test
    public void testInitialRemainingAttempts() {
        // Arrange (Given)
        HangmanAnimation animation = new HangmanAnimation(printStream);

        // Act (When)
        int remainingAttempts = animation.getRemainingAttempts();

        // Assert (Then)
        assertEquals(6, remainingAttempts, "Начальное количество попыток должно быть 6.");
    }

    @Test
    public void testAddWrongAttempt() {
        // Arrange (Given)
        HangmanAnimation animation = new HangmanAnimation(printStream);

        // Act (When)
        animation.addWrongAttempt();
        int remainingAttempts = animation.getRemainingAttempts();

        // Assert (Then)
        assertEquals(5, remainingAttempts, "Количество оставшихся попыток должно уменьшиться на 1.");
    }

    @Test
    public void testGameOver() {
        // Arrange (Given)
        HangmanAnimation animation = new HangmanAnimation(printStream);
        for (int i = 0; i < 6; i++) {
            animation.addWrongAttempt();
        }

        // Act (When)
        boolean gameOver = animation.isGameOver();

        // Assert (Then)
        assertTrue(gameOver, "Игра должна быть окончена после 6 неверных попыток.");
    }

}
