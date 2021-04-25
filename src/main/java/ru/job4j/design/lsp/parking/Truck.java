package ru.job4j.design.lsp.parking;

public class Truck implements Cars {
    private final int size;

    public Truck(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }
}