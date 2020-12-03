package com.cursor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("First Task");
        String s = "Today is a very important day: because Anny is 34," +
                " also year ago she was 33. Amazing!";
        System.out.println(s);
        StringAnalyser analyser = new StringAnalyser(s);
        analyser.showData();

        System.out.println("\nSecond Task");
        TextAnalyser textAnalyser = new TextAnalyser("Tolstoi_Lev__Voina_i_mir._Tom_1_www.Litmir.net_27684.txt");
        textAnalyser.show();
    }
}
