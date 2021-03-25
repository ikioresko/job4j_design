package ru.job4j.finder;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Класс просматривает все файлы в заданном каталоге и сравнивает их имя,
 * с именем, или по маске, или по регулярному выражению которое задал пользователь.
 * Совпадения заносятся в коллекцию List.
 *
 * @author Igor Kioresko
 * @version 0.1
 */
public class Search extends SimpleFileVisitor<Path> {
    private final List<Path> paths;
    private final Predicate<String> condition;

    public Search(Predicate<String> condition) {
        paths = new ArrayList<>();
        this.condition = condition;
    }

    public List<Path> getPaths() {
        return paths;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (condition.test(file.getFileName().toString())) {
            paths.add(file.toAbsolutePath());
        }
        return CONTINUE;
    }
}