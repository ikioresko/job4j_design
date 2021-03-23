package ru.job4j.mail;

import java.util.*;

public class Mailer {
    public Map<String, Set<String>> merge(Map<String, Set<String>> data) {
        Iterator<Map.Entry<String, Set<String>>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Set<String> value = iterator.next().getValue();
            if (value != null && value.size() != 0) {
                for (String key : data.keySet()) {
                    Set<String> emails = data.get(key);
                    if (emails != null && !Objects.equals(value, emails)) {
                        for (String email : emails) {
                            if (email != null && value.contains(email)) {
                                value.addAll(emails);
                                emails.clear();
                            }
                        }
                    }
                }
            } else {
                iterator.remove();
            }
        }
        return data;
    }
}