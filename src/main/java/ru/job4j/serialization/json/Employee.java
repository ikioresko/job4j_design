package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;

public class Employee {
    private final boolean sex;
    private final int age;
    private final Children children;
    private final String[] duties;

    public Employee(boolean sex, int age, Children children, String... duties) {
        this.sex = sex;
        this.age = age;
        this.children = children;
        this.duties = duties;
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
