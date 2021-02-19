package ru.job4j.iterator;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);
        assertThat(Arrays.asList(1, 2, 3), Is.is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfter() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addAfter(input, 1, 4);
        assertThat(Arrays.asList(1, 3, 4), Is.is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddAfterWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addAfter(input, 3, 2);
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3, 3, 1, 3, 3, 1, 3));
        Predicate<Integer> predicate = i -> i.equals(3);
        ListUtils.removeIf(input, predicate);
        assertThat(Arrays.asList(1, 1, 1), Is.is(input));
    }

    @Test
    public void whenReplaceIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3, 3, 1, 1, 3, 3, 1, 3));
        Predicate<Integer> predicate = i -> i.equals(3);
        ListUtils.replaceIf(input, predicate, 0);
        assertThat(Arrays.asList(1, 0, 0, 1, 1, 0, 0, 1, 0), Is.is(input));
    }

    @Test
    public void whenReplaceIfNotFind() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3, 3, 1, 1, 3, 3, 1, 3));
        Predicate<Integer> predicate = i -> i.equals(45);
        ListUtils.replaceIf(input, predicate, 9);
        assertThat(Arrays.asList(1, 3, 3, 1, 1, 3, 3, 1, 3), Is.is(input));
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3, 3, 1, 1, 2, 8, 12, 1));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 3, 2));
        ListUtils.removeAll(input, list2);
        assertThat(Arrays.asList(8, 12), Is.is(input));
    }

}
