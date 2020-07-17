/**
 * 文件名: TimeUtil.java
 * <p>
 * 作者: 常官清 (changguanqing@enlink.cn)
 * 描述:
 * <p>
 * Copyright @2018 Enlink, All Rights Reserved.
 */
package com.security.common.utils;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtil
 * <p>
 * Created by changgq on 2019/03/29
 */
@Slf4j
public class TimeUtils {
    public static final int MS = 1;
    public static final int SECOND = 1000 * MS;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    public static final String DATE_TIME_PATTERN_STR = "yyyy-MM-dd HH-mm-ss";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "mm";
    public static final String HOUR_ONLY_PATTERN = "HH";

    public static final String UTC_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";

    /**
     * 日期相加减天数
     *
     * @param day
     * @param days
     * @param includeTime
     * @return
     * @throws ParseException
     */
    public static Date plus(Date day, int days, boolean includeTime) throws ParseException {
        if (day == null) {
            day = new Date();
        }
        if (!includeTime) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            day = df.parse(df.format(day));
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 时间格式化成字符串
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date date, String pattern) {
        if (StringCommonUtils.isNullOrEmpty(pattern)) {
            pattern = DATE_PATTERN;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 比较两个时间字段的大小，结果大于零表示：start < end；结果小于零表示：start > end
     *
     * @param start
     * @param end
     * @return
     */
    public static long diff(Date start, Date end) {
        return end.getTime() - start.getTime();
    }

    /**
     * 校验两个时间是否在同一天内
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean checkInDay(Date start, Date end) {
        return dateFormat(start, DATE_PATTERN).equals(dateFormat(end, DATE_PATTERN));
    }

    /**
     * 校验两个时间是否在同一月内
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean checkInMonth(Date start, Date end) {
        return dateFormat(start, MONTH_PATTERN).equals(dateFormat(end, MONTH_PATTERN));
    }

    /**
     * 校验两个时间是否在统一年内
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean checkInYear(Date start, Date end) {
        return dateFormat(start, YEAR_PATTERN).equals(dateFormat(end, YEAR_PATTERN));
    }

    /**
     * toString
     * <p>
     * 格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     *
     * @param date
     * @return
     */
    public static String toStringOfUTC(Date date) {
        return date.toInstant().toString();
    }

    /**
     * toString
     * <p>
     * 格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     *
     * @param date
     * @return
     */
    public static String toStringAtOffset(Date date, int hours) {
        return date.toInstant().atOffset(ZoneOffset.ofHours(hours)).toString();
    }

    /**
     * toString
     * <p>将时间字符串转化成 yyyy-MM-dd HH:mm:ss</>
     * @param dateStr
     * @author: tongq
     * @return
     */
    public static String getPatternTime(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.SIMPLIFIED_CHINESE);
        String time = null;
        try {

            log.info("UTC: "+dateStr);
            Date date = dateFormat.parse(dateStr.replace("T"," ").replace("Z",""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
            time = dateFormat.format(calendar.getTime());
            log.info("local: " + time);
        } catch (ParseException e) {
            log.error("时间转换出错");
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取 N 个月 前/后 的格式化时间字符串
     * @param month 月数 正数标识向后移，负数表示向前移
     * @return String
     */
    public static String getBeforeNMonthTimeStr(int month){
        // 当前时间
        Date dNow = new Date();
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(dNow);
        //设置为前N月
        calendar.add(Calendar.MONTH, month);
        //得到前N月的时间
        Date dBefore = calendar.getTime();
        //设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.DATE_TIME_PATTERN);
        //格式化前N月的时间
        return sdf.format(dBefore);
    }

    public static String getNowDateTimeStr(){
        return TimeUtils.dateFormat(new Date(), TimeUtils.DATE_TIME_PATTERN);
    }
}
