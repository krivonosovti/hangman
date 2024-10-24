package backend.academy;

import backend.academy.hangman.GameLogic;
import java.io.PrintStream;
import java.util.Scanner;
import lombok.experimental.UtilityClass;


@UtilityClass
public class Main {
    public static void main(String[] args) {
        PrintStream printStream = System.out;
        Scanner scanner = new Scanner(System.in);
        GameLogic game = new GameLogic("src/main/resources/WordsCollections.txt", printStream);
        game.start(scanner, System.out);
    }
}
