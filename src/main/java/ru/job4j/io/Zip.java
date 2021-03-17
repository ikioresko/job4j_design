package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<File> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(
                new FileOutputStream(target)))) {
            for (File file : sources) {
                zip.putNextEntry(new ZipEntry(file.getPath()));
                try (BufferedInputStream out = new BufferedInputStream(
                        new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void packSingleFile(File source, File target) {
//        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(
//                new FileOutputStream(target)))) {
//            zip.putNextEntry(new ZipEntry(source.getPath()));
//            try (BufferedInputStream out = new BufferedInputStream(
//            new FileInputStream(source))) {
//                zip.write(out.readAllBytes());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws IOException {
        ArgZip argZip = new ArgZip(args);
        if (argZip.valid()) {
            Path start = Paths.get(argZip.directory());
            List<File> files = Search.search(start, p -> !p.toFile()
                    .getName()
                    .endsWith(argZip.exclude()))
                    .stream()
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            new Zip().packFiles(files, new File(argZip.output()));
        }
    }
}
