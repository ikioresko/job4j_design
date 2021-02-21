package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleSet<E> implements Iterable<E> {
    private SimpleArray<E> simpleArrays;
    private int modCount = 0;

    public SimpleSet() {
        this.simpleArrays = new SimpleArray<>(16);
    }

    public SimpleSet(int length) {
        this.simpleArrays = new SimpleArray<>(length);
    }

    public boolean add(E e) {
        for (E element : simpleArrays) {
            if (Objects.equals(element, e)) {
                return false;
            }
        }
        simpleArrays.add(e);
        modCount++;
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int size = 0;
            private int checkModCount = modCount;

            @Override
            public boolean hasNext() {
                if (checkModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return simpleArrays.size() > size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return simpleArrays.get(size++);
            }
        };
    }
}
