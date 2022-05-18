package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            List<String> timeLines = new ArrayList<>();
            System.out.println("Enter the input. Empty line input - end of input");
            String line;
            int i = 1;
            int MAX_COUNT_S = 100000;
            while (!(line = reader.readLine()).isEmpty() && i <= MAX_COUNT_S) {
                timeLines.add(line);
                i++;
            }
            System.out.println(Query.parse(timeLines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
