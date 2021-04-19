package ru.job4j.ood.srp;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final List<User> list;

    public Storage() {
        this.list = new ArrayList<>();
    }

    public void add(User user) {
        list.add(user);
    }

    public void print() {
        for (User u : list) {
            System.out.println(u.toString());
        }
    }
}
