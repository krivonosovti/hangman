package backend.academy.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

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
    private int hintIndex = 0; // Для выдачи подсказок по порядку


    public GameLogic(String filePath, PrintStream printStream) {
        this.wordSelector = new WordSelector(printStream);
        this.printStream = printStream;
        try {
            wordSelector.loadWords(filePath);
        } catch (IOException e) {
            printStream.println("Критическая ошибка: не удалось загрузить слова. Завершение программы.");
            throw new RuntimeException("Ошибка загрузки слов: " + e.getMessage(), e); // Завершаем программу
        }
        this.hangmanAnimation = new HangmanAnimation(printStream);
        this.player = new Guess(printStream);
        this.categories = wordSelector.getCategories();
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); // Для целых чисел
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void start(InputStream inputStream, PrintStream printStream) {
        Scanner scanner = new Scanner(inputStream);
        int randomCategoriesNumber = new SecureRandom().nextInt(categories.size());
        int randomDifficultNumber = new SecureRandom().nextInt(MAX_LAVEL);

        printStream.println("Добро пожаловать в игру 'Виселица'!");

        // Выбор категории
        printStream.println("Категории: ");

        for (int i = 0; i < categories.size(); i++) {
            printStream.println(i + 1 + " " + categories.get(i));
        }

        printStream.print("Выберите номер категории (иначе будет выбрана случайная категория): ");
        String categoryInput = scanner.nextLine();
        String category = null;

        // Проверка, если введена пустая строка — выбираем случайную категорию
        if (categoryInput.isEmpty()) {
            category = categories.get(randomCategoriesNumber); // Выбор случайной категории
        } else {
            // Проверка ввода, если строка не пуста
            while (!isNumeric(categoryInput) || Integer.parseInt(categoryInput) < 1
                || Integer.parseInt(categoryInput) > categories.size()) {
                if (!isNumeric(categoryInput)) {
                    printStream.println("Ошибка: вводите число.");
                } else {
                    printStream.println("Ошибка: номер категории должен быть в диапазоне от 1 до " + categories.size());
                }
                printStream.print("Повторите ввод категории: ");
                categoryInput = scanner.nextLine();

                // Если пользователь снова вводит пустую строку, выбираем случайную категорию
                if (categoryInput.isEmpty()) {
                    category = categories.get(randomCategoriesNumber);
                    break;
                }
            }

            // Если ввод корректен, выбираем указанную категорию
            if (!categoryInput.isEmpty()) {
                category = categories.get(Integer.parseInt(categoryInput) - 1);
            }
        }

        // CHECKSTYLE:OFF
        printStream.print("Выберите уровень сложности 1 - легко, 2 - средне, 3 - сложно (иначе будет выбран сложный уровень): ");
        // CHECKSTYLE:ON
        String difficultyInput = scanner.nextLine();
        Integer difficulty = null;

        if (difficultyInput.isEmpty()) {
            difficulty = randomDifficultNumber;
        } else {
            // Проверка корректности числового ввода и диапазона
            while (!isNumeric(difficultyInput) || Integer.parseInt(difficultyInput) < 1
                || Integer.parseInt(difficultyInput) > MAX_LAVEL) {
                if (!isNumeric(difficultyInput)) {
                    printStream.println("Ошибка: ввод должен быть числом.");
                } else {
                    printStream.println("Ошибка: уровень сложности должен быть в диапазоне от 1 до " + MAX_LAVEL + ".");
                }
                printStream.print("Повторите ввод уровня сложности (от 1 до 3): ");
                difficultyInput = scanner.nextLine();

                // Проверка на пустую строку при повторном вводе
                if (difficultyInput.isEmpty()) {
                    difficulty = randomDifficultNumber;
                    break;
                }
            }

            // Если ввод корректен, задаём уровень сложности
            if (!difficultyInput.isEmpty()) {
                difficulty = Integer.parseInt(difficultyInput);
            }
        }

        // Получение случайного слова на основе выбранных параметров
        this.wordToGuess = wordSelector.getWord(category, difficulty);
        this.hints = wordToGuess.getHints();

        this.currentGuess = "_".repeat(wordToGuess.getWord().length());

        hangmanAnimation.setMaxAttempts(wordToGuess.getMaxMistakes());
        printStream.println("Категория: " + category + ", Сложность: " + difficulty);
        play();
    }

    private void play() {
        while (!hangmanAnimation.isGameOver() && !currentGuess.equals(wordToGuess.getWord())) {
            printStream.println('\n' + "Чтобы получить подсказку введите '!'");
            printStream.println("Слово: " + currentGuess);
            printStream.println("Оставшиеся попытки: " + hangmanAnimation.getRemainingAttempts());

            char guess = player.makeGuess();  // Получение строки от пользователя

            if (guess == '!') {
                provideHint();
                continue;
            }

            if (wordToGuess.getWord().contains(String.valueOf(guess))) {
                updateCurrentGuess(guess);
            } else {
                hangmanAnimation.addWrongAttempt();
            }
        }

        if (currentGuess.equals(wordToGuess.getWord())) {
            printStream.println("Поздравляем! Вы угадали слово: " + wordToGuess.getWord());
        } else {
            printStream.println("Вы проиграли. Загаданное слово было: " + wordToGuess.getWord());
        }
    }

    public void updateCurrentGuess(char guess) {
        StringBuilder updatedGuess = new StringBuilder(currentGuess);
        for (int i = 0; i < wordToGuess.getWord().length(); i++) {
            if (wordToGuess.getWord().charAt(i) == guess) {
                updatedGuess.setCharAt(i, guess);
            }
        }
        currentGuess = updatedGuess.toString();
    }

    public void provideHint() {
        if (hintIndex < hints.size()) {
            printStream.println("Подсказка: " + hints.get(hintIndex));
            hintIndex++;
        } else {
            printStream.println("Больше нет доступных подсказок.");
        }
    }

    public WordSelector getWordSelector() {
        return wordSelector;
    }

    public HangmanAnimation getHangmanAnimation() {
        return hangmanAnimation;
    }

    public Guess getPlayer() {
        return player;
    }

    public Word getWordToGuess() {
        return wordToGuess;
    }

    public String getCurrentGuess() {
        return currentGuess;
    }

    public List<String> getHints() {
        return hints;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getHintIndex() {
        return hintIndex;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    public void setWordToGuess(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public void setCurrentGuess(String currentGuess) {
        this.currentGuess = currentGuess;
    }
}
