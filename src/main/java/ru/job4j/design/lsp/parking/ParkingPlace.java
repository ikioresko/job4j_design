package ru.job4j.design.lsp.parking;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс реализует сущность парковки, размещая и удаляя объекты Cars из коллекций
 *
 * @author Kioresko Igor
 * @version 0.3
 */
public class ParkingPlace implements Parking {
    private final Map<Cars, Integer> carParking;
    private final Map<Cars, Integer> truckParking;
    private int carSize;
    private int truckSize;

    public ParkingPlace(int carSize, int truckSize) {
        this.carParking = new HashMap<>();
        this.truckParking = new HashMap<>();
        this.carSize = carSize;
        this.truckSize = truckSize;
    }

    /**
     * Метод размещает авто в коллекцию, если в коллекции есть свободное место
     * объект с размером == 1 размещается только в коллекции carParking
     * объект с размером > 1 размещается в коллекции truckParking, если места нет
     * пытается разместиться в коллекции carParking
     *
     * @param car Объект реализующий интерфейс Cars
     * @return если авто был размещен на парковке - true, иначе false
     */
    @Override
    public boolean takePlace(Cars car) {
        boolean rsl = true;
        int carsTypeSize = car.size();
        if (carsTypeSize > 1 && 1 <= truckSize) {
            truckParking.put(car, carsTypeSize);
            truckSize -= 1;
        } else if (carsTypeSize >= 1 && carsTypeSize <= carSize) {
            carParking.put(car, carsTypeSize);
            carSize -= carsTypeSize;
        } else {
            rsl = false;
        }
        return rsl;
    }

    /**
     * Метод извлекает объект авто из коллекции, если он в нем присутствует
     *
     * @param car Объект реализующий интерфейс Cars
     * @return если авто был извлечен из парковки - true, иначе false
     */
    @Override
    public boolean freePlace(Cars car) {
        boolean rsl = true;
        int carsTypeSize = car.size();
        if (carsTypeSize > 1 && truckParking.containsKey(car)) {
            truckParking.remove(car);
            truckSize += 1;
        } else if (carsTypeSize >= 1 && carParking.containsKey(car)) {
            carParking.remove(car);
            carSize += carsTypeSize;
        } else {
            rsl = false;
        }
        return rsl;
    }
}
