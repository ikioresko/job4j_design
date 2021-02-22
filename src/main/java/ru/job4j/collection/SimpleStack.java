package ru.job4j.collection;

import java.util.Iterator;

public class SimpleStack<T> {
    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        return linked.deleteLast();
    }

    public void push(T value) {
        linked.add(value);
    }

    public boolean isEmpty() {
        Iterator<T> iterator = linked.iterator();
        return !iterator.hasNext();
    }
}