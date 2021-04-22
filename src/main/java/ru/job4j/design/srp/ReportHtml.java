package ru.job4j.design.srp;

import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует полный отчет в формате HTML по всем полям
 * При создании экземпляра этого класса необходимо передать в конструктор объект
 * имплементирующий интерфейс Store, для получения списка сотрудников из БД
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class ReportHtml implements Report {
    private final Store store;
    private final String ln = System.lineSeparator();

    public ReportHtml(Store store) {
        this.store = store;
    }

    /**
     * Собирает список сотрудников по предикату через интерфейс Store
     * Генерирует полный отчет в формате HTML по всем полям
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в html формате
     */
    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        List<Employee> empList = store.findBy(filter);
        text.append("<!DOCTYPE HTML>").append(ln)
                .append("<html>").append(ln)
                .append("<head>").append(ln)
                .append("<meta charset=\"utf-8\">").append(ln)
                .append("</head>").append(ln)
                .append("<body>").append(ln)
                .append("<table border=\"1\">").append(ln)
                .append("<caption>html отчет по работникам</caption>").append(ln)
                .append("<tr>").append(ln)
                .append("<th>Name</th>").append(ln)
                .append("<th>Hired</th>").append(ln)
                .append("<th>Fired</th>").append(ln)
                .append("<th>Salary</th>").append(ln)
                .append("</tr>").append(ln);
        for (Employee employee : empList) {
            text.append("<tr><td>").append(employee.getName()).append("</td>")
                    .append("<td>").append(employee.getHired().getTime()).append("</td>")
                    .append("<td>").append(employee.getFired().getTime()).append("</td>")
                    .append("<td>").append(employee.getSalary()).append("</td></tr>")
                    .append(ln);
        }
        text.append("</table>").append(ln)
                .append("</body>").append(ln).append("</html>");
        return text.toString();
    }
}