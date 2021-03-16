package ru.job4j.io;

import java.io.File;

public class Dir {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        File file = new File(args[0]);
    }
}