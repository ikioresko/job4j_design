package ru.job4j.design.srp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует полный отчет в формате JSON по всем полям
 * При создании экземпляра этого класса необходимо передать в конструктор объект
 * имплементирующий интерфейс Store, для получения списка сотрудников из БД
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class ReportJson implements Report {
    private final Store store;

    public ReportJson(Store store) {
        this.store = store;
    }

    /**
     * Собирает список сотрудников по предикату через интерфейс Store
     * Генерирует полный отчет в формате JSON по всем полям
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в JSON формате
     */
    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        List<Employee> empList = store.findBy(filter);
        Gson gson = new GsonBuilder().create();
        for (Employee emp : empList) {
            text.append(gson.toJson(emp));
        }
        return text.toString();
    }
}
