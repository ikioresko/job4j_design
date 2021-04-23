package ru.job4j.design.lsp.parking;

public interface Parking {
    boolean takePlace(Car car);

    boolean freePlace(Car car);
}