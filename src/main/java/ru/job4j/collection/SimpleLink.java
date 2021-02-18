package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLink<E> implements Iterable<E> {
    private int modCount = 0;
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    public void add(E value) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(value, null, l);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    public E get(int index) {
        int pointer = 0;
        Node<E> current = first;
        if (index == Objects.checkIndex(index, size)) {
            while (pointer++ != index) {
                current = current.next;
            }
        }
        return current.item;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> previous;

        Node(E element, Node<E> next, Node<E> previous) {
            item = element;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            private Node<E> current = first;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = current.item;
                current = current.next;
                return value;
            }
        };
    }
}
