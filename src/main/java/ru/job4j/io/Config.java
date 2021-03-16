package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.lines()
                    .filter(s -> s.length() > 0 && s.contains("="))
                    .forEach(k -> {
                        int index = k.strip().indexOf('=');
                        if (index == k.length() - 1 || index == 0) {
                            throw new IllegalArgumentException();
                        }
                        if (k.contains("#")) {
                            k = k.substring(0, k.indexOf("#")).strip();
                        }
                        values.put(
                                k.substring(0, index),
                                k.substring(index + 1));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}