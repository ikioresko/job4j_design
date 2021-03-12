package ru.job4j.mail;

import java.util.*;

public class Mailer {

    public List<User> userMerge(List<User> theUsers) {
        for (int i = 0; i < theUsers.size(); i++) {
            for (int j = 0; j < theUsers.size(); j++) {
                List<String> emailThe = theUsers.get(i).getEmails();
                List<String> email = theUsers.get(j).getEmails();
                if ((email != null && emailThe != null) && !Objects.equals(email, emailThe)) {
                    for (int n = 0; n < emailThe.size(); n++) {
                        if (email.contains(emailThe.get(n))) {
                            email.removeAll(emailThe);
                            email.addAll(emailThe);
                            theUsers.remove(theUsers.get(i));
                            break;
                        }
                    }
                }
            }
        }
        return theUsers;
    }
}