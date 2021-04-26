package ru.job4j.design.isp;

/**
 * Класс реализует интерфейс Submenu содержит в себе поля имя и действие которое будет выполнено
 * при выборе данного пункта меню. Реализует метод compareTo для сортировки подпунктов по имени
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class Lesson11 implements Submenu {
    private final String name;
    private final Action action;

    public Lesson11(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public void doAction() {
        action.doSomeAction();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Submenu o) {
        return this.name.compareTo(o.getName());
    }
}
