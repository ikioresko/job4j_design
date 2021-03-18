package ru.job4j.mail;

import java.util.*;

public class Mailer {

    public List<User> userMerge(List<User> theUsers) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < theUsers.size(); i++) {
            List<String> emails = theUsers.get(i).getEmails();
            if (!map.isEmpty() && emails != null) {
                String tempName = theUsers.get(map.size() - 1).getUserName();
                    for (String email : emails) {
                        List<String> mapList = map.get(tempName);
                        if (mapList != null && mapList.contains(email)) {
                            mapList.removeAll(emails);
                            mapList.addAll(emails);
                            theUsers.remove(i);
                            i--;
                            break;
                    }
                }
            }
            if (i == theUsers.size() - 1) {
                i = map.size();
                map.put(theUsers.get(i).getUserName(), theUsers.get(i).getEmails());
            }
        }
        return theUsers;
    }
}