package ru.job4j.design.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует хранилище склада, который содержит продукты, срок годности которых
 * израсходован меньше чем на 25%
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class Warehouse implements StoreHouse {
    private final List<Food> storage;

    public Warehouse() {
        this.storage = new ArrayList<>();
    }

    @Override
    public boolean accept(Food food) {
        return food.getLeftPercent() > 75;
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