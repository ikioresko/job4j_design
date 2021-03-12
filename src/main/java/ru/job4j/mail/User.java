package ru.job4j.mail;

import java.util.List;
import java.util.Objects;

public class User {
    private String userName;
    private List<String> emails;

    public User(String userName, List<String> emails) {
        this.userName = userName;
        this.emails = emails;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getEmails() {
        return emails;
    }

    @Override
    public String toString() {
        return "User{"
                + "userName='" + userName + '\''
                + ", emails=" + emails + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(emails, user.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, emails);
    }
}