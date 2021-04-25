package ru.job4j.design.lsp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Класс описывает сущность продукта
 * Конструктор получающий "LocalDate dateNow" используется для корректного тестирования
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class Food {
    private final String name;
    private final LocalDate createDate;
    private final LocalDate expiryDate;
    private final LocalDate now;
    private final double price;
    private int discount;
    private long leftPercent;

    public Food(
            String name, LocalDate createDate, LocalDate expiryDate, double price, int discount) {
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
        this.price = price;
        this.discount = discount;
        this.now = LocalDate.now();
        leftPercent();
    }

    public Food(String name, LocalDate dateNow, LocalDate createDate, LocalDate expiryDate,
                double price, int discount) {
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
        this.price = price;
        this.discount = discount;
        this.now = dateNow;
        leftPercent();
    }

    public String getName() {
        return name;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean getDiscount() {
        return this.discount > 0;
    }

    public Long getLeftPercent() {
        return this.leftPercent;
    }

    /**
     * Производит расчет срока годности в виде оставшихся процентов
     */
    public void leftPercent() {
        long allDays = createDate.until(expiryDate, ChronoUnit.DAYS);
        long daysLeft = now.until(expiryDate, ChronoUnit.DAYS);
        leftPercent = daysLeft * 100 / allDays;
    }
}