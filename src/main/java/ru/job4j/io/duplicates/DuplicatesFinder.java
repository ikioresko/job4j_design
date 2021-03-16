package ru.job4j.io.duplicates;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class DuplicatesFinder {

    public static void main(String[] args) throws IOException {
        DuplicatesVisitor dv = new DuplicatesVisitor();
        Files.walkFileTree(Path.of(args[0]), dv);
        for (FileProperty fp : dv.getDuplicate()) {
            System.out.println("Founded duplicate: " + " Name: " + fp.getName()
                    + " Size: " + fp.getSize() + " byte");
        }
    }
}