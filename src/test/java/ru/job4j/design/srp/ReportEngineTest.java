package ru.job4j.design.srp;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.*;

public class ReportEngineTest {
    private final String ln = System.lineSeparator();

    private static boolean test(Employee em) {
        return true;
    }

    @Test
    public void whenOldGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        List<String> keywordsColumn = List.of(
                "Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;");
        Comparator<Employee> comp =
                Comparator.comparingDouble(Employee::getSalary);
        Report engine = new ReportEngine(store, keywordsColumn, comp);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(ln)
                .append(worker.getName()).append(";")
                .append(worker.getHired().getTime()).append(";")
                .append(worker.getFired().getTime()).append(";")
                .append(worker.getSalary()).append(";")
                .append(ln);
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenHtmlGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        List<String> keywordsColumn = List.of(
                "Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;", "html");
        Comparator<Employee> comp =
                Comparator.comparingDouble(Employee::getSalary);
        Report engine = new ReportEngine(store, keywordsColumn, comp);
        StringBuilder expect = new StringBuilder()
                .append("<!DOCTYPE HTML>").append(ln)
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
                .append("</tr>").append(ln)
                .append("<tr><td>Ivan</td>")
                .append("<td>").append(worker.getHired().getTime()).append("</td>")
                .append("<td>").append(worker.getFired().getTime()).append("</td>")
                .append("<td>100.0</td></tr>").append(ln)
                .append("</table>").append(ln)
                .append("</body>").append(ln)
                .append("</html>");
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenAccDepGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        List<String> keywordsColumn = List.of(
                "Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;");
        Comparator<Employee> comp =
                Comparator.comparing(Employee::getName);
        Report engine = new ReportEngine(store, keywordsColumn, comp, 0.15);
        double salaryExpected = worker.getSalary();
        salaryExpected += salaryExpected * 0.15;
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(ln)
                .append(worker.getName()).append(";")
                .append(worker.getHired().getTime()).append(";")
                .append(worker.getFired().getTime()).append(";")
                .append(salaryExpected).append(";")
                .append(ln);
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }

    @Test
    public void whenHRGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        Employee worker2 = new Employee("Sergey", now, now, 200);
        store.add(worker);
        store.add(worker2);
        List<String> keywordsColumn = List.of(
                "Name;", " ", "Salary;");
        Comparator<Employee> comp =
                Comparator.comparingDouble(Employee::getSalary);
        Report engine = new ReportEngine(store, keywordsColumn, comp.reversed());
        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;")
                .append(ln)
                .append(worker2.getName()).append(";")
                .append(worker2.getSalary()).append(";")
                .append(ln)
                .append(worker.getName()).append(";")
                .append(worker.getSalary()).append(";")
                .append(ln);
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }
}