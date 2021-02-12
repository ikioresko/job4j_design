package ru.job4j.array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] tArray;
    private final int length;
    private int index = 0;
    private int cursor = 0;

    SimpleArray(int length) {
        tArray = new Object[length];
        this.length = length;
    }

    void add(T model) {
        try {
            tArray[Objects.checkIndex(index, length)] = model;
            index++;
        } catch (IndexOutOfBoundsException ioe) {
            System.out.println("Array is full");
        }
    }

    void set(int index, T model) {
        try {
            tArray[Objects.checkIndex(index, length)] = model;
        } catch (IndexOutOfBoundsException ioe) {
            System.out.println("Index = " + index + " doesn't exist");
        }
    }

    T get(int index) {
        try {
            return (T) tArray[Objects.checkIndex(index, length)];
        } catch (IndexOutOfBoundsException ioe) {
            System.out.println("Index = " + index + " doesn't exist");
        }
        return null;
    }

    public void remove(int index) {
        Object[] temp = tArray;
        try {
            System.arraycopy(tArray,
                    Objects.checkIndex(index, length) + 1,
                    temp,
                    index,
                    tArray.length - (index + 1));
            temp[temp.length - 1] = null;
            tArray = temp;
        } catch (IndexOutOfBoundsException ioe) {
            System.out.println("Index = " + index + " doesn't exist");
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
            @Override
            public boolean hasNext() {
                return cursor < tArray.length;
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
