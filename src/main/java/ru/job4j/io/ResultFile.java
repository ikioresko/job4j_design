package ru.job4j.io;

import java.io.FileOutputStream;

public class ResultFile {
    public static String[] table(int num) {
        String[] str = new String[11];
        for (int i = 0; i < 11; i++) {
            str[i] = num + " * " + i + " = " + i * num;
        }
        return str;
    }

    public static void main(String[] args) {
        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            for (String str : table(5)) {
                out.write(str.getBytes());
                out.write(System.lineSeparator().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}