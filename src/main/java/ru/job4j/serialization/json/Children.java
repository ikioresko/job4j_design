package ru.job4j.serialization.json;

public class Children {
    private final String name;
    private final int age;

    public Children(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Children{"
                + "name='" + name + '\''
                + ", age=" + age + '}';
    }
}
