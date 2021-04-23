package ru.job4j.design.srp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Данный класс используется для сериализации отчета по работникам
 *
 * @author Kioresko Igor
 * @version 0.1
 */
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employees implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Employee> empList;

    public Employees(Predicate<Employee> filter, Store store) {
        this.empList = store.findBy(filter);
    }

    public Employees() {
        this.empList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employees employees = (Employees) o;
        return Objects.equals(empList, employees.empList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empList);
    }

    @Override
    public String toString() {
        return "Employees{"
                + "empList=" + empList
                + '}';
    }
}
