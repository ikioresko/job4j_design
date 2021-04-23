package ru.job4j.design.srp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Predicate;

/**
 * Класс генерирует полный отчет в формате JSON по всем полям
 * При создании экземпляра этого класса необходимо передать в конструктор объект
 * имплементирующий интерфейс Store, для получения списка сотрудников из БД
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class ReportJson implements Report {
    private final Store store;

    public ReportJson(Store store) {
        this.store = store;
    }

    /**
     * Сбор списка работников выполняется в классе Employees
     * Затем экземпляр класса со списком работников сериализуется в JSON
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в JSON формате
     */
    @Override
    public String generate(Predicate<Employee> filter) {
        Employees emp = new Employees(filter, store);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(emp);
    }
}
