package ru.job4j.design.lsp;

import java.util.List;

public interface StoreHouse {
    boolean accept(Food food);

    void add(Food food);

    int getSize();

    List<Food> remove();
}