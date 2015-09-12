package com.example.bence.sqlitetesting.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by bence on 06.09.15.
 */
public class UtcDateFormat {
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        DATETIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        String result = DATETIME_FORMAT.format(date);
        return result;
    }

    public String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        String result = DATE_FORMAT.format(date);
        return result;
    }

    public Date parseDateTime(String dateTimeString) {
        Date date = parse(DATETIME_FORMAT, dateTimeString);
        return date;
    }

    public Date parseDate(String dateString) {
        Date date = parse(DATE_FORMAT, dateString);
        return date;
    }

    private Date parse(SimpleDateFormat dateFormat, String dateTimeString) {
        Date date = null;
        if (dateTimeString != null) {
            try {
                date = dateFormat.parse(dateTimeString);
            } catch (ParseException e) {
                String message = String.format("Unable to parse '%s' with pattern '%s'.",
                        dateTimeString, dateFormat.toPattern());
                throw new RuntimeException(message);
            }
        }
        return date;
    }

}
