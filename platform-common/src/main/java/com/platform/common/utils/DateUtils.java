/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */

package com.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理
 *
 * @author 李鹏军
 */
@Slf4j
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 无分隔符日期格式 "yyyyMMddHHmmssSSS"
     */
    public static String DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";
    /**
     * 不带秒的标准日期格式 "yyyy.MM.dd HH:mm"
     */
    public static String PATTERN_YYYY_MM_DD_HH_MM = "yyyy.MM.dd HH:mm";

    public static String PATTERN_YYYY_MM_DD_T_HH_MM_SSZ = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String PATTERN_YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 日期转换格式数组
     */
    public static String[][] regularExp = new String[][]{

            // 默认格式
            {"\\d{4}-((([0][1,3-9]|[1][0-2]|[1-9])-([0-2]\\d|[3][0,1]|[1-9]))|((02|2)-(([1-9])|[0-2]\\d)))\\s+([0,1]\\d|[2][0-3]|\\d):([0-5]\\d|\\d):([0-5]\\d|\\d)",
                    DATE_TIME_PATTERN},
            // 仅日期格式 年月日 时 分 秒
            {"\\d{4}.((([0][1,3-9]|[1][0-2]|[1-9]).([0-2]\\d|[3][0,1]|[1-9]))|((02|2).(([1-9])|[0-2]\\d)))\\s+([0,1]\\d|[2][0-3]|\\d):([0-5]\\d|\\d)",
                    PATTERN_YYYY_MM_DD_HH_MM},
            // 仅日期格式 年月日
            {"\\d{4}-((([0][1,3-9]|[1][0-2]|[1-9])-([0-2]\\d|[3][0,1]|[1-9]))|((02|2)-(([1-9])|[0-2]\\d)))",
                    DATE_PATTERN},
            //  带毫秒格式
            {"\\d{4}((([0][1,3-9]|[1][0-2]|[1-9])([0-2]\\d|[3][0,1]|[1-9]))|((02|2)(([1-9])|[0-2]\\d)))([0,1]\\d|[2][0-3])([0-5]\\d|\\d)([0-5]\\d|\\d)\\d{1,3}",
                    DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS}
    };

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @return Date
     */
    public static Date stringToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(PATTERN_YYYY_MM_DD_T_HH_MM_SSZ);
        try {
            return fmt.parseLocalDateTime(strDate).toDate();
        } catch (Exception e) {
            // 微信返回的时间字段有不带Z的
            fmt = DateTimeFormat.forPattern(PATTERN_YYYY_MM_DD_T_HH_MM_SS);
            return fmt.parseLocalDateTime(strDate).toDate();
        }
    }


    /**
     * 根据传入的日期格式字符串，获取日期的格式
     *
     * @param dateStr 日期字符串
     * @return 秒
     */
    public static String getDateFormat(String dateStr) {
        String style = null;
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        boolean b = false;
        for (int i = 0; i < regularExp.length; i++) {
            b = dateStr.matches(regularExp[i][0]);
            if (b) {
                style = regularExp[i][1];
            }
        }
        if (StringUtils.isBlank(style)) {
            log.info("date_str:" + dateStr);
            log.info("日期格式获取出错，未识别的日期格式");
        }
        return style;
    }

    /**
     * 转换为时间类型格式
     *
     * @param strDate 日期
     * @return Date
     */
    public static Date strToDate(String strDate) {
        try {
            String strType = getDateFormat(strDate);
            SimpleDateFormat sf = new SimpleDateFormat(strType);
            return new Date((sf.parse(strDate).getTime()));
        } catch (Exception e) {
            return null;
        }
    }

    public static long strToTime(String timeStr) {
        Date time = strToDate(timeStr);
        return time.getTime() / 1000;
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 根据时间，获取当前日期的开始日期、结束日期
     *
     * @param dt 时间
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEndByDate(Date dt) {
        LocalDate date = new LocalDate(dt);

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 根据时间，获取月开始日期、结束日期
     *
     * @param month 月 0本月，-1上月，-2上上月，1下月，2下下月
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getMonthStartAndEnd(int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, month);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        Date date1 = c.getTime();
        c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, month);

        //设置为当月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE, 59);
        //将秒至59
        c.set(Calendar.SECOND, 59);
        //将毫秒至999
        c.set(Calendar.MILLISECOND, 999);
        Date date2 = c.getTime();
        return new Date[]{date1, date2};
    }

    /**
     * 根据时间，获取月开始日期、结束日期
     *
     * @param date date
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getMonthStartAndEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        Date date1 = c.getTime();
        c = Calendar.getInstance();
        c.setTime(date);

        //设置为当月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE, 59);
        //将秒至59
        c.set(Calendar.SECOND, 59);
        //将毫秒至999
        c.set(Calendar.MILLISECOND, 999);
        Date date2 = c.getTime();
        return new Date[]{date1, date2};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * 获取本月、当日的开始结束
     *
     * @return
     */
    public static Map<String, Long> getTime() {
        Map<String, Long> time = new HashMap<>(8);
        //当前开始
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        time.put("CUR_DAY_START", calendar.getTime().getTime());
        //当天结束
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        time.put("CUR_DAY_END", calendar.getTime().getTime());
        //当月开始
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        time.put("CUR_MONTH_START", calendar.getTime().getTime());
        //本月结束
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        time.put("CUR_MONTH_END", calendar.getTime().getTime());
        return time;
    }

    /**
     * 当前时间的时间戳（秒）
     *
     * @return 当前时间秒数
     * @since 4.0.0
     */
    public static long currentSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
