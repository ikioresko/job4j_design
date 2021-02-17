package ru.job4j.generics;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class RoleStoreTest  {
    @Test
    public void whenAddAndFindById() {
        RoleStore roleStore = new RoleStore();
        Role role1 = new Role("1");
        Role role2 = new Role("2");
        roleStore.add(role1);
        roleStore.add(role2);
        assertThat(roleStore.findById("1"), is(role1));
        assertThat(roleStore.findById("2"), is(role2));
    }

    @Test
    public void whenReplace() {
        RoleStore roleStore = new RoleStore();
        Role role1 = new Role("1");
        Role role2 = new Role("2");
        roleStore.add(role1);
        assertThat(roleStore.replace("1", role2), is(true));
        assertThat(roleStore.findById("2"), is(role2));
        assertNull(roleStore.findById("1"));
    }

    @Test
    public void whenDelete() {
        RoleStore roleStore = new RoleStore();
        Role role1 = new Role("1");
        roleStore.add(role1);
        assertThat(roleStore.delete("1"), is(true));
        assertNull(roleStore.findById("1"));
    }

    @Test
    public void whenIdNotFound() {
        RoleStore roleStore = new RoleStore();
        Role role1 = new Role("1");
        roleStore.add(role1);
        assertNull(roleStore.findById("2"));
        assertThat(roleStore.delete("2"), is(false));
        assertThat(roleStore.replace("2", new Role("3")), is(false));
    }
}