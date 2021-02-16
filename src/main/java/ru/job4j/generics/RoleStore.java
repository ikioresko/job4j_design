package ru.job4j.generics;

public class RoleStore<T extends Base> implements Store<T> {
    private final Store<T> role = new MemStore<>();

    @Override
    public void add(T model) {
        role.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        return role.replace(id, model);
    }

    @Override
    public boolean delete(String id) {
        return role.delete(id);
    }

    @Override
    public T findById(String id) {
        return role.findById(id);
    }
}
