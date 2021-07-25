package com.mad.triviaapp.utils;

public class NumberUtil {

    public static Integer parseInt(String number) {
        if (number != null && number.length() > 0) {
            try {
                return Integer.parseInt(number);
            } catch (Exception e) {
                return -1;
            }
        } else return 0;
    }
}
