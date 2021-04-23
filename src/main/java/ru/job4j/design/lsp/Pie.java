package ru.job4j.design.lsp;

import java.time.LocalDate;

public class Pie extends Food {
    public Pie(
            String name, LocalDate createDate, LocalDate expiryDate, double price, int discount) {
        super(name, createDate, expiryDate, price, discount);
    }
}