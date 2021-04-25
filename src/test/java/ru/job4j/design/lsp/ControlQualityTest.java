package ru.job4j.design.lsp;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class ControlQualityTest {
    private final LocalDate now = LocalDate.of(2021, 4, 22);
    private final LocalDate createDate1 = LocalDate.of(2021, 4, 1);
    private final LocalDate expiryDate1 = LocalDate.of(2021, 8, 10);
    private final LocalDate expiryDate2 = LocalDate.of(2021, 7, 25);
    private final LocalDate expiryDate3 = LocalDate.of(2021, 5, 31);
    private final LocalDate expiryDate4 = LocalDate.of(2021, 4, 26);
    private final LocalDate expiryDate5 = LocalDate.of(2021, 4, 21);
    private final LocalDate expiryDate6 = LocalDate.of(2021, 4, 22);

    @Test
    public void sortFood() {
        StoreHouse trash = new Trash();
        StoreHouse warehouse = new Warehouse();
        StoreHouse shop = new Shop();
        ControlQuality cq = new ControlQuality(List.of(trash, warehouse, shop));
        cq.sorter(new Bread("Пшеничный", now, createDate1, expiryDate1, 15, 0));
        cq.sorter(new Water("Аква", now, createDate1, expiryDate2, 35, 0));
        cq.sorter(new Pie("Шоколадный", now, createDate1, expiryDate3, 250, 0));
        cq.sorter(new Cheese("Молочный", now, createDate1, expiryDate4, 550, 0));
        cq.sorter(new Bread("Ржаной", now, createDate1, expiryDate5, 40, 0));
        cq.sorter(new Cheese("Голландский", now, createDate1, expiryDate6, 450, 0));
        assertThat(shop.getSize(), is(3));
        assertThat(trash.getSize(), is(1));
        assertThat(warehouse.getSize(), is(2));
    }
}