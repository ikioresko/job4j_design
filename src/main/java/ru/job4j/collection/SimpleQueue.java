package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private int size = 0;
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    public T poll() {
        T value = null;
        int outSize = 0;
        if (size == 0) {
            throw new NoSuchElementException();
        }
        while (outSize != size) {
            if (outSize < size - 1) {
                out.push(in.pop());
                outSize++;
            }
            if (outSize == size - 1) {
                value = in.pop();
                size = 0;
            }
            if (outSize > size) {
                in.push(out.pop());
                size++;
            }
        }
        return value;
    }

    public void push(T value) {
        in.push(value);
        size++;
    }
}
