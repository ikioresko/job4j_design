package ru.job4j.design.lsp;

import java.time.LocalDate;

public class Bread extends Food {
    public Bread(
            String name, LocalDate createDate, LocalDate expiryDate, double price, int discount) {
        super(name, createDate, expiryDate, price, discount);
    }
}