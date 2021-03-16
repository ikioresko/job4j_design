package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        if (args.length != 2 || Objects.equals(args[0], null)
                || Objects.equals(args[1], null)) {
            throw new IllegalArgumentException(
                    "Root folder and file extension is missing."
                            + "Usage java -jar dir.jar ROOT_FOLDER FILE_EXTENSION.");
        }
        Path start = Paths.get(args[0]);
        search(start, p -> p.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}