package com.cursor;

import java.io.*;
import java.util.*;

public class TextAnalyser {
    private Scanner scanner;
    private FileInputStream file;

    public TextAnalyser(String fileName) throws IOException {
        file = new FileInputStream(fileName);
        DataInputStream in = new DataInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        scanner = new Scanner(bufferedReader);
    }

    private List<String> getWords() {
        List<String> words = new ArrayList<>();
        scanner.useDelimiter("\\p{Space}");
        while (scanner.hasNext()) {
            String value = scanner.next();
            value = value.replaceAll("[,.;:!?\"<>'{}\\[\\]()—*+%$@|\\\\/“”]+", "")
                    .replaceAll("\\p{Digit}", "").trim();
            if (value.contains("-") && value.length() == 1) {
                continue;
            }
            if (!value.isBlank()) {
                words.add(value.toLowerCase());
            }
        }
        return words;
    }


    public void show() throws IOException {
        List<String> words = getWords();
        String min = getMin(words);
        System.out.println("Min: " + min + " - " + getAmount(words, min));
        String max = getMax(words);
        System.out.println("Max: " + max + " - " + getAmount(words, max));
        file.close();
    }

    private String getMin(List<String> words) {
        return words
                .stream()
                .sorted(Comparator.naturalOrder())
                .filter(s -> s.length() > 0)
                .min(Comparator.comparing(String::length))
                .get();
    }

    private String getMax(List<String> words) {
        return words
                .stream()
                .sorted(Comparator.reverseOrder())
                .max(Comparator.comparing(String::length))
                .get();
    }

    private int getAmount(List<String> words, String word) {
        int amount = 0;
        for (String s : words) {
            if (word.equals(s)) {
                amount++;
            }
        }
        return amount;
    }
}