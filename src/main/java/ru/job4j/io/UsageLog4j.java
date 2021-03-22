package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Petr Arsentev";
        int age = 33;
        byte bt = 1;
        short shr = 32000;
        char ch = 's';
        long lng = 1234567890L;
        float flt = 13.3f;
        double dbl = 3.14;
        boolean bool = true;
        LOG.debug("User info name : {}, age : {}, bt : {}, shr : {}, ch {},"
                        + " lng : {}, flt {}, dbl : {}, bool : {}",
                name, age, bt, shr, ch, lng, flt, dbl, bool);
    }
}