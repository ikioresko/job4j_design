package ru.job4j.generics;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class UserStoreTest {
    @Test
    public void whenAddAndFindById() {
        UserStore userStore = new UserStore();
        User user1 = new User("1");
        User user2 = new User("2");
        userStore.add(user1);
        userStore.add(user2);
        assertThat(userStore.findById("1"), is(user1));
        assertThat(userStore.findById("2"), is(user2));
    }

    @Test
    public void whenReplace() {
        UserStore userStore = new UserStore();
        User user1 = new User("1");
        User user2 = new User("2");
        userStore.add(user1);
        assertThat(userStore.replace("1", user2), is(true));
        assertThat(userStore.findById("2"), is(user2));
        assertNull(userStore.findById("1"));
    }

    @Test
    public void whenDelete() {
        UserStore userStore = new UserStore();
        User user1 = new User("1");
        userStore.add(user1);
        assertThat(userStore.delete("1"), is(true));
        assertNull(userStore.findById("1"));
    }

    @Test
    public void whenIdNotFound() {
        UserStore userStore = new UserStore();
        User user1 = new User("1");
        userStore.add(user1);
        assertNull(userStore.findById("2"));
        assertThat(userStore.delete("2"), is(false));
        assertThat(userStore.replace("2", new User("3")), is(false));
    }
}