package ru.job4j.design.isp;

import java.util.*;

/**
 * Класс реализует интерфейс Menu, при создании экземпляра класса следует передать в конструктор
 * TreeMap с подпунктами меню, в котором имя это ключ, а объект это значение
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class MenuLessons implements Menu {
    private final Map<String, Submenu> submenuSet;

    public MenuLessons(Map<String, Submenu> submenuSet) {
        this.submenuSet = submenuSet;
    }

    /**
     * Отображает все пункты меню которые содержатся в коллекции, при выборе пункта меню
     * совершает действие которое относится к выбранному пункту
     * МИНИМАЛЬНАЯ РЕАЛИЗАЦИЯ БЕЗ ЦИКЛА, ДЛЯ ДЕМОНСТРАЦИИ
     */
    @Override
    public void run() {
        for (Map.Entry<String, Submenu> menu : submenuSet.entrySet()) {
            System.out.println(menu.getKey());
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите пункт меню: ");
        String input = scan.nextLine();
        submenuSet.get(input).doAction();
    }

    public static void main(String[] args) {
        Submenu submenu = new Lesson1("Lesson 1.", new ActionPrint());
        Submenu submenu11 = new Lesson11("Lesson 1.1.", new ActionShow());
        Submenu submenu12 = new Lesson12("Lesson 1.2.", new ActionPrint());
        Submenu submenu111 = new Lesson111("Lesson 1.1.1.", new ActionShow());
        Map<String, Submenu> set = new TreeMap<>();
        set.put(submenu.getName(), submenu);
        set.put(submenu12.getName(), submenu12);
        set.put(submenu11.getName(), submenu11);
        set.put(submenu111.getName(), submenu111);
        Menu menu = new MenuLessons(set);
        menu.run();
    }
}
