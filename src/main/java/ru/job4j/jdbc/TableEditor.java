package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.*;
import java.util.*;

/**
 * Класс содержит метод main.
 * Класс совместно с {@link Config} производит подключение к базе данных,
 * с помощью своих методов создает, редактирует и удаляет таблицы.
 * При создании новой таблицы генерирует столбец "id" с типом "serial" и
 * столбец "name" с типом "varchar(255)".
 *
 * @author Igor Kioresko
 * @version 0.1
 */
public class TableEditor implements AutoCloseable {
    private Connection connection;
    private final Properties properties;

    public TableEditor(Properties properties) throws Exception {
        this.properties = properties;
        initConnection();
    }

    /**
     * Метод вызывает getConfig() который генерирует параметры подключения,
     * затем на основании этих параметров создается подключение;
     *
     * @throws Exception Class.forName() и DriverManager.getConnection()
     *                   могут генерировать исключения.
     */
    private void initConnection() throws Exception {
        getConfig();
        Class.forName(properties.getProperty("driver"));
        connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
    }

    /**
     * Метод создает экземпляр класса {@link Config} и передает ему путь c настройками,
     * в виде строки из properties. Затем вызывает cfg.load() для генерации параметров
     * подключения, а после размещает созданные параметры в properties для создания
     * подключения в методе initConnection().
     */
    private void getConfig() {
        Config cfg = new Config(properties.getProperty("path"));
        cfg.load();
        properties.put("driver", cfg.value("driver_class"));
        properties.put("url", cfg.value("url"));
        properties.put("username", cfg.value("username"));
        properties.put("password", cfg.value("password"));
    }

    /**
     * Метод создает указанную таблицу;
     *
     * @param tableName Имя создаваемой таблицы;
     * @throws SQLException createStatement(), execute(), getScheme(),
     *                      могут генерировать исключения SQLException.
     */
    public void createTable(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "create table if not exists " + tableName + " (%s, %s);",
                    "id serial primary key",
                    "name varchar(255)"
            );
            statement.execute(sql);
            System.out.println(getScheme(tableName));
        }
    }

    /**
     * Метод удаляет указанную таблицу;
     *
     * @param tableName Имя удаляемой таблицы;
     * @throws SQLException createStatement(), execute(), getScheme(),
     *                      могут генерировать исключения SQLException.
     */
    public void dropTable(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "drop table if exists %s;",
                    tableName
            );
            statement.execute(sql);
            System.out.println(getScheme(tableName));
        }
    }

    /**
     * Метод создает столбец в указанной таблице;
     *
     * @param tableName  Имя таблицы в которой производятся изменения;
     * @param columnName Имя создаваемого столбца;
     * @param type       Тип данных столбца;
     * @throws SQLException createStatement(), execute(), getScheme(),
     *                      могут генерировать исключения SQLException.
     */
    public void addColumn(String tableName, String columnName, String type) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "alter table if exists " + tableName + "%s;",
                    " add column " + columnName + " " + type
            );
            statement.execute(sql);
            System.out.println(getScheme(tableName));
        }
    }

    /**
     * Метод удаляет столбец в указанной таблице;
     *
     * @param tableName  Имя таблицы в которой производятся изменения;
     * @param columnName Имя удаляемого столбца;
     * @throws SQLException createStatement(), execute(), getScheme(),
     *                      могут генерировать исключения SQLException.
     */
    public void dropColumn(String tableName, String columnName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "alter table if exists " + tableName + "%s;",
                    " drop column if exists " + columnName
            );
            statement.execute(sql);
            System.out.println(getScheme(tableName));
        }
    }

    /**
     * Метод переименовывает столбец в указанной таблице;
     *
     * @param tableName     Имя таблицы в которой производятся изменения;
     * @param columnName    Имя столбца, который нужно переименовать;
     * @param newColumnName Новое имя столбца;
     * @throws SQLException createStatement(), execute(), getScheme(),
     *                      могут генерировать исключения SQLException.
     */
    public void renameColumn(String tableName, String columnName, String newColumnName)
            throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "alter table if exists " + tableName + "%s;",
                    " rename " + columnName + " to " + newColumnName
            );
            statement.execute(sql);
            System.out.println(getScheme(tableName));
        }
    }

    /**
     * @param tableName Имя таблицы;
     * @return Содержимое таблицы в виде Строки;
     * @throws SQLException Методы getMetaData(), getColumns(), getString(), next()
     *                      могут генерировать исключения SQLException.
     */
    public String getScheme(String tableName) throws SQLException {
        StringBuilder scheme = new StringBuilder();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
            scheme.append(String.format("%-15s %-15s%n", "column", "type"));
            while (columns.next()) {
                scheme.append(String.format("%-15s %-15s%n",
                        columns.getString("COLUMN_NAME"),
                        columns.getString("TYPE_NAME")));
            }
        }
        return scheme.toString();
    }

    /**
     * Закрывает соединение с БД;
     *
     * @throws Exception close() Может генерировать исключение.
     */
    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("path", "app.properties");
        TableEditor tableEditor = new TableEditor(properties);
        tableEditor.createTable("test");
        tableEditor.renameColumn("test", "name", "first_name");
        tableEditor.addColumn("test", "last_name", "varchar(80)");
        tableEditor.dropColumn("test", "first_name");
        tableEditor.dropTable("test");
        tableEditor.close();
    }
}