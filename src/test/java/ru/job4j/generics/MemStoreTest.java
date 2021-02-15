package ru.job4j.generics;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class MemStoreTest {

    @Test
    public void whenAddAndFindById() {
        MemStore<User> memStore = new MemStore<>();
        User user1 = new User("1");
        User user2 = new User("2");
        memStore.add(user1);
        memStore.add(user2);
        assertThat(memStore.findById("1"), is(user1));
        assertThat(memStore.findById("2"), is(user2));
    }

    @Test
    public void whenReplace() {
        MemStore<User> memStore = new MemStore<>();
        User user1 = new User("1");
        User user2 = new User("2");
        memStore.add(user1);
        assertThat(memStore.replace("1", user2), is(true));
        assertThat(memStore.findById("2"), is(user2));
        assertNull(memStore.findById("1"));
    }

    @Test
    public void whenDelete() {
        MemStore<User> memStore = new MemStore<>();
        User user1 = new User("1");
        memStore.add(user1);
        assertThat(memStore.delete("1"), is(true));
        assertNull(memStore.findById("1"));
    }

    @Test
    public void whenIdNotFound() {
        MemStore<User> memStore = new MemStore<>();
        User user1 = new User("1");
        memStore.add(user1);
        assertNull(memStore.findById("2"));
        assertThat(memStore.delete("2"), is(false));
        assertThat(memStore.replace("2", new User("3")), is(false));
    }
}