package ru.job4j.kiss;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaxMinTest {
    private final List<String> list1 = new ArrayList<>();
    private final List<String> list2 = List.of("a");
    private final List<String> list3 = List.of("a", "b", "c", "d", "e", "f", "g", "h");

    @Test
    public void max() {
        assertThat(new MaxMin().getResult(list3, Comparator.naturalOrder()), is("h"));
    }

    @Test
    public void min() {
        assertThat(new MaxMin().getResult(list3, Comparator.reverseOrder()), is("a"));
    }

    @Test
    public void whenOneElement() {
        assertThat(new MaxMin().getResult(list2, Comparator.naturalOrder()), is("a"));
        assertThat(new MaxMin().getResult(list2, Comparator.reverseOrder()), is("a"));
    }

    @Test
    public void maxWhenNoneElement() {
        assertNull(new MaxMin().getResult(list1, Comparator.naturalOrder()));
    }
}