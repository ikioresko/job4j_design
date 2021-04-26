package ru.job4j.design.lsp.parking;

import java.util.Objects;

public class Car implements Cars {
    private final int size;

    public Car(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return size == car.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }
}