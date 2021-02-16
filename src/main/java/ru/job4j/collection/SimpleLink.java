package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLink<E> implements Iterable<E> {
    private int indexNode = 0;
    private int modCount = 0;
    private transient int size = 0;
    private transient Node<E> first;
    private transient Node<E> last;

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
        newNode.index = indexNode++;
    }

    public E get(int index) {
        Node<E> temp = first;
        if (index == Objects.checkIndex(index, size)) {
            while (temp.index != index) {
                temp = temp.next;
            }
        }
        return temp.item;
    }

    private static class Node<E> {
        private int index;
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
            private int size = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return indexNode > size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(size++);
            }
        };
    }
}
