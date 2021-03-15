package ru.job4j.io;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AnalyzeTest {
    private final String ls = System.lineSeparator();

    @Test
    public void whenTwoDiapason() {
        String path = "unavailable.csv";
        String pathWithResult = "timeout.csv";
        Analyze analyze = new Analyze();
        analyze.unavailable(path, pathWithResult);
        String result = analyze.fileReader(pathWithResult);
        assertThat(result, is("10:57:01;10:59:01" + ls
                + "11:01:02;11:02:02" + ls));
    }
}