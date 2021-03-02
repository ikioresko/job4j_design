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
        return findByPredicate(element -> element.getChildren().size() > 2).isEmpty();
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (condition.test(el)) {
                rsl = Optional.of(el);
                break;
            }
        data.addAll(el.getChildren());
    }
        return rsl;
}

    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate(el -> el.getValue().equals(value));
    }
}

