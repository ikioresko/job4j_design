package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        while (row < data.length) {
            if (data[row].length == column) {
                column = 0;
                row++;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (data[row].length - 1 >= column) {
            return data[row][column++];
        }
        return data[row++][column++];
    }
}