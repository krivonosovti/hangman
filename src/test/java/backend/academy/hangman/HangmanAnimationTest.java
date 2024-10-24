package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class HangmanAnimationTest {

    private HangmanAnimation hangmanAnimation;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        hangmanAnimation = new HangmanAnimation(new PrintStream(outputStream));
    }

    @Test
    public void testMaxAttemptsConstant() {
        assertEquals(10, HangmanAnimation.MAX_ATTEMPTS, "MAX_ATTEMPTS should be 10");
    }

    @Test
    public void testInitialFigure() {
        String[] figure = hangmanAnimation.getFigure();
        assertNotNull(figure, "Figure array should not be null");
        assertEquals(10, figure.length, "Figure array should have 10 stages");

        assertEquals(
            "  ________\n" +
                "  | /     \n" +
                "  |/       \n" +
                "  |        \n" +
                "  |         \n" +
                "  |          \n" +
                "  |           \n" +
                "__|__      \n",
            figure[0],
            "Initial figure should match the expected drawing"
        );
    }

    @Test
    public void testFigureAtFinalStage() {
        String[] figure = hangmanAnimation.getFigure();
        assertEquals(
            "  ________\n" +
                "  | /    |\n" +
                "  |/     |\n" +
                "  |      O\n" +
                "  |     /|\\ \n" +
                "  |      | \n" +
                "  |     / \\ \n" +
                "__|__      \n",
            figure[9],
            "Final figure should match the expected drawing"
        );
    }

    @Test
    public void testIntermediateFigures() {
        String[] figure = hangmanAnimation.getFigure();

        // Проверка промежуточных стадий
        assertEquals(
            "  ________\n" +
                "  | /    |\n" +
                "  |/     |\n" +
                "  |      O\n" +
                "  |         \n" +
                "  |          \n" +
                "  |           \n" +
                "__|__      \n",
            figure[3],
            "Intermediate figure (stage 4) should match the expected drawing"
        );

        assertEquals(
            "  ________\n" +
                "  | /    |\n" +
                "  |/     |\n" +
                "  |      O\n" +
                "  |     /| \n" +
                "  |          \n" +
                "  |           \n" +
                "__|__      \n",
            figure[5],
            "Intermediate figure (stage 6) should match the expected drawing"
        );
    }
}
