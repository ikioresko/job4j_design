package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleMap<K, V> implements Iterable<Node> {
    private final int initialCapacity;
    private final float loadFactor;
    private int maxCapacity;
    private Node<K, V>[] node;
    private int addCount = 0;
    private int modCount = 0;

    public SimpleMap() {
        loadFactor = 0.75F;
        initialCapacity = 16;
        this.node = new Node[initialCapacity];
        maxCapacity = (int) (initialCapacity * loadFactor);
    }

    public SimpleMap(int initialCapacity) {
        loadFactor = 0.75F;
        this.node = new Node[initialCapacity];
        this.initialCapacity = initialCapacity;
        maxCapacity = (int) (initialCapacity * loadFactor);
    }

    public SimpleMap(int initialCapacity, float loadFactor) {
        this.node = new Node[initialCapacity];
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        maxCapacity = (int) (initialCapacity * loadFactor);
    }

    private boolean checkKey(K nodeKey, K newKey) {
        return Objects.hashCode(nodeKey) == (Objects.hashCode(newKey))
                && Objects.equals(nodeKey, newKey);
    }

    private int getIndex(K key) {
        int h = Objects.hash(key);
        return (node.length - 1) & (h ^ (h >>> 16));
    }

    public boolean insert(K key, V value) {
        if (addCount >= maxCapacity) {
            reSize();
        }
        int hashKey = Objects.hash(key);
        int index = getIndex(key);
        if (node[index] == null) {
            node[index] = new Node<>(hashKey, key, value, null);
        } else {
            for (Node<K, V> head = node[index]; head != null; head = head.getNext()) {
                if (checkKey(head.getKey(), key)) {
                    head.setValue(value);
                    modCount++;
                    return true;
                }
                if (!(checkKey(head.getKey(), key)) && head.getNext() == null) {
                    head.setNext(new Node<>(hashKey, key, value, null));
                    break;
                }
            }
        }
        addCount++;
        modCount++;
        return true;
    }

    private void reSize() {
        SimpleMap<K, V> newMap = new SimpleMap<>(initialCapacity + initialCapacity * 2);
        Node<K, V>[] array = node;
        for (Node nodes : array) {
            for (Node<K, V> bcktNode = nodes; bcktNode != null; bcktNode = bcktNode.getNext()) {
                newMap.insert(bcktNode.getKey(), bcktNode.getValue());
            }
        }
        maxCapacity = (int) (newMap.initialCapacity * loadFactor);
        node = newMap.node;
    }

    public V get(K key) {
        V value = null;
        int index = getIndex(key);
        Node<K, V> head = node[index];
        while (head != null) {
            if (checkKey(head.getKey(), key)) {
                value = head.getValue();
                break;
            }
            head = head.getNext();
        }
        return value;
    }

    public boolean delete(K key) {
        boolean rslt = false;
        int index = getIndex(key);
        if (node[index] != null) {
            if (checkKey(node[index].getKey(), key)) {
                node[index] = node[index].getNext();
                rslt = true;
                addCount--;
                modCount++;
            } else {
                Node<K, V> prev = null;
                for (Node<K, V> head = node[index]; head != null; head = head.getNext()) {
                    if (prev != null && checkKey(prev.getNext().getKey(), key)) {
                        prev.setNext(head.getNext());
                        rslt = true;
                        addCount--;
                        modCount++;
                        break;
                    }
                    prev = head;
                }
            }
        }
        return rslt;
    }

    @Override
    public Iterator<Node> iterator() {
        return new Iterator() {
            private final int expModCount = modCount;
            private int iteratorSize = 0;
            private int bucketIndex = -1;
            private Node<K, V> currentNode;

            @Override
            public boolean hasNext() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return addCount > iteratorSize;
            }

            @Override
            public Node<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                if (currentNode == null) {
                    while (currentNode == null) {
                        currentNode = node[++bucketIndex];
                    }
                } else {
                    currentNode = currentNode.getNext();
                    if (currentNode == null) {
                        while (currentNode == null) {
                            currentNode = node[++bucketIndex];
                        }
                    }
                }
                iteratorSize++;
                return currentNode;
            }
        };
    }
}