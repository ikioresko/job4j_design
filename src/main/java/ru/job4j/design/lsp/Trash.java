package ru.job4j.design.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует хранилище корзины, который содержит продукты, срок годности которых
 * израсходован на 100%
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class Trash implements StoreHouse {
    private final List<Food> storage;

    public Trash() {
        this.storage = new ArrayList<>();
    }

    @Override
    public boolean accept(Food food) {
        return food.getLeftPercent() < 0;
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