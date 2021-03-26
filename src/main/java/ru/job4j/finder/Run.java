package ru.job4j.finder;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс содержит метод main который принимает массив аргументов в виде строк,
 * производит запуск поиска файла(ов) и записывает результаты в указанный файл.
 *
 * @author Igor Kioresko
 * @version 0.2
 */
public class Run {
    /**
     * @param type     тип поиска (по маске, по полному совпадение имени, по регулярному выражению);
     * @param fileName имя файла, маска, либо регулярное выражение;
     * @return возвращает Predicate в зависимости от указанного типа поиска.
     */
    private static Predicate<String> searchType(String type, String fileName) {
        Predicate<String> predicate = null;
        if (type.equals("mask")) {
            String name = fileName.replace("*", "");
            predicate = t -> t.contains(name);
        }
        if (type.equals("name")) {
            predicate = t -> t.equals(fileName);
        }
        if (type.equals("regex")) {
            String name = fileName.replace("\"", "");
            Pattern pattern = Pattern.compile(name);
            Matcher matcher = pattern.matcher("");
            predicate = t -> matcher.reset(t).matches();
        }
        return predicate;
    }

    /**
     * Метод совершает запись результатов поиска в файл.
     *
     * @param paths  List совпадений в результе поиска;
     * @param output Файл в который будет записан результат.
     */
    private static void write(List<Path> paths, String output) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(output)))) {
            for (Path path : paths) {
                out.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод производит обход по файловому дереву и совершает поиск файлов
     * согласно полученному предикату.
     * @param path      директория, в которой начнется поиск;
     * @param predicate Predicate по которому происходит поиск файлов;
     * @return Возвращает List с записанными совпадениями в результе поиска;
     * @throws IOException метод обхода по файловому дереву Files.walkFileTree()
     *                     может сгенерировать исключение.
     */
    private static List<Path> find(String path, Predicate<String> predicate) throws IOException {
        Search search = new Search(predicate);
        Path start = Paths.get(path);
        Files.walkFileTree(start, search);
        return search.getPaths();
    }

    /**
     * @param args принимает массив аргументов в следующем виде:
     *             * -d=c:/ -n=*.txt -t=mask -o=log.txt, где
     *             * -d - директория, в которой начинать поиск.
     *             * -n - имя файла, маска, либо регулярное выражение.
     *             * -t - тип поиска: mask искать по маске, name по полному совпадение имени,
     *             *      regex по регулярному выражению.
     *             * -o - результат записать в файл.
     *             Передает аргументы в класс {@link Keys} для валидации введенных данных,
     *             если данные были введены верно, определяет нужный тип поиска в методе
     *             searchType(), вызывает метод find() который начинает поиск файлов,
     *             затем вызывает метод write(), для записи результатов в файл.
     * @throws IOException метод find() может сгенерировать исключение.
     */
    public static void main(String[] args) throws IOException {
        Keys keys = new Keys(args);
        if (keys.valid()) {
            Predicate<String> predicate = searchType(keys.type(), keys.fileName());
            List<Path> result = find(keys.directory(), predicate);
            write(result, keys.output());
        }
    }
}