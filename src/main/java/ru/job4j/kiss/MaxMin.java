package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;

public class MaxMin {
    /**
     * @param value      List элементов для сравнения
     * @param comparator Comparator.naturalOrder() для поиска максимального значения
     *                   Comparator.reverseOrder() для поиска минимального значения
     * @param <T>        Тип объекта
     * @return Наибольший или наименьший элемент
     */
    public <T> T getResult(List<T> value, Comparator<T> comparator) {
        T first = null;
        if (value.size() >= 1) {
            first = value.get(0);
            for (T second : value) {
                if (comparator.compare(first, second) <= 0) {
                    first = second;
                }
            }
        }
        return first;
    }
}