package ru.job4j.scanner;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CSVReaderTest {

    @Test
    public void whenNameAndAgeOnFilter() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        Path result = Files.createTempFile("result", ".txt");
        Files.writeString(tempFile, "name, age, birthDate, education, children"
                + System.lineSeparator()
                + "Tom, 18, 02.09.1999, Yes, Sandy"
                + System.lineSeparator()
                + "Manny, 18, 01.10.1990, No, Archy");
        CSVReader csv = new CSVReader();
        String path = tempFile.toAbsolutePath().toString();
        String[] args = {"-path=" + path,
                "-delimiter=,",
                "-out=" + result.toAbsolutePath(),
                "-filter=name,age"};
        csv.init(args);
        String expected = "name, age,"
                + System.lineSeparator()
                + "Tom, 18,"
                + System.lineSeparator()
                + "Manny, 18,";
        String resultFileContent = Files
                .lines(result, StandardCharsets.UTF_8)
                .collect(Collectors.joining(System.lineSeparator()));
        assertThat(expected, is(resultFileContent));
    }
}