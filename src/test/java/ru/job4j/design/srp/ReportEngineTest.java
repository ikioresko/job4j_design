package ru.job4j.design.srp;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportEngineTest {
    private final String ln = System.lineSeparator();

    private static boolean test(Employee em) {
        return true;
    }

    @Test
    public void whenOldGenerated() throws Exception {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        Report engine = new ReportEngine(store);
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
    public void whenHtmlGenerated() throws Exception {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        Report engine = new ReportHtml(store);
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
    public void whenAccDepGenerated() throws Exception {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        Report engine = new ReportAccDep(store, 0.15);
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
    public void whenHRGenerated() throws Exception {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        Employee worker2 = new Employee("Sergey", now, now, 200);
        store.add(worker);
        store.add(worker2);
        Report engine = new ReportHR(store);
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

    @Test
    public void whenJson() throws Exception {
        MemStore store = new MemStore();
        Gson gson = new GsonBuilder().create();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        Report engine = new ReportJson(store);
        StringBuilder expect = new StringBuilder()
                .append("{\"name\":\"Ivan\",\"hired\":")
                .append(gson.toJson(worker.getHired()))
                .append(",\"fired\":")
                .append(gson.toJson(worker.getFired()))
                .append(",\"salary\":100.0}");
        String actual = engine.generate(em -> true);
        Employee emp = gson.fromJson(actual, Employee.class);
        assertThat(actual, is(expect.toString()));
        assertThat(emp, is(worker));
    }

    @Test
    public void whenXml() throws Exception {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        LocalDateTime ldt = new Timestamp(date.getTime()).toLocalDateTime();
        String zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        Report engine = new ReportXml(store);
        StringBuilder expect = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n")
                .append("<employee>\n")
                .append("    <name>Ivan</name>\n")
                .append("    <hired>").append(zdt).append("</hired>\n")
                .append("    <fired>").append(zdt).append("</fired>\n")
                .append("    <salary>100.0</salary>\n")
                .append("</employee>\n");
        String actual = engine.generate(em -> true);
        JAXBContext context = JAXBContext.newInstance(Employee.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Employee deserialization;
        try (StringReader reader = new StringReader(actual)) {
            deserialization = (Employee) unmarshaller.unmarshal(reader);
        }
        assertThat(actual, is(expect.toString()));
        assertThat(deserialization, is(worker));
    }
}