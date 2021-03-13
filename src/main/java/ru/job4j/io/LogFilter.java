package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    public static List<String> filter(String file) {
        List<String> rsl = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> list = reader.lines().collect(Collectors.toList());
            String regex = "\\d+";
            for (String str : list) {
                String[] array = str.split(" ");
                if (array[array.length - 2].equals("404")
                        && array[array.length - 1].matches(regex)) {
                    rsl.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        System.out.println(log);
    }
}