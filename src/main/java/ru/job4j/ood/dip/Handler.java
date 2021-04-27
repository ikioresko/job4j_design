package ru.job4j.ood.dip;

import java.util.List;

public class Handler {
    private final Base base;
    private final List<String> data;

    public Handler(List<String> data, Base base) {
        this.data = data;
        this.base = base;
    }

    public void doWorkWithData(Base base) {
        //Some work with Data and Base
    }
}
