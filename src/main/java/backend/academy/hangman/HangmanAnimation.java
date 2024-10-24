package backend.academy.hangman;

import java.io.PrintStream;

public class HangmanAnimation {
    public static final int MAX_ATTEMPTS = 10;
    private final String[] figure = new String[MAX_ATTEMPTS];
    private PrintStream printStream;

    // CHECKSTYLE:OFF
    public HangmanAnimation(PrintStream printStream) {
        this.printStream = printStream;
        int i = 0;
        figure[i] =
            "  ________" + '\n'
            + "  | /     " + '\n'
            + "  |/       " + '\n'
            + "  |        " + '\n'
            + "  |         " + '\n'
            + "  |          " + '\n'
            + "  |           " + '\n'
            + "__|__      " + '\n';

        figure[++i] = figure[i - 1].replace("  | /     " + '\n', "  | /    |" + '\n');
        figure[++i] = figure[i - 1].replace("  |/       " + '\n', "  |/     |" + '\n');
        figure[++i] = figure[i - 1].replace("  |        " + '\n', "  |      O" + '\n');
        figure[++i] = figure[i - 1].replace("  |         " + '\n', "  |      | " + '\n');
        figure[++i] = figure[i - 1].replace("  |      | " + '\n', "  |     /| " + '\n');
        figure[++i] = figure[i - 1].replace("  |     /| " + '\n',  "  |     /|\\ " + '\n');
        figure[++i] = figure[i - 1].replace("  |          " + '\n', "  |      | " + '\n');
        figure[++i] = figure[i - 1].replace("  |           " + '\n', "  |     /" + '\n');
        figure[++i] = figure[i - 1].replace("  |     /" + '\n', "  |     / \\ " + '\n');
    }

    // CHECKSTYLE:ON
    public String[] getFigure() {
        return figure;
    }
}

