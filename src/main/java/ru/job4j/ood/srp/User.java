package ru.job4j.ood.srp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final List<String> list;
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        list = new ArrayList<>();
    }

    public void add(Storage store) {
        store.add(this);
    }

    @Override
    public String toString() {
        return "User{"
                + "list=" + list
                + ", name='" + name + '\''
                + ", age=" + age
                + '}';
    }
}
