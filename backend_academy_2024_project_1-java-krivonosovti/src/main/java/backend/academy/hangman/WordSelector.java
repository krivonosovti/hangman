package backend.academy.hangman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSelector {
    public static final int THREE = 3;
    private Map<String, List<Word>> wordMap; // Карта категории -> список слов
    private PrintStream printStream;


    public WordSelector(PrintStream printStream) {
        this.wordMap = new HashMap<>();
        this.printStream = printStream;
    }


    protected void loadWords(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            new FileInputStream(filename), StandardCharsets.UTF_8))) {

            String line;

            // Чтение строк из файла
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                // Проверяем, что в строке есть минимум THREE элемента: слово, категория, сложность
                if (parts.length < THREE) {
                    printStream.println("Некорректная строка: " + line);
                    continue; // Пропускаем некорректные строки
                }

                String word = parts[0];
                String category = parts[1];
                int difficulty;

                // Проверка сложности и подсказок
                try {
                    difficulty = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    printStream.println("Некорректная сложность для строки: " + line);
                    continue; // Пропускаем строку с неверным форматом сложности
                }

                // Проверяем наличие подсказок
                if (parts.length > THREE) {
                    List<String> hints =
                        new ArrayList<>(Arrays.asList(Arrays.copyOfRange(parts, THREE, parts.length)));

                    if (!hints.isEmpty()) { // Проверяем, что подсказки не пустые
                        Word wordObj = new Word(word, category, difficulty, hints, printStream);
                        wordMap.computeIfAbsent(category, k -> new ArrayList<>()).add(wordObj);
                    } else {
                        printStream.println("Нет подсказок для слова: " + word);
                    }
                } else {
                    printStream.println("Нет подсказок для: " + word);
                }
            }
        } catch (IOException e) {
            printStream.println("Ошибка чтения файла: " + filename);
            throw e;
        }
    }

    // Метод получения слова по категории и сложности
    public Word getWord(String category, int difficulty) {
        List<Word> words = wordMap.getOrDefault(category, new ArrayList<>());
        return words.stream()
            .filter(w -> w.getDifficulty() == difficulty)
            .findAny()
            .orElse(null);
    }

    public Map<String, List<Word>> getWordMap() {
        return wordMap;
    }

    public List<String> getCategories() {
        return new ArrayList<>(wordMap.keySet()); // Возвращаем список ключей из wordMap, т.е. категории
    }


}
