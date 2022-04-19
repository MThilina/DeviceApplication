package com.thilinam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final String DATE_TIME_STRING_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private static final String ISO_DATE_PATTERN= "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATE_STRING_PATTERN = "yyyy-MM-dd";

    public static Date isoStringToDate(String string) throws ParseException {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        TemporalAccessor accessor = timeFormatter.parse(string);
        Date date = Date.from(Instant.from(accessor));

        return date;
    }

    public static String dateToISOstring(Date date) {

        if(date == null){
            return null;
        }
        String isoDatePattern = ISO_DATE_PATTERN;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    /**
     * This method used to get date when file upload
     * @param date
     * @param timeZoneId
     */
    public static String dateToISOstringWithTimeZone(Date date, String timeZoneId) {

        if(date == null){
            return null;
        }
        String isoDatePattern = ISO_DATE_PATTERN;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }



    public static String dateToDateString(Date date) {

        if(date == null){
            return null;
        }
        String isoDatePattern = "yyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static int getZoneOffsetByISOString(String isoString) {

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoString, DateTimeFormatter.ISO_DATE_TIME);
        TimeZone timeZone = TimeZone.getTimeZone(zonedDateTime.getZone());
        return timeZone.getRawOffset();
    }

    public static ZonedDateTime getZoneDateTime(Date isoUtcDate, String timeZoneId) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        return ZonedDateTime.ofInstant(isoUtcDate.toInstant(), timeZone.toZoneId());
    }


    /**
     * Format java.util.date to 'yyyy-mm-dd hh:mm:ss' pattern string
     *
     * @param date date to format
     * @return formatted date time string
     */
    public static String dateToDateTimeString(Date date){

        if(date == null){
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_STRING_PATTERN);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

}