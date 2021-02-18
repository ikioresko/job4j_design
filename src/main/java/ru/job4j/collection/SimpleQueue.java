package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private int size = 0;
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    public T poll() {
        T value;
        int inSize = size;
        if (size == 0) {
            throw new NoSuchElementException();
        }
        while (size != 0) {
            out.push(in.pop());
            size--;
        }
        value = out.pop();
        while (inSize - 1 > size) {
            in.push(out.pop());
            size++;
        }
        return value;
    }

    public void push(T value) {
        in.push(value);
        size++;
    }
}
