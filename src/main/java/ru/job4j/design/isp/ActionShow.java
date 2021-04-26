package ru.job4j.design.isp;

/**
 * Класс демонстрирует возможность совершить абстрактное действие
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class ActionShow implements Action {
    @Override
    public void doSomeAction() {
        System.out.println("This is another one action");
    }
}
