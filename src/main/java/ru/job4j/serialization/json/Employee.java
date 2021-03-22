package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.annotation.*;
import java.util.Arrays;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {
    @XmlAttribute
    private boolean sex;

    @XmlAttribute
    private int age;
    private Children children;

    @XmlElementWrapper(name = "statuses")
    @XmlElement(name = "status")
    private String[] duties;

    public Employee(boolean sex, int age, Children children, String... duties) {
        this.sex = sex;
        this.age = age;
        this.children = children;
        this.duties = duties;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{"
                + "sex=" + sex
                + ", age=" + age
                + ", children=" + children
                + ", duties=" + Arrays.toString(duties) + '}';
    }

    public static void main(String[] args) {
        Employee emp = new Employee(true, 30, new Children("Daniel", 10), "work", "relax");
        Gson gson = new GsonBuilder().create();
        String gsonEmp = gson.toJson(emp);
        System.out.println(gsonEmp);
        Employee empMod = gson.fromJson(gsonEmp, Employee.class);
        System.out.println(empMod);
    }
}
