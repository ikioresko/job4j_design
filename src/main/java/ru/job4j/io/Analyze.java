package ru.job4j.io;

import java.io.*;

public class Analyze {
    public void unavailable(File source, File target) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line = reader.readLine();
            StringBuilder buffer = new StringBuilder();
            StringBuilder result = new StringBuilder();
            while (line != null) {
                if ((line.contains("400") || line.contains("500")) && buffer.length() == 0) {
                    buffer.append(line.substring(4));
                }
                if ((line.contains("200") || line.contains("300")) && buffer.length() > 0) {
                    buffer.append(";").append(line.substring(4));
                    result.append(buffer).append(System.lineSeparator());
                    buffer = new StringBuilder();
                }
                line = reader.readLine();
            }
            write(result, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(StringBuilder sb, File target) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(String.valueOf(target)))) {
            out.print(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}