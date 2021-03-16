package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final List<FileProperty> duplicate;
    private final List<FileProperty> propertyList;

    public DuplicatesVisitor() {
        duplicate = new ArrayList<>();
        propertyList = new ArrayList<>();
    }

    public List<FileProperty> getDuplicate() {
        return duplicate;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty files = new FileProperty(
                file.toAbsolutePath().toFile().length(),
                file.toAbsolutePath().getFileName().toString());
        if (propertyList.contains(files)) {
            duplicate.add(files);
        } else {
            propertyList.add(files);
        }
        return super.visitFile(file, attrs);
    }
}