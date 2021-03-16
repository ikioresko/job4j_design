package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final List<FileProperty> duplicate;
    private final Set<FileProperty> propertyList;

    public DuplicatesVisitor() {
        duplicate = new ArrayList<>();
        propertyList = new HashSet<>();
    }

    public List<FileProperty> getDuplicate() {
        return duplicate;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty files = new FileProperty(
                file.toAbsolutePath().toFile().length(),
                file.toAbsolutePath().getFileName().toString());
        if (!propertyList.add(files)) {
            duplicate.add(files);
        }
        return super.visitFile(file, attrs);
    }
}