package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analyze {
    public void unavailable(File source, File target) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line = reader.readLine();
            String buffer = "";
            List<String> list = new ArrayList<>();
            while (line != null) {
                if ((line.contains("400") || line.contains("500")) && buffer.length() == 0) {
                    buffer = line.substring(4);
                }
                if ((line.contains("200") || line.contains("300")) && buffer.length() > 0) {
                    buffer += ";" + line.substring(4);
                    list.add(buffer);
                    buffer = "";
                }
                line = reader.readLine();
            }
            write(list, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(List<String> list, File target) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(String.valueOf(target)))) {
            for (String str : list) {
                out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}