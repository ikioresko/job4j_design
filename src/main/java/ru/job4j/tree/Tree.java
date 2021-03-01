package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

class Tree<E> implements SimpleTree<E> {
    private final Node<E> root;

    Tree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean rsl = false;
        Optional<Node<E>> parentNode = findBy(parent);
        if (parentNode.isPresent() && !(findBy(child).isPresent())) {
            Node<E> pn = parentNode.get();
            pn.setChildren(child);
            rsl = true;
        }
        return rsl;
    }

    public boolean isBinary() {
        Predicate predicate = element -> element.equals(3);
        return !findByPredicate(null, predicate).isPresent();
    }

    private Optional<Node<E>> findByPredicate(Predicate<E> condition, Predicate bin) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (bin == null) {
                if (condition.test(el.getValue())) {
                    rsl = Optional.of(el);
                    break;
                }
            } else {
                if (bin.test(el.getChildren().size())) {
                    rsl = Optional.of(el);
                    break;
                }
            }
            data.addAll(el.getChildren());
        }
        return rsl;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Predicate<E> predicate = element -> Objects.equals(element, value);
        return findByPredicate(predicate, null);
    }
}

