package backend.academy.hangman;

import java.io.IOException;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogic {
    private static final int MAX_LAVEL = 3;
    private WordSelector wordSelector;
    private HangmanAnimation hangmanAnimation;
    private Guess player;
    private Word wordToGuess;
    private String currentGuess;
    private List<String> hints;
    private List<String> categories;
    private PrintStream printStream;

    private SecureRandom secureRandom;
    private int hintIndex = 0; // Для выдачи подсказок по порядку
    private static final Logger LOGGER = Logger.getLogger(GameLogic.class.getName());

    public GameLogic(String filePath, PrintStream printStream) {
        this.wordSelector = new WordSelector(printStream);
        this.printStream = printStream;
        try {
            wordSelector.loadWords(filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Не удалось загрузить слова. Завершение программы.", e);
            throw new RuntimeException("Ошибка загрузки слов: " + e.getMessage(), e); // Завершаем программу
        }
        this.hangmanAnimation = new HangmanAnimation(printStream);
        this.player = new Guess(printStream);
        this.categories = wordSelector.getCategories();
        this.secureRandom = new SecureRandom();
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); // Для целых чисел
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Integer getValidInput(int randomNumber, Scanner scanner,
        Integer lowedBound, Integer upperBound) {
        String inputString = null;
        Integer temp = null;
        Integer result = null;

        while (true) {
            inputString = scanner.nextLine();
            if (inputString.isEmpty()) {
                result = randomNumber;
                break;
            }
            if (!isNumeric(inputString)) {
                printStream.println("Ошибка: ввод должен быть числом.");
            } else {
                temp = Integer.parseInt(inputString);
                if (temp < lowedBound || temp > upperBound) {
                    printStream.println("Ошибка: Значение должно быть в диапазоне ["
                        + lowedBound + " ; " + upperBound + "].");
                } else {
                    result = temp;
                    break;
                }
            }
            printStream.print("Повторите ввод значнения (от " + lowedBound + " до " + upperBound + "): ");
        }

        return result;
    }

    protected String selectCategories(Scanner scanner) {
        int randomCategoriesNumber = secureRandom.nextInt(categories.size()) + 1;
        return categories.get(getValidInput(randomCategoriesNumber, scanner, 1, categories.size()) - 1);
    }

    protected Integer selectDifficulty(Scanner scanner) {
        int randomDifficultNumber = secureRandom.nextInt(1, MAX_LAVEL);
        return getValidInput(randomDifficultNumber, scanner, 1, MAX_LAVEL);
    }

    public void start(Scanner scanner, PrintStream printStream) {

        printStream.println("Добро пожаловать в игру 'Виселица'!");

        // Выбор категории
        printStream.println("Категории: ");

        for (int i = 0; i < categories.size(); i++) {
            printStream.println(i + 1 + " " + categories.get(i));
        }

        printStream.print("Выберите номер категории (иначе будет выбрана случайная категория): ");

        String category = null;
        category = selectCategories(scanner);
        printStream.print("Выберите уровень сложности 1 - легко, 2 - средне, "
            + "3 - сложно (иначе будет выбран сложный уровень): ");

        Integer difficulty = null;
        difficulty = selectDifficulty(scanner);

        // Получение случайного слова на основе выбранных параметров
        this.wordToGuess = wordSelector.getWord(category, difficulty);
        this.hints = wordToGuess.getHints();

        this.currentGuess = "_".repeat(wordToGuess.getWord().length());

        printStream.println("Категория: " + category + ", Сложность: " + difficulty);
        play();
    }

    private void play() {

        int stage = hangmanAnimation.MAX_ATTEMPTS - wordToGuess.getMaxMistakes();
        int mistakesCounter = 0;
        while (stage < hangmanAnimation.MAX_ATTEMPTS  && !currentGuess.equals(wordToGuess.getWord())) {
            printStream.println('\n' + "Чтобы получить подсказку введите '!'");
            printStream.println("Слово: " + currentGuess);
            printStream.println("Оставшиеся попытки: " + (wordToGuess.getMaxMistakes() - mistakesCounter));

            char guess = player.makeGuess();  // Получение строки от пользователя

            if (guess == '!') {
                provideHint();
                continue;
            }

            if (wordToGuess.getWord().contains(String.valueOf(guess))) {
                updateCurrentGuess(guess);
            } else {
                printStream.println(hangmanAnimation.getFigure()[stage]);
                stage++;
                mistakesCounter++;
            }
        }

        if (currentGuess.equals(wordToGuess.getWord())) {
            printStream.println("Поздравляем! Вы угадали слово: " + wordToGuess.getWord());
        } else {
            printStream.println("Вы проиграли. Загаданное слово было: " + wordToGuess.getWord());
        }
    }

    protected void updateCurrentGuess(char guess) {
        StringBuilder updatedGuess = new StringBuilder(currentGuess);
        for (int i = 0; i < wordToGuess.getWord().length(); i++) {
            if (wordToGuess.getWord().charAt(i) == guess) {
                updatedGuess.setCharAt(i, guess);
            }
        }
        currentGuess = updatedGuess.toString();
    }

    protected void provideHint() {
        if (hintIndex < hints.size()) {
            printStream.println("Подсказка: " + hints.get(hintIndex));
            hintIndex++;
        } else {
            printStream.println("Больше нет доступных подсказок.");
        }
    }
}
