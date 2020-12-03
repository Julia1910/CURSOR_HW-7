package com.cursor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAnalyser {
    private String string;
    private Map<String, Integer> symbols;
    private Map<String, Integer> gaps;
    private Map<String, Integer> punctuation;
    private Map<String, Integer> numbers;
    private Map<String, Integer> letters;


    public StringAnalyser(String string) {
        this.string = string;
        symbols = new LinkedHashMap<>();
        gaps = new LinkedHashMap<>();
        punctuation = new LinkedHashMap<>();
        numbers = new LinkedHashMap<>();
        letters = new LinkedHashMap<>();
    }

    private void getSymbols() {
        List<String> characters = new ArrayList<>(Arrays.asList(string.split("(?!^)")));
        int amount = 1;
        for (int i = 0; i < characters.size(); i++) {
            String symbol = characters.get(i);
            for (int j = i + 1; j < characters.size(); j++) {
                if (symbol.equalsIgnoreCase(characters.get(j))) {
                    amount++;
                    characters.remove(j);
                    j--;
                }
            }
            symbols.put(symbol.toLowerCase(), amount);
            amount = 1;
        }
    }


    private void distribution() {
        Pattern p = Pattern.compile("[.,!?;:\\\\-]");
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            Matcher matcher = p.matcher(entry.getKey());
            if (entry.getKey().equals(" ")) {
                gaps.put(entry.getKey(), entry.getValue());
            } else if (isNumeric(entry.getKey())) {
                numbers.put(entry.getKey(), entry.getValue());
            } else if (matcher.find()) {
                punctuation.put(entry.getKey(), entry.getValue());
            } else {
                letters.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void showData() {
        getSymbols();
        distribution();
        System.out.println("Letters in String and their number of repetitions: ");
        letters.forEach((key, value) -> System.out.print(key + " : " + value + "\n"));
        System.out.println("Total number of letters: " + getAmount(letters));
        System.out.println("Number of gaps: " + getAmount(gaps));
        System.out.println("Number of digits: " + getAmount(numbers));
        System.out.println("Number of punctuation: " + getAmount(punctuation));
   }

   private AtomicInteger getAmount(Map<String,Integer> map) {
       AtomicInteger sum = new AtomicInteger();
       map.forEach((key, value)->{
           sum.addAndGet(value);
       });
       return sum;
   }
}
