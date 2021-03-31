package ru.job4j.jdbc;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<String> keys = read();
        if (keys.size() == 3) {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(
                    keys.get(0), keys.get(1), keys.get(2))) {
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println(metaData.getUserName());
                System.out.println(metaData.getURL());
            }
        }
    }

    private static List<String> read() {
        List<String> keys = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("app.properties"))) {
            String str = reader.readLine();
            while (str != null) {
                if (str.contains("url") || str.contains("username") || str.contains("password")) {
                    int start = str.indexOf('=') + 1;
                    String rsl = str.substring(start);
                    keys.add(rsl);
                }
                str = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keys;
    }
}