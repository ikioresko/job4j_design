package ru.job4j.generics;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class RoleStoreTest  {
    @Test
    public void whenAddAndFindById() {
        RoleStore<User> roleStore = new RoleStore<>();
        User user1 = new User("1");
        User user2 = new User("2");
        roleStore.add(user1);
        roleStore.add(user2);
        assertThat(roleStore.findById("1"), is(user1));
        assertThat(roleStore.findById("2"), is(user2));
    }

    @Test
    public void whenReplace() {
        RoleStore<User> roleStore = new RoleStore<>();
        User user1 = new User("1");
        User user2 = new User("2");
        roleStore.add(user1);
        assertThat(roleStore.replace("1", user2), is(true));
        assertThat(roleStore.findById("2"), is(user2));
        assertNull(roleStore.findById("1"));
    }

    @Test
    public void whenDelete() {
        RoleStore<User> roleStore = new RoleStore<>();
        User user1 = new User("1");
        roleStore.add(user1);
        assertThat(roleStore.delete("1"), is(true));
        assertNull(roleStore.findById("1"));
    }

    @Test
    public void whenIdNotFound() {
        RoleStore<User> roleStore = new RoleStore<>();
        User user1 = new User("1");
        roleStore.add(user1);
        assertNull(roleStore.findById("2"));
        assertThat(roleStore.delete("2"), is(false));
        assertThat(roleStore.replace("2", new User("3")), is(false));
    }
}