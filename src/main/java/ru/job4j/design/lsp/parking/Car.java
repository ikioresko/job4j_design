package ru.job4j.design.lsp.parking;

public class Car implements Cars {
    private final int size;

    public Car(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }
}