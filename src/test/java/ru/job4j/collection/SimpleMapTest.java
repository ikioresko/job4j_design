package ru.job4j.collection;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenAddAndGet() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert("map", 0);
        assertThat(map.insert("map", 100), is(true));
        assertThat(map.get("map"), is(100));
        assertThat(map.insert("map2", 200), is(true));
        assertThat(map.get("map2"), is(200));
    }

    @Test
    public void whenAddNullTwiceAndChangeValue() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert(null, 100);
        map.insert(null, 200);
        assertThat(map.get(null), is(200));
    }

    @Test
    public void whenGetIsAbsent() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert("map", 100);
        assertNull(map.get("map2"));
    }

    @Test
    public void whenDelete() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert("map", 100);
        assertThat(map.delete("map"), is(true));
        assertNull(map.get("map"));
    }

    @Test
    public void whenDeleteFromBucketWithSomeElm() {
        SimpleMap<Integer, Integer> map = new SimpleMap<>(2);
        map.insert(1, 100);
        map.insert(2, 200);
        map.insert(3, 300);
        map.insert(4, 400);
        map.insert(5, 500);
        map.insert(6, 600);
        map.insert(7, 700);
        assertThat(map.delete(4), is(true));
        assertNull(map.get(4));
    }

    @Test
    public void whenDeleteIsAbsent() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert("map", 100);
        assertThat(map.delete("map2"), is(false));
    }

    @Test
    public void whenChangeSize() {
        SimpleMap<String, Integer> map = new SimpleMap<>(2);
        map.insert("1", 1);
        map.insert("2", 2);
        map.insert("3", 3);
        map.insert("4", 4);
        map.insert("5", 5);
        assertThat(map.get("1"), is(1));
        assertThat(map.get("2"), is(2));
        assertThat(map.get("3"), is(3));
        assertThat(map.get("4"), is(4));
        assertThat(map.get("5"), is(5));
    }

    @Test
    public void whenEmptyMapIteratorHasNext() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        Iterator<Node> iterator = map.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenIteratorNext() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert("map1", 100);
        map.insert("map2", 200);
        map.insert("map3", 300);
        Iterator<Node> iterator = map.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertEquals(iterator.next(), "map3");
        assertThat(iterator.hasNext(), is(true));
        assertEquals(iterator.next(), "map2");
        assertThat(iterator.hasNext(), is(true));
        assertEquals(iterator.next(), "map1");
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoSuchElementException() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        Iterator<Node> iterator = map.iterator();
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenConcurrentModificationException() {
        SimpleMap<String, Integer> map = new SimpleMap<>();
        map.insert("map1", 100);
        Iterator<Node> iterator = map.iterator();
        map.insert("map2", 200);
        iterator.hasNext();
    }
}