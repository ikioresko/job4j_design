package ru.job4j.io;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

public class AnalyzeTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private final String ls = System.lineSeparator();

    @Test
    public void whenTest() throws IOException {
        Analyze analyze = new Analyze();
        File source = folder.newFile("source.txt");
        File target = folder.newFile("target.txt");
        try (PrintWriter out = new PrintWriter(source)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("200 10:59:01");
            out.println("500 11:01:02");
            out.println("200 11:02:02");
        }
        analyze.unavailable(source, target);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(r -> rsl.append(r).append(ls));
            assertThat(rsl.toString(), is("10:57:01;10:59:01" + ls
                    + "11:01:02;11:02:02" + ls));
        }
    }
}