package com.heroes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Stanislav Tyurikov
 */
public class DateUtils
{
    public static Date date(String date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
