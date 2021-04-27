package ru.job4j.design.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует хранилище магазина, который содержит продукты, срок годности которых
 * израсходован на 25% и более
 *
 * @author Kioresko Igor
 * @version 0.3
 */
public class Shop implements StoreHouse {
    private final List<Food> storage;

    public Shop() {
        this.storage = new ArrayList<>();
    }

    @Override
    public boolean accept(Food food) {
        long leftPercent = food.getLeftPercent();
        if (leftPercent <= 25 && leftPercent > -1) {
            food.setDiscount(30);
        }
        return leftPercent <= 75 && leftPercent > -1 || food.getDiscount();
    }

    @Override
    public void add(Food food) {
        storage.add(food);
    }

    @Override
    public int getSize() {
        return storage.size();
    }

    @Override
    public List<Food> remove() {
        List<Food> list = new ArrayList<>(storage);
        storage.clear();
        return list;
    }
}