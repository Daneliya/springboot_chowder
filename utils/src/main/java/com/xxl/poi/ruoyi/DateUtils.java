package com.xxl.poi.ruoyi;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        SimpleDateFormat dateformat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date endOfDate = calendar1.getTime();
        try {
            return dateformat1.parse(dateformat1.format(endOfDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 獲取传入时间后多少周的时间戳
     *
     * @param datestr
     * @param value
     * @return
     */
    public static Date getChangeDate(String datestr, int value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(datestr.trim());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.add(Calendar.WEEK_OF_MONTH, value);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 獲取传入时间后多少周的时间戳
     *
     * @param datestr
     * @return
     */
    public static Date getDateChange(Date datestr, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datestr);
        if (value != 0) {
            calendar.add(Calendar.WEEK_OF_MONTH, value);
        }
        return calendar.getTime();
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author xxl
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断某个时间段在某个区间内
     *
     * @param nowStartTime
     * @param nowEndTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isStartCompareEnd(Date nowStartTime, Date nowEndTime, Date startTime, Date endTime) {
        if (nowStartTime.getTime() >= startTime.getTime() && nowEndTime.getTime() <= endTime.getTime()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 计算两个时间段内有多少周
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int until(String startTime, String endTime) {
        int week = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = format.parse(startTime.trim());
            Date endDate = format.parse(endTime.trim());
            //处理开始时间
            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //处理结束时间
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //天数
            week = (int) startLocalDate.until(endLocalDate, ChronoUnit.DAYS) / 7;
            return week;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return week;
    }

    public static int until(Date startTime, Date endTime) {
        //处理开始时间
        LocalDate startLocalDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //处理结束时间
        LocalDate endLocalDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //天数
        int week = (int) startLocalDate.until(endLocalDate, ChronoUnit.DAYS) / 7;
        return week;
    }

    /**
     * 计算两个日期差多少天
     * 同年
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int daydiff(Date startDate, Date endDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(startDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(endDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;

    }

    /**
     * 计算两个日期差多少小时
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long differHour(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long hours = diff / 60 / 60 / 1000;
        return hours;
    }

    /**
     * date2比date1多的天数
     * 跨年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) { //同一年{
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) { //闰年{
                    timeDistance += 366;
                    //不是闰年{
                } else {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
            //不同年
        } else {
            return day2 - day1;
        }
    }

    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        // 获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        // 定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取某一年的所有周的开始时间和结束时间
     *
     * @param week
     * @return
     */
    public static String[] getWeekDays(Integer week) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf1.format(date);
        // 使用2021年
        cal.set(Calendar.YEAR, Integer.valueOf(year) - 1);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        String beginDate = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        String endDate = sdf.format(cal.getTime());
        return new String[]{beginDate, endDate};
    }

    /**
     * 获取某一年的最大周数
     *
     * @param date
     * @return
     */
    public static int getWeekCount(Date date) {
        // 获取日历类实例
        Calendar calendar = Calendar.getInstance();
        // 设置时间
        calendar.setTime(date);
        // 设置周一为每周第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 返回当年最大周数
        return calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前周数
     *
     * @param date
     * @return
     */
    public static int getWeekYear(Date date) {
        // 获取日历类实例
        Calendar calendar = Calendar.getInstance();
        // 设置时间
        calendar.setTime(date);
        // 设置周一为每周第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 返回当年最大周数
        return calendar.getWeekYear();
    }

    /**
     * 处理组装时间
     *
     * @param startYearTime
     * @param endDate
     * @return
     */
    public static Date assemblyTime(String startYearTime, String endDate) {
        StringBuffer str = new StringBuffer();
        str.append(startYearTime).append(" ").append(endDate);
        return DateUtil.parse(str.toString());
    }

    /**
     * 获取本周的开始时间
     *
     * @return
     */
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }


    /**
     * 获取本周结束时间
     *
     * @return
     */
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    /**
     * 未来7天
     *
     * @return
     */
    public static List<String> getSevenDays(Date time) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i < 8; i++) {
            Date date = org.apache.commons.lang3.time.DateUtils.addDays(time, i);
            String formatDate = sdf.format(date);
            dateList.add(formatDate);
        }
        return dateList;
    }
}
