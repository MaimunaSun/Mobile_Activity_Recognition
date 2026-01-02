package com.example.fitbox;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserProfileDateConverter {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @TypeConverter
    public static Date fromDateString(String value) {
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String dateToDateString(Date date) {
        return date == null ? null : new SimpleDateFormat(DATE_FORMAT, Locale.US).format(date);
    }
}
