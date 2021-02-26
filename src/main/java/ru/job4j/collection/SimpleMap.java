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
    private Node<K, V> head;
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
            for (head = node[index]; head != null; head = head.getNext()) {
                if (checkKey(head.getKey(), key)) {
                    head.setValue(value);
                    modCount++;
                    return true;
                }
                if (!(checkKey(head.getKey(), key)) && head.getNext() == null) {
                    head.setNext(hashKey, key, value, null);
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
        for (Node nodes : node) {
            while (nodes != null) {
                newMap.insert((K) nodes.getKey(), (V) nodes.getValue());
                nodes = nodes.getNext();
            }
        }
        maxCapacity = (int) (newMap.initialCapacity * loadFactor);
        node = newMap.node;
    }

    public V get(K key) {
        V value = null;
        if (node[getIndex(key)] != null) {
            head = node[getIndex(key)];
            while (head != null) {
                if (checkKey(head.getKey(), key)) {
                    value = head.getValue();
                    break;
                }
                head = head.getNext();
            }
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
                head = node[index];
                while (head.getNext() != null) {
                    if (checkKey(head.getNext().getKey(), key)) {
                        head.setNext(head.getNext().getNext().getHash(),
                                head.getNext().getNext().getKey(),
                                head.getNext().getNext().getValue(),
                                head.getNext().getNext().getNext());
                        rslt = true;
                        addCount--;
                        modCount++;
                    }
                    head = head.getNext();
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

            @Override
            public boolean hasNext() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return addCount > iteratorSize;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int innerCounter = 0;
                Node<K, V> result = null;
                Node<K, V>[] array = node;
                for (Node nodes : array) {
                    while (nodes != null) {
                        if (iteratorSize < ++innerCounter) {
                            iteratorSize++;
                            return nodes.getKey();
                        }
                            nodes = nodes.getNext();
                    }
                }
                return result.getKey();
            }
        };
    }
}