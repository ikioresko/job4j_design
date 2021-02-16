package ru.job4j.collection;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayTest {

    @Test
    public void whenAddThenGet() {
        SimpleArray<String> array = new SimpleArray<>();
        array.add("first");
        String rsl = array.get(0);
        assertThat(rsl, is("first"));
    }

    @Test
    public void whenAddThenIt() {
        SimpleArray<String> array = new SimpleArray<>();
        array.add("first");
        String rsl = array.iterator().next();
        assertThat(rsl, is("first"));
    }

    @Test
    public void whenAddMoreThanArraySizeThenGet() {
        SimpleArray<String> array = new SimpleArray<>(3);
        array.add("1");
        array.add("2");
        array.add("3");
        array.add("4");
        assertThat(array.get(0), is("1"));
        assertThat(array.get(1), is("2"));
        assertThat(array.get(2), is("3"));
        assertThat(array.get(3), is("4"));
    }

    @Test
    public void whenDeleteWithModel() {
        SimpleArray<String> array = new SimpleArray<>(3);
        array.add("1");
        array.add("2");
        array.remove("1");
        assertThat(array.get(0), is("2"));
    }

    @Test
    public void whenDeleteWithIndex() {
        SimpleArray<String> array = new SimpleArray<>(3);
        array.add("1");
        array.add("2");
        array.remove(0);
        assertThat(array.get(0), is("2"));
    }

    @Test
    public void whenSet() {
        SimpleArray<String> array = new SimpleArray<>(5);
        array.add("1");
        array.add("2");
        array.add("3");
        array.set(1, ("50"));
        assertThat(array.get(1), is("50"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetEmpty() {
        SimpleArray<String> array = new SimpleArray<>();
        array.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetOutBound() {
        SimpleArray<String> array = new SimpleArray<>();
        array.add("first");
        array.get(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetEmptyFromIt() {
        SimpleArray<String> array = new SimpleArray<>();
        array.iterator().next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedIt() {
        SimpleArray<String> array = new SimpleArray<>();
        array.add("first");
        Iterator<String> it = array.iterator();
        array.add("second");
        it.next();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteOutBound() {
        SimpleArray<String> array = new SimpleArray<>(3);
        array.add("1");
        array.remove(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenSetOutBound() {
        SimpleArray<String> array = new SimpleArray<>(1);
        array.add("1");
        array.set(1, ("2"));
    }
}