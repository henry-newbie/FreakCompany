package com.henry.freakcompany.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by henry on 2016/6/15.
 */
public class DateUtil {

    private final static int MillSecLenOneDay = 24 * 60 * 60 * 1000;   // 一天的毫秒数

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    private static final Object object = new Object();

    /**
     * 获取SimpleDateFormat
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 以友好的方式显示时间
     * @param date
     * @return
     */
    public static String friendlyForTime(Date date) {
        Date curDate = getCurrentDate();
        if (date == null && date.after(curDate)) { // 如果日期为null或者日期比今天大
            return null;
        }
        String friendlyTime = null;

        int intervalDays = getIntervalDaysStrict(curDate, date);
        if (intervalDays == 0) { // 相差不到一天
            int hour = (int) ((curDate.getTime() - date.getTime()) / 3600000);
            if (hour == 0)  // 同一个小时
                friendlyTime = Math.max(
                        (curDate.getTime() - date.getTime()) / 60000, 1)
                        + "分钟前";
            else
                friendlyTime = hour + "小时前";
            return friendlyTime;
        } else { // 相差大于一天
            if(intervalDays == 1) {
                friendlyTime = "昨天";
            } else if(intervalDays == 2) {
                friendlyTime = "前天";
            } else if(intervalDays > 2 && intervalDays <= 10) {
                friendlyTime = intervalDays + "天前";
            } else if(intervalDays > 10){
                friendlyTime = DateToString(date, DateStyle.YYYY_MM_DD);
            }
        }
        return friendlyTime;
    }

    /**
     * 获取当前时间
     * */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取两个日期相差的天数（严格意义上的计算，比如今天中午与昨天晚上的两个时间，相差0天）
     * @param date
     * @param otherDate
     * @return
     */
    public static int getIntervalDaysStrict(Date date, Date otherDate) {
        if (null == date || null == otherDate) {
            return -1;
        }

        long intervalMilli = Math.abs(date.getTime() - otherDate.getTime());

        return (int) (intervalMilli / MillSecLenOneDay);
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param dateStyle 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, DateStyle dateStyle) {
        if (dateStyle != null) {
            return StringToDate(date, dateStyle.getValue());
        }
        return null;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {

            }
        }
        return myDate;
    }

    public enum DateStyle {

        YYYY_MM("yyyy-MM"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

        YYYY_MM_EN("yyyy/MM"),
        YYYY_MM_DD_EN("yyyy/MM/dd"),
        YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

        YYYY_MM_CN("yyyy年MM月"),
        YYYY_MM_DD_CN("yyyy年MM月dd日"),
        YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
        YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

        HH_MM("HH:mm"),
        HH_MM_SS("HH:mm:ss"),

        MM_DD("MM-dd"),
        MM_DD_HH_MM("MM-dd HH:mm"),
        MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),

        MM_DD_EN("MM/dd"),
        MM_DD_HH_MM_EN("MM/dd HH:mm"),
        MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),

        MM_DD_CN("MM月dd日"),
        MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
        MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss");

        private String value;


        DateStyle(String value) {
            this.value = value;
        }

        public String  getValue() {
            return value;
        }
    }
}
