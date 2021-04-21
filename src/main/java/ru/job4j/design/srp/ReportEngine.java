package ru.job4j.design.srp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует отчет в форматах: txt,  html, JSON, XML. При создании экземпляра класса,
 * для генерации отчета, в конструктор необходимо передать список с ключевыми словами,
 * по которым будет выводиться отчет, например для вывода отчета в котором должны присутствовать
 * только имя и зарплата сотрудника, список должен содержать: {"Name;", " ", "Salary;"}
 * для вывода в отчет всех данных, список должен содержать все поля, разделенные пробелами
 * {"Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;}
 * Для генерации html, JSON или XML отчета, в список, кроме полей сотрудников необходимо добавить
 * ключевое слово "html" или "JSON" или "XML" соответственно, например:
 * {"Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;, "html"}
 * Кроме того, при создании экземпляра класса, в конструктор необходимо передать компаратор
 * для сортировки отчета перед его выводом в консоль. Для редактирования поля salary перед
 * выводом отчета, в конструктор нужно передать положительный или отрицательный коэффициент
 * который в свою очередь изменит значение. Если нет необходимости редактировать значение
 * поля salary, можно воспользоваться перегруженым конструктором
 * ***
 * При выборе JSON или XML формата - отчет будет генерироваться по всем полям объекта Employee
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class ReportEngine implements Report {
    private final Comparator<Employee> compare;
    private StringBuilder header;
    private boolean name;
    private boolean hired;
    private boolean fired;
    private boolean salary;
    private final Store store;
    private final double salaryFactor;
    private final String ln = System.lineSeparator();

    public ReportEngine(Store store, List<String> keywordsColumn,
                        Comparator<Employee> compare, double salaryFactor) {
        this.store = store;
        getHeader(keywordsColumn);
        getFlag(keywordsColumn);
        this.compare = compare;
        this.salaryFactor = salaryFactor;
    }

    public ReportEngine(Store store, List<String> keywordsColumn, Comparator<Employee> compare) {
        this.store = store;
        getHeader(keywordsColumn);
        getFlag(keywordsColumn);
        this.compare = compare;
        this.salaryFactor = 0;
    }

    /**
     * Метод собирает заголовок из листа в котором содержатся ключи используемых данных
     * Заголовок генерируется только для TXT отчета, в остальных случаях, перед генерацией
     * отчета заголовок будет очищен
     *
     * @param list Список с ключами в виде {"Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;}
     */
    public void getHeader(List<String> list) {
        header = new StringBuilder();
        if (list.contains("html")) {
            header.append("html");
        } else if (list.contains("json")) {
            header.append("json");
        } else if (list.contains("xml")) {
            header.append("xml");
        } else {
            for (String s : list) {
                header.append(s);
            }
            header.append(ln);
        }
    }

    /**
     * Применяется только для TXT и HTML отчетов
     * Активирует флаги используемых данных, для генерации отчета
     * Пример: Если в листе содержится ключ "Name;", значит этот столюец
     * будет включен в выводе отчета. Если ключ "Name;" отсутствует в листе, значит
     * и в отчете этот столбец с данными будет отсутствовать
     *
     * @param list Список с ключами в виде ("Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;)
     */
    public void getFlag(List<String> list) {
        name = list.contains("Name;");
        hired = list.contains("Hired;");
        fired = list.contains("Fired;");
        salary = list.contains("Salary;");
    }

    /**
     * Производит сортировку по имеющемуся компаратару,
     * который был отправлен в конструктор при создании класса
     *
     * @param empList Список работников
     */
    public void doSort(List<Employee> empList) {
        empList.sort(compare);
    }

    /**
     * Изменяет значение зарплаты в процентном соотношении
     * Пример: чтобы добавить 15% к текущей з/п нужно передать следующее значение = 0.15,
     * чтобы убавить 15% от текущей з/п нужно передать значение = -0.15
     *
     * @param salary Значение зарплаты
     * @return Измененное значение з/п
     */
    public double salaryRefactor(double salary) {
        return salary + salary * salaryFactor;
    }

    /**
     * Собирает список сотрудников по предикату, вызывает метод для сортировки списка
     * затем вызывает метод для выбора типа генерации отчета
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в HTML, JSON, XML или текстовом формате
     * @throws Exception JAXBContext
     */
    @Override
    public String generate(Predicate<Employee> filter) throws Exception {
        List<Employee> empList = store.findBy(filter);
        doSort(empList);
        return reportSwitcher(empList);
    }

    /**
     * Определяет тип отчета по заголовку
     * Подробности о генерации заголовка в методе @getHeader
     *
     * @param empList Лист работников
     * @return Отчет в HTML, JSON, XML или текстовом формате
     * @throws Exception JAXBContext
     */
    public String reportSwitcher(List<Employee> empList) throws Exception {
        String rsl;
        String head = header.toString();
        if (head.contains("html")) {
            rsl = genHtml(empList);
        } else if (head.contains("json")) {
            rsl = genJson(empList);
        } else if (head.contains("xml")) {
            rsl = genXml(empList);
        } else {
            rsl = genSimple(empList);
        }
        return rsl;
    }

    /**
     * Генерирует отчет по отмеченым флагам столбцов
     * Подробности об активации флага в методе @getFlag
     *
     * @param empList Лист работников
     * @return Отчет в текстовом формате
     */
    public String genSimple(List<Employee> empList) {
        for (Employee employee : empList) {
            if (name) {
                header.append(employee.getName()).append(";");
            }
            if (hired) {
                header.append(employee.getHired().getTime()).append(";");
            }
            if (fired) {
                header.append(employee.getFired().getTime()).append(";");
            }
            if (salary) {
                double sal = salaryRefactor(employee.getSalary());
                header.append(sal).append(";");
            }
            header.append(ln);
        }
        return header.toString();
    }

    /**
     * Генерирует отчет по отмеченым флагам столбцов
     * Подробности об активации флага в методе @getFlag
     *
     * @param empList Лист работников
     * @return Отчет в html формате
     */
    public String genHtml(List<Employee> empList) {
        header = new StringBuilder();
        header.append("<!DOCTYPE HTML>").append(ln)
                .append("<html>").append(ln)
                .append("<head>").append(ln)
                .append("<meta charset=\"utf-8\">").append(ln)
                .append("</head>").append(ln)
                .append("<body>").append(ln)
                .append("<table border=\"1\">").append(ln)
                .append("<caption>html отчет по работникам</caption>").append(ln)
                .append("<tr>").append(ln);
        if (name) {
            header.append("<th>Name</th>").append(ln);
        }
        if (hired) {
            header.append("<th>Hired</th>").append(ln);
        }
        if (fired) {
            header.append("<th>Fired</th>").append(ln);
        }
        if (salary) {
            header.append("<th>Salary</th>").append(ln);
        }
        header.append("</tr>").append(ln);
        for (Employee employee : empList) {
            header.append("<tr>");
            if (name) {
                header.append("<td>").append(employee.getName()).append("</td>");
            }
            if (hired) {
                header.append("<td>").append(employee.getHired().getTime()).append("</td>");
            }
            if (fired) {
                header.append("<td>").append(employee.getFired().getTime()).append("</td>");
            }
            if (salary) {
                double sal = salaryRefactor(employee.getSalary());
                header.append("<td>").append(sal).append("</td>");
            }
            header.append("</tr>").append(ln);
        }
        header.append("</table>").append(ln)
                .append("</body>").append(ln).append("</html>");
        return header.toString();
    }

    /**
     * Генерирует полный отчет в формате JSON по всем полям
     *
     * @param empList Лист работников
     * @return Отчет в JSON формате
     */
    public String genJson(List<Employee> empList) {
        header = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        for (Employee emp : empList) {
            emp.setSalary(salaryRefactor(emp.getSalary()));
            header.append(gson.toJson(emp));
        }
        return header.toString();
    }

    /**
     * Генерирует полный отчет в формате XML по всем полям
     *
     * @param empList Лист работников
     * @return Отчет в XML формате
     * @throws Exception JAXBContext
     */
    public String genXml(List<Employee> empList) throws Exception {
        header = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(Employee.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (StringWriter writer = new StringWriter()) {
            for (Employee emp : empList) {
                emp.setSalary(salaryRefactor(emp.getSalary()));
                marshaller.marshal(emp, writer);
                header.append(writer.getBuffer().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return header.toString();
    }
}