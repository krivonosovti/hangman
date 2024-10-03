package backend.academy;

import backend.academy.hangman.GameLogic;
import java.io.PrintStream;
import lombok.experimental.UtilityClass;


@UtilityClass
public class Main {
    public static void main(String[] args) {
        PrintStream printStream = System.out;
        GameLogic game = new GameLogic("src/main/resources/WordsCollections.txt", printStream);
        game.start(System.in, System.out);
    }
}
