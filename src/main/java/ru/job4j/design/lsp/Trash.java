package ru.job4j.design.lsp;

import java.util.ArrayList;
import java.util.List;

public class Trash implements StoreHouse {
    private final List<Food> storage;

    public Trash() {
        this.storage = new ArrayList<>();
    }

    @Override
    public void add(Food food) {
        storage.add(food);
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}