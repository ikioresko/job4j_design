package ru.job4j.design.srp;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует отчет в двух форматах: txt и html. При создании экземпляра класса,
 * для генерации отчета, в конструктор необходимо передать список с ключевыми словами,
 * по которым будет выводиться отчет, например для вывода отчета в котором должны присутствовать
 * только имя и зарплата сотрудника, список должен содержать: {"Name;", " ", "Salary;"}
 * для вывода в отчет всех данных, список должен содержать все поля, разделенные пробелами
 * {"Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;}
 * Для генерации html отчета, в список, кроме полей сотрудников необходимо добавить
 * ключевое слово "html" например {"Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;, "html"}
 * Кроме того, при создании экземпляра класса, в конструктор необходимо передать компаратор
 * для сортировки отчета перед его выводом в консоль. Для редактирования поля salary перед
 * выводом отчета, в конструктор нужно передать положительный или отрицательный коэффициент
 * который в свою очередь изменит значение. Если нет необходимости редактировать значение
 * поля salary, можно воспользоваться перегруженым конструктором
 * List<String> keywordsColumn с ключом "html"
 *
 * @author Kioresko Igor
 * @version 0.1
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
     * Метод собирает заголовок из листа в котором содержатся ключи используемых данных.
     * Есл в списке содержится ключ - "html", метод НЕ ГЕНЕРИРУЕТ заголовок для html отчета
     *
     * @param list Список с ключами в виде {"Name;", " ", "Hired;", " ", "Fired;", " ", "Salary;}
     */
    public void getHeader(List<String> list) {
        if (!list.contains("html")) {
            StringBuilder text = new StringBuilder();
            for (String s : list) {
                text.append(s);
            }
            this.header = text.append(ln);
        } else {
            this.header = new StringBuilder();
        }
    }

    /**
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
     * @return Отчет в html или текстовом формате
     */
    @Override
    public String generate(Predicate<Employee> filter) {
        List<Employee> empList = store.findBy(filter);
        doSort(empList);
        return reportSwitcher(empList);
    }

    /**
     * Определяет тип отчета по заголовку
     * Подробности о генерации заголовка в методе @getHeader
     *
     * @param empList Лист работников
     * @return Отчет в html или текстовом формате
     */
    public String reportSwitcher(List<Employee> empList) {
        String rsl;
        if (header.length() == 0) {
            rsl = genHtml(empList);
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
}