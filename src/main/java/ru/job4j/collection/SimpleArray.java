package ru.job4j.collection;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] container;
    private int index = 0;
    private int modCount = 0;

    public SimpleArray() {
        container = new Object[10];
    }

    public SimpleArray(int length) {
        container = new Object[length];
    }

    public T get(int index) {
        return (T) container[Objects.checkIndex(index, this.index)];
    }

    public void add(T model) {
        if (index == container.length) {
            Object[] temp = Arrays.copyOf(container, index + index / 3);
            System.arraycopy(container, 0, temp, 0, index);
            container = temp;
            container[Objects.checkIndex(index++, index)] = model;
        } else {
            container[Objects.checkIndex(index++, index)] = model;
        }
        modCount++;
    }

    public void remove(T model) {
        int i = -1;
        for (Object o : container) {
            i++;
            if (o.equals(model)) {
                remove(i);
                break;
            }
        }
    }

    public void remove(int index) {
        if (index == Objects.checkIndex(index, this.index)) {
            System.arraycopy(container, index + 1, container, index,
                    container.length - (index + 1));
            container[container.length - 1] = null;
            this.index--;
            modCount++;
        }
    }

    public void set(int index, T model) {
        container[Objects.checkIndex(index, this.index)] = model;
        modCount++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int expectedModCount = modCount;
            private int size = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index > size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) container[size++];
            }
        };
    }

    public static void main(String[] args) {
        SimpleArray<String> s = new SimpleArray<>();
        s.add("HI");
        System.out.println(s.get(0));
    }
}