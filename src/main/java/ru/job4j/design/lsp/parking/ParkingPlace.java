package ru.job4j.design.lsp.parking;

import java.util.HashMap;
import java.util.Map;

public class ParkingPlace implements Parking {
    private final Map<Car, Integer> carParking;
    private final Map<Car, Integer> truckParking;
    private int carSize;
    private int truckSize;

    public ParkingPlace(int carSize, int truckSize) {
        this.carParking = new HashMap<>();
        this.truckParking = new HashMap<>();
        this.carSize = carSize;
        this.truckSize = truckSize;
    }

    @Override
    public boolean takePlace(Cars car) {
        return true;
    }

    @Override
    public boolean freePlace(Cars car) {
        return true;
    }
}
