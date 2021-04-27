package ru.job4j.design.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит в себе список хранилищ, в которые производится добавление продуктов
 * в зависимости от условий для вставки в хранилище
 *
 * @author Kioresko Igor
 * @version 0.3
 */
public class ControlQuality {
    private final List<StoreHouse> storeHouses;

    public ControlQuality(List<StoreHouse> storeHouses) {
        this.storeHouses = storeHouses;
    }

    /**
     * Сверяет продукт по полям с условиями в хранилище
     * если условия совпадают, добавляет продукт в хранилище
     *
     * @param food продукт
     */
    public void sorter(Food food) {
        for (StoreHouse store : storeHouses) {
            if (store.accept(food)) {
                store.add(food);
            }
        }
    }

    /**
     * Вспомогательный метод, собирает все продукты из всех хранилищ в один список
     * При выполнении метода все хранилища будут очищены
     *
     * @return Список продуктов
     */
    private List<Food> collect() {
        List<Food> list = new ArrayList<>();
        for (StoreHouse store : storeHouses) {
            list.addAll(store.remove());
        }
        return list;
    }

    /**
     * Пререраспределяет продукты, предварительно собрав их из всех хранилищ методом collect()
     */
    public void resort() {
        List<Food> foodList = collect();
        for (Food food : foodList) {
            sorter(food);
        }
    }
}
