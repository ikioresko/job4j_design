package ru.job4j.finder;

import java.util.Objects;

/**
 * Класс принимает и обрабатывает переданные ему аргументы в виде массива строк.
 * Подробности {@link Run} в методе main.
 * Если данные отсутствуют, или отсутсвует часть необходимых данных, генерируется
 * исключение IllegalArgumentException.
 *
 * @author Igor Kioresko
 * @version 0.1
 */
public class Keys {
    private final String[] keys;

    public Keys(String[] keys) {
        this.keys = keys;
    }

    public boolean valid() {
        if (keys.length != 4
                || Objects.equals(directory(), null)
                || Objects.equals(fileName(), null)
                || Objects.equals(type(), null)
                || Objects.equals(output(), null)) {
            throw new IllegalArgumentException("Root folder is null, or configs is absent. "
                    + "Usage:  java -jar find.jar -d=c:/ -n=*.txt -t=mask -o=log.txt"
                    + "-o RESULT_FILENAME.*");
        }
        return true;
    }

    public String directory() {
        return keys[0].substring(3);
    }

    public String fileName() {
        return keys[1].substring(3);
    }

    public String type() {
        return keys[2].substring(3);
    }

    public String output() {
        return keys[3].substring(3);
    }
}
