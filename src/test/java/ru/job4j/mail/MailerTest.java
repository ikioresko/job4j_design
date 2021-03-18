package ru.job4j.mail;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

public class MailerTest {
    @Test
    public void whenMergeTwoUser() {
        Mailer mailer = new Mailer();
        List<User> userList = new ArrayList<>();
        User user1 = new User("user1",
                new ArrayList<>(List.of("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        User user2 = new User("user2",
                new ArrayList<>(List.of("foo@gmail.com", "ups@pisem.net")));
        User user3 = new User("user3",
                new ArrayList<>(List.of("xyz@pisem.net", "vasya@pupkin.com")));
        User user4 = new User("user4",
                new ArrayList<>(List.of("ups@pisem.net", "aaa@bbb.ru")));
        User user5 = new User("user5",
                new ArrayList<>(List.of("xyz@pisem.net")));
        User user6 = new User("user6",
                new ArrayList<>(List.of("www@pisem.net")));
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
        List<User> expected = List.of(new User("user1",
                        List.of("xxx@ya.ru", "lol@mail.ru", "foo@gmail.com",
                                "ups@pisem.net", "aaa@bbb.ru")),
                new User("user3", List.of("vasya@pupkin.com", "xyz@pisem.net")),
                new User("user6", List.of("www@pisem.net")));
        Assert.assertThat(mailer.userMerge(userList), is(expected));
    }

    @Test
    public void whenAllUnique() {
        Mailer mailer = new Mailer();
        List<User> userList = new ArrayList<>();
        User user1 = new User("user1",
                new ArrayList<>(List.of("xxxs@ya.ru", "foos@gmail.com", "lols@mail.ru")));
        User user2 = new User("user2",
                new ArrayList<>(List.of("food@gmail.com", "upsd@pisem.net")));
        User user3 = new User("user3",
                new ArrayList<>(List.of("xyzf@pisem.net", "vasyaf@pupkin.com")));
        User user4 = new User("user4",
                new ArrayList<>(List.of("upsg@pisem.net", "aaag@bbb.ru")));
        User user5 = new User("user5",
                new ArrayList<>(List.of("xyz@pisem.net")));
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        List<User> expected = List.of(new User("user1",
                        List.of("xxxs@ya.ru", "foos@gmail.com", "lols@mail.ru")),
                new User("user2", List.of("food@gmail.com", "upsd@pisem.net")),
                new User("user3", List.of("xyzf@pisem.net", "vasyaf@pupkin.com")),
                new User("user4", List.of("upsg@pisem.net", "aaag@bbb.ru")),
                new User("user5", List.of("xyz@pisem.net")));
        Assert.assertThat(mailer.userMerge(userList), is(expected));
    }

    @Test
    public void whenSomeEmptyList() {
        Mailer mailer = new Mailer();
        List<User> userList = new ArrayList<>();
        User user1 = new User("user1",
                new ArrayList<>(List.of("xxxs@ya.ru", "foos@gmail.com", "lols@mail.ru")));
        User user2 = new User("user2",
                new ArrayList<>(List.of()));
        User user3 = new User("user3",
                new ArrayList<>(List.of("xyzf@pisem.net", "vasyaf@pupkin.com")));
        User user4 = new User("user4",
                new ArrayList<>(List.of("upsg@pisem.net", "aaag@bbb.ru")));
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        List<User> expected = List.of(new User("user1",
                        List.of("xxxs@ya.ru", "foos@gmail.com", "lols@mail.ru")),
                new User("user2", List.of()),
                new User("user3", List.of("xyzf@pisem.net", "vasyaf@pupkin.com")),
                new User("user4", List.of("upsg@pisem.net", "aaag@bbb.ru")));
        Assert.assertThat(mailer.userMerge(userList), is(expected));
    }

    @Test
    public void whenSomeElementOfLisIsNull() {
        Mailer mailer = new Mailer();
        List<User> userList = new ArrayList<>();
        List<String> nullStr = new ArrayList<>();
        nullStr.add(null);
        nullStr.add("xxx@ya.ru");
        nullStr.add(null);
        nullStr.add("foo2@gmail.com");
        User user1 = new User("user1",
                new ArrayList<>(List.of("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        User user2 = new User("user2", nullStr);
        User user3 = new User("user3",
                new ArrayList<>(List.of("xyz@pisem.net", "vasya@pupkin.com")));
        User user4 = new User("user4",
                new ArrayList<>(List.of("ups@pisem.net", "aaa@bbb.ru")));
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        List<String> expNullList = new ArrayList<>();
        expNullList.add("foo@gmail.com");
        expNullList.add("lol@mail.ru");
        expNullList.add(null);
        expNullList.add("xxx@ya.ru");
        expNullList.add(null);
        expNullList.add("foo2@gmail.com");
        List<User> expected = List.of(new User("user1", expNullList),
                new User("user3", List.of("xyz@pisem.net", "vasya@pupkin.com")),
                new User("user4", List.of("ups@pisem.net", "aaa@bbb.ru")));
        Assert.assertThat(mailer.userMerge(userList), is(expected));
    }

    @Test
    public void whenListIsNull() {
        Mailer mailer = new Mailer();
        List<User> userList = new ArrayList<>();
        User user1 = new User("user1",
                new ArrayList<>(List.of("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        User user2 = new User("user2", null);
        User user3 = new User("user3",
                new ArrayList<>(List.of("xyz@pisem.net", "foo@gmail.com")));
        User user4 = new User("user4",
                new ArrayList<>(List.of("ups@pisem.net", "aaa@bbb.ru")));
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        List<User> expected = List.of(new User("user1",
                        List.of("xxx@ya.ru", "lol@mail.ru", "xyz@pisem.net", "foo@gmail.com")),
                user2, new User("user4", List.of("ups@pisem.net", "aaa@bbb.ru")));
        Assert.assertThat(mailer.userMerge(userList), is(expected));
    }
}