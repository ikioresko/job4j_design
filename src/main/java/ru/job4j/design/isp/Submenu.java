package ru.job4j.design.isp;

public interface Submenu extends Comparable<Submenu> {
    String getName();

    void doAction();
}
