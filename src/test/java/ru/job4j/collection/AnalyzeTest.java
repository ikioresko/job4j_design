package ru.job4j.collection;

import ru.job4j.collection.Analyze.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.List;

public class AnalyzeTest {
    @Test
    public void whenAdd() {
        Analyze analyze = new Analyze();
        User user1 = new User(1, "Name1");
        User user2 = new User(2, "Name2");
        User user3 = new User(3, "Name3");
        User user4 = new User(4, "Name4");
        User user5 = new User(5, "Name5");
        List<User> previous = List.of(user1, user2, user3, user4, user5);
        List<User> current = List.of(user1, user2, user3, user4, user5,
                new User(10, "NewName10"),
                new User(11, "NewName11"),
                new User(12, "NewName12"),
                new User(13, "NewName13"));
        assertThat(analyze.diff(previous, current), is(new Info(4, 0, 0)));
    }

    @Test
    public void whenChange() {
        User user1 = new User(1, "Name1");
        User user2 = new User(2, "Name2");
        User user3 = new User(3, "Name3");
        User user4 = new User(4, "Name4");
        List<User> previous = List.of(user1, user2, user3, user4);
        List<User> current = List.of(
                new User(1, "ChangeName1"),
                new User(2, "ChangeName2"),
                new User(3, "ChangeName3"),
                new User(4, "ChangeName4"));
        assertThat(new Analyze().diff(previous, current), is(new Info(0, 4, 0)));
    }

    @Test
    public void whenDelete() {
        User user1 = new User(1, "Name1");
        User user2 = new User(2, "Name2");
        User user3 = new User(3, "Name3");
        User user4 = new User(4, "Name4");
        List<User> previous = List.of(user1, user2, user3, user4);
        List<User> current = List.of(new User(1, "Name1"));
        assertThat(new Analyze().diff(previous, current), is(new Info(0, 0, 3)));
    }

    @Test
    public void whenAddAndDelete() {
        User user1 = new User(1, "Name1");
        User user2 = new User(2, "Name2");
        User user3 = new User(3, "Name3");
        User user4 = new User(4, "Name4");
        User user5 = new User(5, "Name5");
        List<User> previous = List.of(user1, user2, user3, user4, user5);
        List<User> current = List.of(
                new User(10, "NewName10"),
                new User(11, "NewName11"),
                new User(12, "NewName12"),
                new User(13, "NewName13"));
        assertThat(new Analyze().diff(previous, current), is(new Info(4, 0, 5)));
    }

    @Test
    public void whenAddAndChange() {
        User user1 = new User(1, "Name1");
        User user2 = new User(2, "Name2");
        User user3 = new User(3, "Name3");
        User user4 = new User(4, "Name4");
        User user5 = new User(5, "Name5");
        List<User> previous = List.of(user1, user2, user3, user4, user5);
        List<User> current = List.of(user2, user3, user4,
                new User(1, "ChangeName1"),
                new User(11, "NewName11"),
                new User(12, "NewName12"),
                new User(5, "ChangeName5"));
        assertThat(new Analyze().diff(previous, current), is(new Info(2, 2, 0)));
    }

    @Test
    public void whenAddDeleteChange() {
        User user1 = new User(1, "Name1");
        User user2 = new User(2, "Name2");
        User user3 = new User(3, "Name3");
        User user4 = new User(4, "Name4");
        User user5 = new User(5, "Name5");
        User user6 = new User(6, "Name6");
        User user7 = new User(7, "Name7");
        User user8 = new User(8, "Name8");
        User user9 = new User(9, "Name9");
        User user10 = new User(10, "Name10");
        List<User> previous = List.of(user1, user2, user3, user4, user5,
                user6, user7, user8, user9, user10);
        List<User> current = List.of(user1, user5, user10,
                new User(3, "Name33"),
                new User(30, "NewName1"),
                new User(6, "Name66"),
                new User(7, "Name77"),
                new User(4, "Name44"),
                new User(40, "NewName2"));
        assertThat(new Analyze().diff(previous, current), is(new Info(2, 4, 3)));
    }
}