package com.mad.triviaapp.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DatetimeUtil {

    public static final String DAY_MONTH_DATE_TIME = "EEE, MMM dd, hh:mm aaa";

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat getSimpleDateTimeFormat() {
        return new SimpleDateFormat(DAY_MONTH_DATE_TIME);
    }

    /**
     * formats to EEE, MMM dd, hh:mm aaa format
     *
     * @param milliSeconds - time in milli seconds
     * @return - date in EEE, MMM dd, hh:mm aaa format
     */
    public static String formatDateTime(long milliSeconds) {
        return getSimpleDateTimeFormat().format(new Date(milliSeconds));
    }

}
