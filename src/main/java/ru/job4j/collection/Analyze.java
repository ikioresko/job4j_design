package ru.job4j.collection;

import java.util.*;

public class Analyze {
    public static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
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
            return id == user.id && Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    public static class Info {
        private int added = 0;
        private int changed = 0;
        private int deleted = 0;

        public Info() {
        }

        public Info(int added, int changed, int deleted) {
            this.added = added;
            this.changed = changed;
            this.deleted = deleted;
        }

        @Override
        public String toString() {
            return "Info{"
                    + "added=" + added
                    + ", changed=" + changed
                    + ", deleted=" + deleted + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Info info = (Info) o;
            return added == info.added && changed == info.changed && deleted == info.deleted;
        }

        @Override
        public int hashCode() {
            return Objects.hash(added, changed, deleted);
        }
    }

    public Info diff(List<User> previous, List<User> current) {
        Info result = new Info();
        int contains = 0;
        Map<Integer, String> prevUserMap = new HashMap<>();
        for (User us : previous) {
            prevUserMap.put(us.id, us.name);
        }
        for (Map.Entry<Integer, String> user : prevUserMap.entrySet()) {
            for (User curUser : current) {
                if (user.getKey() == curUser.id) {
                    if (user.getValue().equals(curUser.name)) {
                        contains++;
                    } else {
                        result.changed++;
                    }
                }
            }
        }
        result.deleted = previous.size() - contains - result.changed;
        result.added = current.size() - result.changed - contains;
        return result;
    }
}