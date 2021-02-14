package ru.job4j.array;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] tArray;
    private int index = 0;

    public SimpleArray(int length) {
        tArray = new Object[length];
    }

    public void add(T model) {
        tArray[Objects.checkIndex(index++, index)] = model;
    }

    public void set(int index, T model) {
        tArray[Objects.checkIndex(index, this.index)] = model;
    }

    public T get(int index) {
        return (T) tArray[Objects.checkIndex(index, this.index)];
    }

    public void remove(int index) {
        if (index == Objects.checkIndex(index, this.index)) {
            System.arraycopy(tArray,
                    index + 1,
                    tArray,
                    index,
                    tArray.length - (index + 1));
            tArray[tArray.length - 1] = null;
            this.index--;
        }
    }

    @Override
    public String toString() {
        return "SimpleArray{"
                + "tArray=" + Arrays.toString(tArray)
                + '}';
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < tArray.length && index > 0;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) tArray[cursor++];
            }
        };
    }
}
