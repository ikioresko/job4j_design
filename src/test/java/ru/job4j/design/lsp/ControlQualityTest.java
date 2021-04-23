package ru.job4j.design.lsp;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class ControlQualityTest {
    private final LocalDate createDate1 = LocalDate.of(2021, 4, 1);
    private final LocalDate expiryDate1 = LocalDate.of(2021, 8, 10);
    private final LocalDate expiryDate2 = LocalDate.of(2021, 7, 25);
    private final LocalDate expiryDate3 = LocalDate.of(2021, 5, 31);
    private final LocalDate expiryDate4 = LocalDate.of(2021, 4, 26);
    private final LocalDate expiryDate5 = LocalDate.of(2021, 4, 21);
    private final LocalDate expiryDate6 = LocalDate.of(2021, 4, 22);

    @Test
    public void sortFood() {
        ControlQuality cq = new ControlQuality(LocalDate.of(2021, 4, 22));
        cq.sorter(new Bread("Пшеничный", createDate1, expiryDate1, 15, 0));
        cq.sorter(new Water("Аква", createDate1, expiryDate2, 35, 0));
        cq.sorter(new Pie("Шоколадный", createDate1, expiryDate3, 250, 0));
        cq.sorter(new Cheese("Молочный", createDate1, expiryDate4, 550, 0));
        cq.sorter(new Bread("Ржаной", createDate1, expiryDate5, 40, 0));
        cq.sorter(new Cheese("Голландский", createDate1, expiryDate6, 450, 0));
        assertThat(cq.getShopSize(), is(3));
        assertThat(cq.getTrashSize(), is(1));
        assertThat(cq.getWarehouseSize(), is(2));
    }
}