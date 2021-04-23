package ru.job4j.design.lsp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ControlQuality {
    private final StoreHouse warehouse;
    private final StoreHouse shop;
    private final StoreHouse trash;
    private final LocalDate now;

    public ControlQuality() {
        this.warehouse = new Warehouse();
        this.shop = new Shop();
        this.trash = new Trash();
        this.now = LocalDate.now();
    }

    public ControlQuality(LocalDate date) {
        this.warehouse = new Warehouse();
        this.shop = new Shop();
        this.trash = new Trash();
        this.now = date;
    }

    public int getWarehouseSize() {
        return warehouse.getSize();
    }

    public int getShopSize() {
        return shop.getSize();
    }

    public int getTrashSize() {
        return trash.getSize();
    }

    public void sorter(Food food) {
        LocalDate createDate = food.getCreateDate();
        LocalDate expiryDate = food.getExpiryDate();
        long allDays = createDate.until(expiryDate, ChronoUnit.DAYS);
        long daysLeft = now.until(expiryDate, ChronoUnit.DAYS);
        long leftPercent = daysLeft * 100 / allDays;
        if (leftPercent < 0) {
            trash.add(food);
        } else if (leftPercent <= 25) {
            food.setDiscount(30);
            shop.add(food);
        } else if (leftPercent <= 75) {
            shop.add(food);
        } else {
            warehouse.add(food);
        }
    }
}
