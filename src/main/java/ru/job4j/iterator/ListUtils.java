package ru.job4j.iterator;

import java.util.*;
import java.util.function.Predicate;

public class ListUtils {

    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> i = list.listIterator();
        while (i.hasNext()) {
            if (i.nextIndex() == index) {
                i.add(value);
                break;
            }
            i.next();
        }
    }

    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> i = list.listIterator();
        while (i.hasNext()) {
            if (i.nextIndex() == index) {
                i.next();
                i.add(value);
                break;
            }
            i.next();
        }
    }

    public static <T> List<T> removeIf(List<T> list, Predicate<T> filter) {
        ListIterator<T> i = list.listIterator();
        while (i.hasNext()) {
            if (filter.test(i.next())) {
                i.remove();
            }
        }
        return list;
    }

    public static <T> List<T> replaceIf(List<T> list, Predicate<T> filter, T value) {
        ListIterator<T> i = list.listIterator();
        while (i.hasNext()) {
            if (filter.test(i.next())) {
                i.set(value);
            }
        }
        return list;
    }

    public static <T> List<T> removeAll(List<T> list, List<T> elements) {
        int switcher = 0;
        ListIterator<T> i = list.listIterator();
        for (T element : elements) {
            while (i.hasNext() || i.hasPrevious()) {
                if (switcher == 0 && element.equals(i.next())) {
                    i.remove();
                }
                if (switcher == 0 && !i.hasNext()) {
                    switcher = 1;
                    break;
                }
                if (switcher == 1 && element.equals(i.previous())) {
                    i.remove();
                }
                if (switcher == 1 && !i.hasPrevious()) {
                    switcher = 0;
                    break;
                }
            }
        }
        return list;
    }
}