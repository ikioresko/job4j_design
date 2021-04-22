package ru.job4j.design.srp;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует отчет отсортированный по полю Salary в порядке убывания
 * Сгенерированый отчет содержит поля Name и Salary
 * При создании экземпляра этого класса необходимо передать в конструктор объект
 * имплементирующий интерфейс Store, для получения списка сотрудников из БД
 *
 * @author Kioresko Igor
 * @version 0.3
 */
public class ReportHR implements Report {
    private final Store store;

    public ReportHR(Store store) {
        this.store = store;
    }

    /**
     * Производит сортировку списка сотрудников по полю Salary в порядке убывания
     *
     * @param empList Список работников
     */
    public void doSort(List<Employee> empList) {
        empList.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
    }

    /**
     * Собирает список сотрудников по предикату через интерфейс Store,
     * вызывает метод doSort() для сортировки списка
     * Генерирует отчет в формате TXT по полям Name и Salary
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в TXT формате
     */
    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;")
                .append(System.lineSeparator());
        List<Employee> empList = store.findBy(filter);
        doSort(empList);
        for (Employee employee : empList) {
            text.append(employee.getName()).append(";")
                    .append(employee.getSalary()).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}