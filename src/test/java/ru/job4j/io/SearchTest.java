package ru.job4j.io;

import org.junit.Test;

import java.io.IOException;

public class SearchTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenArgsIsMissing() throws IOException {
        String[] args = new String[2];
        Search.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenArgsHaveNullElements() throws IOException {
        String[] args = new String[0];
        Search.main(args);
    }
}