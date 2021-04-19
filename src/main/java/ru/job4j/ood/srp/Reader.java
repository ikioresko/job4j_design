package ru.job4j.ood.srp;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    private List<String> list;

    public Reader() {
        this.list = new ArrayList<>();
    }

    public void read() {
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            list = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(File file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            list.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sort() {
        Collections.sort(list);
    }
}
