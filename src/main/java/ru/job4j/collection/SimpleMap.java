package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleMap<K, V> implements Iterable<Node> {
    private Node<K, V>[] node;
    private int addCount = 0;
    private int modCount = 0;
    private int length = 0;

    public SimpleMap() {
        this.node = new Node[16];
    }

    public SimpleMap(int length) {
        this.node = new Node[length];
    }

    private int getIndex(K key) {
        int h;
        int hs;
        if (key == null) {
            hs = 0;
        } else {
            h = key.hashCode();
            h = h ^ (h >>> 16);
            hs = (node.length - 1) & (h);
        }
        return hs;
    }

    public boolean insert(K key, V value) {
        if (node.length == addCount) {
            reSize();
        }
        int hashKey = Objects.hash(key);
        int index = getIndex(key);
        if (node[index] == null) {
            node[index] = new Node<>(hashKey, key, value, null);
        } else {
            while (node[index] != null) {
                if (checkKey(node[index].getKey(), key)) {
                    node[index].setValue(value);
                    break;
                }
                if (node[index].getNext() == null) {
                    node[index].setNext(hashKey, key, value, null);
                    break;
                } else {
                    node[index] = node[index].getNext();
                    break;
                }
            }
        }
        addCount++;
        modCount++;
        return true;
    }

    public void reSize() {
        SimpleMap<K, V> newMap = new SimpleMap<>(node.length + node.length / 2 + 1);
        for (Node nodes : node) {
            while (nodes != null) {
                newMap.insert((K) nodes.getKey(), (V) nodes.getValue());
                nodes = nodes.getNext();
            }
        }
        node = newMap.node;
    }

    public V get(K key) {
        V value = null;
        int index = getIndex(key);
        while (node[index] != null) {
            if (checkKey(node[index].getKey(), key)) {
                value = node[index].getValue();
                break;
            } else {
                node[index] = node[index].getNext();
            }
        }
        return value;
    }

    public boolean delete(K key) {
        int index = getIndex(key);
        while (node[index] != null) {
            if (checkKey(node[index].getKey(), key)) {
                node[index] = null;
                return true;
            } else {
                node[index] = node[index].getNext();
            }
        }
        return false;
    }

    private boolean checkKey(K nodeKey, K newKey) {
        return Objects.hashCode(nodeKey) == (Objects.hashCode(newKey))
                && Objects.equals(nodeKey, newKey);
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
                for (Node nodes : node) {
                    while (nodes != null) {
                        if (nodes == null) {
                            nodes = nodes.getNext();
                        }
                        if (iteratorSize < ++innerCounter) {
                            result = nodes;
                            iteratorSize++;
                            return result.getKey();
                        } else {
                            nodes = nodes.getNext();
                        }
                    }
                }
                return result.getKey();
            }
        };
    }
}