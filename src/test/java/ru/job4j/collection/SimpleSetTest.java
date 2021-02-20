package ru.job4j.collection;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class SimpleSetTest {

    @Test
    public void whenAdd() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(null);
        assertThat(set.add(3), is(false));
        assertThat(set.add(5), is(true));
        assertThat(set.add(null), is(false));
    }

    @Test
    public void whenHasIterator() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(2);
        set.add(3);
        set.add(null);
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.hasNext(), is(true));
        assertNull(iterator.next());
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenEmptySetHasIteratorAndNSEException() {
        SimpleSet<Integer> set = new SimpleSet<>();
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenConcurrentExp() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(1);
        Iterator<Integer> iterator = set.iterator();
        set.add(2);
        iterator.next();
    }
}