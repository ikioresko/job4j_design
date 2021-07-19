package ru.job4j.scanner;

import ru.job4j.io.ArgsName;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * Класс читает данные из CSV файла и выводит их в консоль или в файл
 *
 * @author Igor Kioresko
 * @version 0.1
 */
public class CSVReader {
    /**
     * Запускает процесс вывода информации из CSV файла по заданным критериям
     *
     * @param path      Путь к файлу CSV
     * @param delimiter Разделитель столбцов для вывода результата
     * @param out       Параметр вывода (в консоль или в файл)
     * @param filter    Фильтр столбцов
     * @throws IOException IOException – if an I/O error occurs opening source
     */
    public void run(String path, String delimiter, String out, String filter)
            throws IOException {
        Path pathCSV = Path.of(path);
        List<String> ls = scan(pathCSV);
        List<String> fl = doFilter(filter);
        List<Integer> li = getIndexes(ls, fl);
        if (out.equals("stdout")) {
            stdout(doOut(pathCSV, li, delimiter));
        } else {
            fileOut(out, doOut(pathCSV, li, delimiter));
        }
    }

    /**
     * @param filter Строковое значение фильтра
     * @return Возвращает значения фильтра в виде коллекции
     */
    private List<String> doFilter(String filter) {
        return List.of(filter.split(","));
    }

    /**
     * @param ls     Заголовок таблицы CSV в виде коллекции
     * @param filter Столбцы которые нееобходимо вывести
     * @return Возвращает индексы искомых столбцов таблицы CSV в виде коллекции
     */
    private List<Integer> getIndexes(List<String> ls, List<String> filter) {
        List<Integer> indexes = new ArrayList<>();
        for (String key : filter) {
            int index = ls.indexOf(key);
            if (index != -1) {
                indexes.add(index);
            }
        }
        return indexes;
    }

    /**
     * @param path Путь к файлу CSV
     * @return Возвращает заголовок таблицы CSV в виде коллекции
     * @throws IOException – if an I/O error occurs opening source
     */
    private List<String> scan(Path path) throws IOException {
        Scanner scanner = new Scanner(path);
        String str = scanner.nextLine();
        List<String> ls = Arrays.asList(str.replace(" ", "").split(","));
        scanner.close();
        return ls;
    }

    /**
     * @param path       Путь к файлу CSV
     * @param getIndexes Индексы необходимых строк
     * @param delimiter  Разделитель
     * @return Возвращает StringBuilder в виде столбцов с информацией из CSV
     * по указанному фильтру и с указанным делителем
     * @throws IOException – if an I/O error occurs opening source
     */
    private StringBuilder doOut(Path path, List<Integer> getIndexes, String delimiter)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            List<String> lss = Arrays.asList(scanner.nextLine().split(","));
            for (Integer index : getIndexes) {
                sb.append(lss.get(index)).append(delimiter);
            }
            sb.append(System.lineSeparator());
        }
        scanner.close();
        return sb;
    }

    /**
     * Выводит результат в консоль
     *
     * @param sb Результат с информацией из CSV
     */
    private void stdout(StringBuilder sb) {
        System.out.println(sb);
    }

    /**
     * Производит запись результата в файл
     *
     * @param out Путь к файлу для записи
     * @param sb  Результат с информацией из CSV
     * @throws IOException write()
     */
    private void fileOut(String out, StringBuilder sb) throws IOException {
        try (FileWriter writer = new FileWriter(out)) {
            writer.write(String.valueOf(sb));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Производит валидацию аргументов
     *
     * @param args Массив аргументов для запуска программы
     */
    private void check(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException(
                    "Root folder and file extension is missing."
                            + " Usage java -jar dir.jar ROOT_FOLDER DELIMITER OUTPUT FILTER"
                            + " For example:"
                            + " -path=file.txt -delimiter=; -out=stdout -filter=name,age");
        }
    }

    /**
     * Инициализирует запуск программы
     *
     * @param args Массив аргументов для запуска программы
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     *                     This class is the general class of exceptions
     *                     produced by failed or interrupted I/O operations.
     */
    public void init(String[] args) throws IOException {
        check(args);
        ArgsName arg = ArgsName.of(args);
        run(arg.get("path"), arg.get("delimiter"), arg.get("out"), arg.get("filter"));
    }

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader();
        csv.init(args);
    }
}
