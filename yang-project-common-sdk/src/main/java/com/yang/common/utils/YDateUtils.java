
package com.yang.common.utils;

import cn.hutool.core.date.DateUtil;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class YDateUtils extends DateUtil implements DatePattern {

    /**
     * 判断目标时间是否在当前时间之后
     *
     * @param time 目标时间
     * @return 是否之后
     */
    public static boolean isAfter(Long time) {
        return isAfter(time, System.currentTimeMillis());
    }

    /**
     * 判断目标时间是否在当前时间之前
     *
     * @param time 目标时间
     * @return 是否之前
     */
    public static boolean isBefore(Long time) {
        return isBefore(time, System.currentTimeMillis());
    }

    /**
     * 判断目标时间是否在某时间之后
     *
     * @param time       某时间
     * @param targetTime 目标时间
     * @return 是否之后
     */
    public static boolean isAfter(Long time, Long targetTime) {
        if (null == time || null == targetTime) {
            return false;
        }
        long now = System.currentTimeMillis();
        return time > targetTime;
    }

    /**
     * 判断目标时间是否在某时间之前
     *
     * @param time       某时间
     * @param targetTime 目标时间
     * @return 是否之前
     */
    public static boolean isBefore(Long time, Long targetTime) {
        if (null == time || null == targetTime) {
            return false;
        }
        long now = System.currentTimeMillis();
        return time <= targetTime;
    }

    /**
     * 匹配开始-进行中-结束状态中文名称
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 状态中文名称
     */
    public static String getStartEndStatusName(Date startTime, Date endTime) {
        return getStartEndStatusName("", startTime, endTime);
    }


    /**
     * 匹配开始-进行中-结束状态中文名称
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 状态中文名称
     */
    public static String getStartEndStatusName(Long startTime, Long endTime) {
        return getStartEndStatusName("", startTime, endTime);
    }

    /**
     * 匹配开始-进行中-结束状态中文名称
     *
     * @param namePrefix 状态前缀，如会议未开始，会议已结束
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 状态中文名称
     */
    public static String getStartEndStatusName(String namePrefix, Date startTime, Date endTime) {
        if (YStrUtils.isAnyNull(startTime, endTime)) {
            return namePrefix + "已结束";
        }
        return getStartEndStatusName(startTime.getTime(), endTime.getTime());
    }

    /**
     * 匹配开始-进行中-结束状态中文名称
     *
     * @param namePrefix 状态前缀，如会议未开始，会议已结束
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 状态中文名称
     */
    public static String getStartEndStatusName(String namePrefix, Long startTime, Long endTime) {
        if (YStrUtils.isAnyNull(startTime, endTime)) {
            return namePrefix + "已结束";
        }
        long now = System.currentTimeMillis();
        if (now <= startTime) { // 当前时间小于开始时间
            return namePrefix + "未开始";
        } else if (now < endTime) { // 当前时间大于开始时间，小于结束时间
            return namePrefix + "进行中";
        } else if (now >= endTime) { // 当前时间大于等于结束时间
            return namePrefix + "已结束";
        } else {
            return namePrefix + "已结束";
        }
    }

    /**
     * 根据年龄设置时间查询区间
     *
     * @param startAge     开始年龄
     * @param endAge       结束年龄
     * @param setStartTime 开始条件函数
     * @param setEndTime   结束条件函数
     */
    public static void setStartEndAge(Integer startAge, Integer endAge, Consumer<Long> setStartTime,
                                      Consumer<Long> setEndTime) {
        if (YStrUtils.isAllNotNull(startAge, endAge)) {
            setStartTime.accept(computerStartTime(endAge + 1));
            setEndTime.accept(computerEndTime(startAge));
        }
    }

    /**
     * 获取某天的开始date
     *
     * @param year  年
     * @param month 月
     * @param day   天
     * @return 开始日期
     */
    public static Date getFirstDayOfYearMonth(Integer year, Integer month, Integer day) {
        return getStartEndDate(year, month, day, true);
    }

    /**
     * 获取某天的开始date
     *
     * @param year  年
     * @param month 月
     * @return 开始日期
     */
    public static Date getFirstDayOfYearMonth(Integer year, Integer month) {
        return getStartEndDate(year, month, null, true);
    }

    /**
     * 获取某天的结束date
     *
     * @param year  年
     * @param month 月
     * @return 结束日期
     */
    public static Date getLastDayOfYearMonth(Integer year, Integer month) {
        return getStartEndDate(year, month, null, false);
    }

    /**
     * 获取某天的结束date
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 结束日期
     */
    public static Date getLastDayOfYearMonth(Integer year, Integer month, Integer day) {
        return getStartEndDate(year, month, day, false);
    }

    /**
     * 设置开始结束查询条件
     *
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param setStartTime 开始时间设置函数
     * @param setEndTime   结束时间设置函数
     */
    public static void setQueryTime(Long startTime, Long endTime, Consumer<Long> setStartTime,
                                    Consumer<Long> setEndTime) {
        if (null == startTime && null == endTime) {
            return;
        } else if (null != startTime && null == endTime) {
            startTime = getStartTimeOfCurrentDay(new Date(startTime)).getTime();
            endTime = System.currentTimeMillis();
        } else if (null == startTime) {
            startTime = 0L;
            endTime = getEndTimeOfCurrentDay(new Date(endTime)).getTime();
        } else {
            startTime = getStartTimeOfCurrentDay(new Date(startTime)).getTime();
            endTime = getEndTimeOfCurrentDay(new Date(endTime)).getTime();
        }
        setStartTime.accept(startTime);
        setEndTime.accept(endTime);
    }

    /**
     * 获取本月第一天毫秒数
     *
     * @return 最后一天
     */
    public static long getMonthStartDay() {
        LocalDate date = LocalDate.now();
        LocalDate with1 = date.with(TemporalAdjusters.firstDayOfMonth());
        ZonedDateTime atStartOfDay = with1.atStartOfDay(ZoneId.systemDefault());
        Instant instant = atStartOfDay.toInstant();
        Date from = Date.from(instant);
        return from.getTime();
    }

    /**
     * 获取本月最后一天毫秒数
     *
     * @return 最后一天
     */
    public static long getMonthEndDay() {
        LocalDate date = LocalDate.now();
        LocalDate with2 = date.with(TemporalAdjusters.lastDayOfMonth());
        ZonedDateTime atStartOfDay = with2.atStartOfDay(ZoneId.systemDefault());
        Instant instant = atStartOfDay.toInstant();
        Date from = Date.from(instant);
        long time = from.getTime();
        long oneDay = (24 * 60 * 60 * 1000) - 1;
        time += oneDay;
        return time;
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 表达式
     * @return 年龄
     */
    public static String formart(Long date, String pattern) {
        if (null == date) {
            return "";
        }
        return formart(new Date(date), pattern);
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 表达式
     * @return 年龄
     */
    public static String formart(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据日期计算年龄
     *
     * @param birthday 日期
     * @return 年龄
     */
    public static Integer getAgeByBirth(String birthday) {
        Date birth = toDate(birthday, "yyyy-MM-dd");
        return getAgeByBirth(birth);
    }

    /**
     * 根据日期计算年龄
     *
     * @param birthday 日期
     * @return 年龄
     */
    public static Integer getAgeByBirth(Long birthday) {
        if (null != birthday && 0 != birthday) {
            try {
                return getAgeByBirth(new Date(birthday));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 根据日期计算年龄
     *
     * @param birth 日期
     * @return 年龄
     */
    public static Integer getAgeByBirth(Date birth) {
        if (null == birth) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        int yearNow = instance.get(Calendar.YEAR);
        int monthNow = instance.get(Calendar.MONTH) + 1;

        Calendar instanceBirth = Calendar.getInstance();
        instanceBirth.setTime(birth);
        int yearBirth = instanceBirth.get(Calendar.YEAR);
        int monthBirth = instanceBirth.get(Calendar.MONTH) + 1;
        int age = yearNow - yearBirth;
        // 判断是否足月
        if (monthNow > monthBirth) {
            age = age + 1;
        }
        return Math.min(age, 150);
    }

    /**
     * 修改日期元素
     *
     * @param date   日期
     * @param hour   时
     * @param min    分
     * @param second 秒
     * @return 日期
     */
    public static Date setTimes(Date date, int hour, int min, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        cal.add(Calendar.MINUTE, min);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();

    }

    /**
     * 修改日期的天
     *
     * @param date 日期
     * @param days 天数
     * @return 日期
     */
    public static Date getDateFromNow(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();

    }

    /**
     * 年份天转日期
     *
     * @param days 天数
     * @return 日期
     */
    public static Date getDateFromNow(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();

    }

    /**
     * 字符转日期，处理下异常
     *
     * @param date 日期
     * @param sdf  转换器
     * @return 日期
     */
    public static Date toDate(String date, SimpleDateFormat sdf) {
        Date parse;
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
        return parse;
    }

    /**
     * 通用字符转日期，处理下异常
     *
     * @param date 日期
     * @return 日期
     */
    public static Long toDataLong(String date) {
        Date d = toDate(date);
        return null != d ? d.getTime() : null;
    }

    /**
     * 通用字符转日期，处理下异常
     *
     * @param date 日期
     * @return 日期
     */
    public static Date toDate(String date) {// 13707490327
        if (YStrUtils.isNull(date)) {
            return null;
        }
        Date parse = toDate(date, "yyyy-MM-dd HH:mm:ss");
        if (null == parse) {
            parse = toDate(date, "yyyy-MM-dd");
        }
        if (null == parse) {
            parse = toDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
        }
        if (null == parse) {
            parse = toDate(date, "yyyyMMdd");
        }
        if (null == parse) {
            parse = toDate(date, "yyyyMMddHHmmss");
        }
        if (null == parse) {
            parse = toDate(date, "MM/dd/yyyy");
        }
        if (null == parse) {
            parse = toDate(date, "yyyy.MM");
        }
        if (null == parse) {
            parse = toDate(date, "yyyy-MM");
        }
        if (null == parse) {
            parse = toDate(date, "yyyy");
        }
        if (null == parse && date.length() == 13) {
            try {
                long parseLong = Long.parseLong(date);
                parse = new Date(parseLong);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return parse;
    }

    /**
     * 字符转日期，处理下异常
     *
     * @param date   日期
     * @param patten 表达式
     * @return 日期
     */
    public static Date toDate(String date, String patten) {// 13707490327
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        Date parse;
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
        return parse;
    }

    /**
     * 获取本天天的最开始的一刻，某天最小
     *
     * @return 本天最小日期
     */
    public static long getStartLongTimeOfCurrentDay() {
        return getStartTimeOfCurrentDay().getTime();
    }

    /**
     * 获取本天天的最开始的一刻，某天最小
     *
     * @return 本天最小日期
     */
    public static Date getStartTimeOfCurrentDay() {
        return getStartTimeOfCurrentDay(new Date());
    }

    /**
     * 获取某一天的最开始的一刻，某天最小
     *
     * @param date 日期
     * @return 本天最小日期
     */
    public static long getStartLongTimeOfCurrentDay(Date date) {
        return getStartTimeOfCurrentDay(date).getTime();
    }

    /**
     * 获取某一天的最开始的一刻，某天最小
     *
     * @param date 日期
     * @return 本天最小日期
     */
    public static Date getStartTimeOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取今天的最大日期时决戮
     *
     * @return 今天 最大日期
     */
    public static long getEndLongTimeOfCurrentDay() {
        return getEndTimeOfCurrentDay().getTime();
    }

    /**
     * 获取今天的最大日期
     *
     * @return 今天 最大日期
     */
    public static Date getEndTimeOfCurrentDay() {
        return getEndTimeOfCurrentDay(new Date());
    }

    /**
     * 获取最大日期
     *
     * @param date 日期
     * @return 最大的long日期
     */
    public static Long getEndLongTimeOfCurrentDay(Date date) {
        if (null == date) {
            return null;
        }
        return getEndTimeOfCurrentDay(date).getTime();
    }

    /**
     * 获取某一天的最后一刻，某天最大的时间戮
     *
     * @param date 日期
     * @return 本天最大日期
     */
    public static Date getEndTimeOfCurrentDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * 获取当前季节
     *
     * @return 季节
     */
    public static int getQuarter() {
        return getQuarter(DateTime.now());
    }

    /**
     * 获取日期季节
     *
     * @param date 日期
     * @return 季节
     */
    public static Integer getQuarter(Long date) {
        return null == date ? null : getQuarter(new DateTime(date));
    }

    /**
     * 获取日期季节
     *
     * @param dateTime 日期
     * @return 季节
     */
    public static int getQuarter(DateTime dateTime) {
        int monthOfYear = dateTime.getMonthOfYear();
        if (monthOfYear <= 3) {
            return 1;
        }
        if (monthOfYear <= 6) {
            return 2;
        }
        if (monthOfYear <= 9) {
            return 3;
        }
        return 4;
    }

    /**
     * 获取当前月
     *
     * @return 当前月份
     */
    public static int getMonthValue() {
        LocalDate date = LocalDate.now();
        return date.getMonthValue();
    }

    /**
     * 获取当前年
     *
     * @return 当前年份
     */
    public static int getNowYear() {
        LocalDate date = LocalDate.now();
        return date.getYear();
    }

    /**
     * 日期转Long，处理下非空
     *
     * @param dateLong 时间戳
     * @return 日期
     */
    public static Long toLongDate(String dateLong) {
        if (YStrUtils.isNull(dateLong)) {
            return null;
        }
        Date date = toDate(dateLong);
        return null != date ? date.getTime() : null;
    }

    private static Date getStartEndDate(Integer year, Integer month, Integer day, boolean isGetForStart) {
        if (null == year || null == month) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);

        if (null == day) {
            int dayOfMonth = Calendar.DAY_OF_MONTH;
            day = isGetForStart ?
                    cal.getActualMinimum(dayOfMonth) : cal.getActualMaximum(dayOfMonth);
        }
        cal.set(Calendar.DAY_OF_MONTH, day);

        cal.set(Calendar.HOUR_OF_DAY, isGetForStart ? 0 : 23);

        cal.set(Calendar.MINUTE, isGetForStart ? 0 : 59);

        cal.set(Calendar.SECOND, isGetForStart ? 0 : 59);

        cal.set(Calendar.MILLISECOND, isGetForStart ? 0 : 999);

        return cal.getTime();
    }

    /**
     * 根据年龄计算出生年的开始时间
     *
     * @param endAge 年龄
     * @return 出生开始时间
     */
    private static Long computerStartTime(Integer endAge) {
        if (YStrUtils.isNull(endAge)) {
            return Long.MIN_VALUE;
        }
        DateTime now = new DateTime();
        int year = now.getYear();
        int month = now.getMonthOfYear();
        int day = now.getDayOfMonth();
        int endYear = year - endAge;
        Date date = getFirstDayOfYearMonth(endYear, month, day);
        return date.getTime();
    }

    /**
     * 根据年龄计算出生年的结束时间
     *
     * @param startAge 年龄
     * @return 出生结束时间
     */
    private static Long computerEndTime(Integer startAge) {// 40
        if (YStrUtils.isNull(startAge)) {
            return System.currentTimeMillis();
        }
        DateTime now = new DateTime();
        int year = now.getYear();
        int month = now.getMonthOfYear();
        int day = now.getDayOfMonth();
        int endYear = year - startAge;
        Date date = getLastDayOfYearMonth(endYear, month, day);
        return date.getTime();
    }
}
