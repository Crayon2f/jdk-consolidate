package com.crayon2f.jdk.consolidate.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by feiFan.gou on 2018/1/17 15:20.
 */
public class FirstLastDay {

    private static final SimpleDateFormat yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");

    //获取每月第一天
    public static Date getFirstDayMonthByDate(Date d) {
        Calendar cale;
        cale = Calendar.getInstance();
        cale.setTime(d);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }

    //获取每周第一天
    public static Date getFirstDayWeekByDate(Date d) {
        Calendar cale;
        cale = Calendar.getInstance();
        cale.setTime(d);
        cale.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cale.getTime();
    }

    //获取每月最后一天
    public static Date getLastDayOfMonth(Date current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String stringDate =  year + "-" + (month < 10 ? ("0" + month) : month) + "-" + date;
        try {
            return yyyy_mm_dd.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //获取每周最后一天
    public static Date getLastDayOfWeek(Date current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        int d;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - calendar.get(Calendar.DAY_OF_WEEK);
        }
        calendar.add(Calendar.DAY_OF_WEEK, d);
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        //所在周结束日期
        return calendar.getTime();
    }
}
