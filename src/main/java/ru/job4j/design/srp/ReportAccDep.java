package ru.job4j.design.srp;

import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует полный отчет в формате TXT по всем полям, с возможностью изменить значение
 * поля Salary перед выводом отчета. При создании экземпляра этого класса необходимо передать в
 * конструктор объект имплементирующий интерфейс Store. Для изменения в отчете поля Salary,
 * в конструктор так же необходимо передать значение SalaryFactor, например:
 * чтобы добавить 15% к текущей з/п нужно передать следующее значение = 0.15,
 * чтобы убавить 15% от текущей з/п нужно передать значение = -0.15
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class ReportAccDep implements Report {
    private final Store store;
    private final double salaryFactor;

    public ReportAccDep(Store store, double salaryFactor) {
        this.store = store;
        this.salaryFactor = salaryFactor;
    }

    public ReportAccDep(Store store) {
        this.store = store;
        this.salaryFactor = 0;
    }

    /**
     * Изменяет значение зарплаты в процентном соотношении
     *
     * @param salary Текущее значение зарплаты
     * @return Измененное значение з/п
     */
    public double salaryRefactor(double salary) {
        return salary + salary * salaryFactor;
    }

    /**
     * Собирает список сотрудников по предикату через интерфейс Store
     * Генерирует полный отчет в формате TXT по всем полям с возможностью изменения поля Salary
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в TXT формате
     */
    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        List<Employee> empList = store.findBy(filter);
        text.append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator());
        for (Employee employee : empList) {
            text.append(employee.getName()).append(";")
                    .append(employee.getHired().getTime()).append(";")
                    .append(employee.getFired().getTime()).append(";");
            double sal = salaryRefactor(employee.getSalary());
            text.append(sal).append(";");
        }
        text.append(System.lineSeparator());
        return text.toString();
    }
}
