package com.example.fitbox;
import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityDateConverter {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @TypeConverter
    public static Date fromTimestamp(String value) {
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        return date == null ? null : new SimpleDateFormat(DATE_FORMAT, Locale.US).format(date);
    }
}



