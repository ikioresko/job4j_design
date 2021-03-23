package ru.job4j.mail;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;

public class MailerTest {
    @Test
    public void whenMergeTwoUser() {
        Mailer mailer = new Mailer();
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> set1 = new HashSet<>();
        set1.add("xxx@ya.ru");
        set1.add("foo@gmail.com");
        set1.add("lol@mail.ru");
        Set<String> set2 = new HashSet<>();
        set2.add("foo@gmail.com");
        set2.add("ups@pisem.net");
        Set<String> set3 = new HashSet<>();
        set3.add("xyz@pisem.net");
        set3.add("vasya@pupkin.com");
        Set<String> set4 = new HashSet<>();
        set4.add("ups@pisem.net");
        set4.add("aaa@bbb.ru");
        Set<String> set5 = new HashSet<>();
        set5.add("xyz@pisem.net");
        Set<String> set6 = new HashSet<>();
        set6.add("www@pisem.net");
        map.put("user1", set1);
        map.put("user2", set2);
        map.put("user3", set3);
        map.put("user4", set4);
        map.put("user5", set5);
        map.put("user6", set6);
        Map<String, Set<String>> result = mailer.merge(map);
        Map<String, Set<String>> expected = Map.of(
                "user1", Set.of(
                        "aaa@bbb.ru", "ups@pisem.net", "lol@mail.ru", "xxx@ya.ru", "foo@gmail.com"),
                "user5", Set.of("vasya@pupkin.com", "xyz@pisem.net"),
                "user6", Set.of("www@pisem.net"));
        Assert.assertThat(result, is(expected));
    }

    @Test
    public void whenAllUnique() {
        Mailer mailer = new Mailer();
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> set1 = new HashSet<>();
        set1.add("aaa@ya.ru");
        set1.add("bbb@gmail.com");
        set1.add("ccc@mail.ru");
        Set<String> set2 = new HashSet<>();
        set2.add("ddd@gmail.com");
        set2.add("eee@pisem.net");
        Set<String> set3 = new HashSet<>();
        set3.add("fff@pisem.net");
        set3.add("ggg@pupkin.com");
        Set<String> set4 = new HashSet<>();
        set4.add("hhh@pisem.net");
        set4.add("iii@bbb.ru");
        Set<String> set5 = new HashSet<>();
        set5.add("jjj@pisem.net");
        map.put("user1", set1);
        map.put("user2", set2);
        map.put("user3", set3);
        map.put("user4", set4);
        map.put("user5", set5);
        Map<String, Set<String>> result = mailer.merge(map);
        Map<String, Set<String>> expected = Map.of(
                "user1", Set.of("aaa@ya.ru", "ccc@mail.ru", "bbb@gmail.com"),
                "user2", Set.of("eee@pisem.net", "ddd@gmail.com"),
                "user5", Set.of("jjj@pisem.net"),
                "user3", Set.of("ggg@pupkin.com", "fff@pisem.net"),
                "user4", Set.of("hhh@pisem.net", "iii@bbb.ru"));
        Assert.assertThat(result, is(expected));
    }

    @Test
    public void whenSomeSetEmptyAndMergeOneUser() {
        Mailer mailer = new Mailer();
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> set1 = new HashSet<>();
        set1.add("aaa@ya.ru");
        set1.add("bbb@gmail.com");
        set1.add("ccc@mail.ru");
        Set<String> set2 = new HashSet<>();
        Set<String> set3 = new HashSet<>();
        set3.add("fff@pisem.net");
        set3.add("ggg@pupkin.com");
        Set<String> set4 = new HashSet<>();
        set4.add("hhh@pisem.net");
        set4.add("bbb@gmail.com");
        Set<String> set5 = new HashSet<>();
        set5.add("jjj@pisem.net");
        map.put("user1", set1);
        map.put("user2", set2);
        map.put("user3", set3);
        map.put("user4", set4);
        map.put("user5", set5);
        Map<String, Set<String>> result = mailer.merge(map);
        Map<String, Set<String>> expected = Map.of(
                "user1", Set.of("aaa@ya.ru", "ccc@mail.ru", "bbb@gmail.com", "hhh@pisem.net"),
                "user5", Set.of("jjj@pisem.net"),
                "user3", Set.of("ggg@pupkin.com", "fff@pisem.net"));
        Assert.assertThat(result, is(expected));
    }

    @Test
    public void whenSomeElementOfSetIsNull() {
        Mailer mailer = new Mailer();
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> set1 = new HashSet<>();
        set1.add("aaa@ya.ru");
        set1.add("bbb@gmail.com");
        set1.add(null);
        set1.add("ccc@mail.ru");
        Set<String> set2 = new HashSet<>();
        Set<String> set3 = new HashSet<>();
        set3.add("fff@pisem.net");
        set3.add("ggg@pupkin.com");
        Set<String> set4 = new HashSet<>();
        set4.add("hhh@pisem.net");
        set4.add(null);
        set4.add("bbb@gmail.com");
        Set<String> set5 = new HashSet<>();
        set5.add("jjj@pisem.net");
        map.put("user1", set1);
        map.put("user2", set2);
        map.put("user3", set3);
        map.put("user4", set4);
        map.put("user5", set5);
        Map<String, Set<String>> result = mailer.merge(map);
        Set<String> setWithNull = new HashSet<>();
        setWithNull.add(null);
        setWithNull.add("aaa@ya.ru");
        setWithNull.add("ccc@mail.ru");
        setWithNull.add("bbb@gmail.com");
        setWithNull.add("hhh@pisem.net");
        Map<String, Set<String>> expected = Map.of(
                "user1", setWithNull,
                "user5", Set.of("jjj@pisem.net"),
                "user3", Set.of("ggg@pupkin.com", "fff@pisem.net"));
        Assert.assertThat(result, is(expected));
    }

    @Test
    public void whenSomeSetIsNull() {
        Mailer mailer = new Mailer();
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> set1 = new HashSet<>();
        set1.add("aaa@ya.ru");
        set1.add("bbb@gmail.com");
        set1.add("ccc@mail.ru");
        Set<String> set3 = new HashSet<>();
        set3.add("fff@pisem.net");
        set3.add("ggg@pupkin.com");
        Set<String> set4 = new HashSet<>();
        set4.add("hhh@pisem.net");
        set4.add("bbb@gmail.com");
        Set<String> set5 = new HashSet<>();
        set5.add("jjj@pisem.net");
        map.put("user1", set1);
        map.put("user2", null);
        map.put("user3", set3);
        map.put("user4", set4);
        map.put("user5", set5);
        Map<String, Set<String>> result = mailer.merge(map);
        Map<String, Set<String>> expected = Map.of(
                "user1", Set.of("aaa@ya.ru", "ccc@mail.ru", "bbb@gmail.com", "hhh@pisem.net"),
                "user5", Set.of("jjj@pisem.net"),
                "user3", Set.of("ggg@pupkin.com", "fff@pisem.net"));
        Assert.assertThat(result, is(expected));
    }
}