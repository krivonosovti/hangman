package backend.academy.hangman;

import java.io.PrintStream;
import java.util.Scanner;

public class Guess {
    private PrintStream printStream;
    private Scanner scanner = new Scanner(System.in);

    public Guess(PrintStream printStream) {
        this.printStream = printStream;
    }

    // добавить выбор уровня сложности и коллекции
    public char makeGuess() {
        printStream.print("Введите букву: ");
        String input = scanner.nextLine().toLowerCase();
        if (!input.equals("!")) {
            while (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                printStream.print("Пожалуйста, введите одну букву: ");
                input = scanner.nextLine().toLowerCase();
            }
        }
        return input.charAt(0);
    }
}
