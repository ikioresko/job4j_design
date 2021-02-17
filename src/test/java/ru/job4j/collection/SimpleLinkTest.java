package ru.job4j.collection;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkTest {

    @Test
    public void whenAddAndGet() {
        SimpleLink<String> sl = new SimpleLink<>();
        sl.add("1");
        sl.add("2");
        sl.add("3");
        assertThat(sl.get(0), is("1"));
        assertThat(sl.get(1), is("2"));
        assertThat(sl.get(2), is("3"));
    }

    @Test
    public void whenHasNext() {
        SimpleLink<String> sl = new SimpleLink<>();
        sl.add("1");
        sl.add("2");
        Iterator<String> iterator = sl.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("2"));
        assertThat(iterator.hasNext(), is(false));

    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenHasNextModificationException() {
        SimpleLink<String> sl = new SimpleLink<>();
        sl.add("1");
        sl.add("2");
        Iterator<String> iterator = sl.iterator();
        sl.add("3");
        assertThat(iterator.hasNext(), is(true));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddAndGetOutOfBounds() {
        SimpleLink<String> sl = new SimpleLink<>();
        sl.add("1");
        sl.get(2);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenHasNextNoSuchElementException() {
        SimpleLink<String> sl = new SimpleLink<>();
        sl.add("1");
        Iterator<String> iterator = sl.iterator();
        iterator.next();
        iterator.next();
    }
}