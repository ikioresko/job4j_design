package ru.job4j.design.lsp;

public interface StoreHouse {
    boolean accept(Food food);

    void add(Food food);

    int getSize();
}