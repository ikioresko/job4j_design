package ru.job4j.design.lsp.parking;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class ParkingTest {
    @Test
    public void whenCarAddDone() {
        Parking parking = new ParkingPlace(1, 2);
        Cars car = new Car(1);
        boolean isDone = parking.takePlace(car);
        assertThat(isDone, is(true));
    }

    @Test
    public void whenTruckAddDone() {
        Parking parking = new ParkingPlace(1, 3);
        Cars truck = new Truck(2);
        boolean isDone = parking.takePlace(truck);
        assertThat(isDone, is(true));
    }

    @Ignore
    @Test
    public void whenTruckAddFalse() {
        Parking parking = new ParkingPlace(2, 0);
        Cars truck = new Truck(3);
        boolean isDone = parking.takePlace(truck);
        assertThat(isDone, is(false));
    }

    @Ignore
    @Test
    public void whenFreeSpaceOnlyForTruck() {
        Parking parking = new ParkingPlace(0, 3);
        Cars car = new Car(1);
        boolean isDone = parking.takePlace(car);
        assertThat(isDone, is(false));
    }

    @Test
    public void whenTruckInCarPlaceDone() {
        Parking parking = new ParkingPlace(3, 0);
        Cars truck = new Truck(2);
        boolean isDone = parking.takePlace(truck);
        assertThat(isDone, is(true));
    }
}