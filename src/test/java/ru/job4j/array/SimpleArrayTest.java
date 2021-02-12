package ru.job4j.array;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

public class SimpleArrayTest {
    private static final String LN = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

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
        assertNull(ss.get(4));
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
        assertNull(ss.get(2));
    }

    @Test
    public void testSet() {
        SimpleArray<Integer> ss = new SimpleArray<>(3);
        ss.add(10);
        ss.add(20);
        ss.add(30);
        ss.set(1, 99);
        assertThat(ss.get(0), is(10));
        assertThat(ss.get(1), is(99));
        assertThat(ss.get(2), is(30));
    }

    @Test
    public void addWhenArrayIsFull() {
        SimpleArray<Integer> ss = new SimpleArray<>(1);
        ss.add(10);
        ss.add(20);
        assertEquals("Array is full" + LN, outContent.toString());
    }

    @Test
    public void whenInvalidIndexOfSetMethod() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        ss.set(3, 99);
        assertEquals("Index = 3 doesn't exist" + LN, outContent.toString());
    }

    @Test
    public void whenInvalidIndexOfGetMethod() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        ss.get(1001);
        assertEquals("Index = 1001 doesn't exist" + LN, outContent.toString());
    }

    @Test
    public void whenInvalidIndexOfRemoveMethod() {
        SimpleArray<Integer> ss = new SimpleArray<>(2);
        ss.add(10);
        ss.add(20);
        ss.remove(4);
        assertEquals("Index = 4 doesn't exist" + LN, outContent.toString());
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
}