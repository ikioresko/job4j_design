package ru.job4j.array;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.NoSuchElementException;

public class SimpleArrayTest {

    @Test
    public void testAddAndGet() {
        SimpleArray<String> ss = new SimpleArray<>(5);
        ss.add("Семен");
        ss.add("Василий");
        ss.add("Иван");
        ss.add("Артем");
        assertThat(ss.get(0), is("Семен"));
        assertThat(ss.get(1), is("Василий"));
        assertThat(ss.get(2), is("Иван"));
        assertThat(ss.get(3), is("Артем"));
    }

    @Test
    public void testRemove() {
        SimpleArray<Integer> ss = new SimpleArray<>(3);
        ss.add(10);
        ss.add(20);
        ss.add(30);
        ss.remove(0);
        assertThat(ss.get(0), is(20));
        assertThat(ss.get(1), is(30));
    }

    @Test
    public void testSet() {
        SimpleArray<Integer> ss = new SimpleArray<>(3);
        ss.add(10);
        ss.add(20);
        ss.add(30);
        ss.set(2, 99);
        assertThat(ss.get(0), is(10));
        assertThat(ss.get(1), is(20));
        assertThat(ss.get(2), is(99));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addWhenArrayIsFull() {
        SimpleArray<Integer> ss = new SimpleArray<>(1);
        ss.add(10);
        ss.add(20);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenInvalidIndexOfSetMethod() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        ss.set(3, 99);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenInvalidIndexOfGetMethod() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        ss.get(1001);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenInvalidIndexOfRemoveMethod() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        ss.remove(4);
    }

    @Test
    public void iteratorTest() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        assertThat(ss.iterator().hasNext(), is(true));
        assertThat(ss.iterator().next(), is(10));
        assertThat(ss.iterator().hasNext(), is(true));
        assertThat(ss.iterator().next(), is(20));
        assertThat(ss.iterator().hasNext(), is(false));
    }

    @Test
    public void iteratorTestWhenFirstIsEmpty() {
        SimpleArray<Integer> ss = new SimpleArray<>(3);
        ss.add(null);
        ss.add(null);
        ss.add(20);
        assertThat(ss.iterator().hasNext(), is(true));
        assertNull(ss.iterator().next());
        assertThat(ss.iterator().hasNext(), is(true));
        assertNull(ss.iterator().next());
        assertThat(ss.iterator().hasNext(), is(true));
        assertThat(ss.iterator().next(), is(20));
        assertThat(ss.iterator().hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextFromEmpty() {
        SimpleArray<Integer> ss = new SimpleArray<>(0);
        assertNull(ss.iterator().next());
    }

    @Test
    public void iteratorTestWhenArrayIsEmpty() {
        SimpleArray<Integer> ss = new SimpleArray<>(0);
        assertThat(ss.iterator().hasNext(), is(false));
    }

    @Test
    public void iteratorTestWhenArrayIsTwoEmptyElement() {
        SimpleArray<String> ss = new SimpleArray<>(2);
        ss.add("");
        ss.add("");
        assertThat(ss.iterator().hasNext(), is(true));
        assertThat(ss.iterator().hasNext(), is(true));
    }
}