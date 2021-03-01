package ru.job4j.collection;

import java.util.Objects;

public class Node<K, V> {
    private int hash;
    private K key;
    private V value;
    private Node<K, V> next;

    public Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getHash() {
        return hash;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> node) {
        this.next = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node<?, ?> node = (Node<?, ?>) o;
        return hash == node.hash
                && Objects.equals(key, node.key)
                && Objects.equals(value, node.value)
                && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, key, value, next);

    }

    @Override
    public String toString() {
        return "Node{"
                + "hash=" + hash
                + ", key=" + key
                + ", value=" + value
                + ", next=" + next
                + '}';
    }
}