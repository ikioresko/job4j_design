package ru.job4j.design.lsp;

import java.time.LocalDate;

public class Water extends Food {

    public Water(String name, LocalDate createDate,
                 LocalDate expiryDate, double price, int discount) {
        super(name, createDate, expiryDate, price, discount);
    }

    public Water(String name, LocalDate dateNow, LocalDate createDate,
                 LocalDate expiryDate, double price, int discount) {
        super(name, dateNow, createDate, expiryDate, price, discount);
    }
}
