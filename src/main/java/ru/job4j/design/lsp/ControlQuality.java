package ru.job4j.design.lsp;

import java.util.List;

/**
 * Класс содержит в себе список хранилищ, в которые производится добавление продуктов
 * в зависимости от условий для вставки в хранилище
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class ControlQuality {
    private final List<StoreHouse> storeHouses;

    public ControlQuality(List<StoreHouse> storeHouses) {
        this.storeHouses = storeHouses;
    }

    /**
     * Сверяет продукт по полям с условиями в хранилище
     * если условия совпадают, добавляет продукт в хранилище
     * @param food продукт
     */
    public void sorter(Food food) {
        for (StoreHouse store : storeHouses) {
            if (store.accept(food)) {
                store.add(food);
            }
        }
    }
}
