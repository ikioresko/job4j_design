package ru.job4j.io;

import java.util.Objects;

public class ArgZip {
    private final String[] args;

    public ArgZip(String[] args) {
        this.args = args;
    }

    public boolean valid() {
        if (args.length != 6
                || Objects.equals(directory(), null)
                || Objects.equals(exclude(), null)
                || Objects.equals(output(), null)) {
            throw new IllegalArgumentException("Root folder is null, or configs is absent. "
                    + "Usage: java -jar pack.jar -d ROOT_FOLDER -e *.EXCLUDE_CLASS"
                    + "-o RESULT_FILENAME.*");
        }
        return true;
    }

    public String directory() {
        return args[1];
    }

    public String exclude() {
        return args[3].substring(1);
    }

    public String output() {
        return args[5];
    }
}