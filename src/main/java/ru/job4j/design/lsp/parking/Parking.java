package ru.job4j.design.lsp.parking;

public interface Parking {
    boolean takePlace(Cars car);

    boolean freePlace(Cars car);
}