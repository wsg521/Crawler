package com.open.crawler.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期时间帮助类
 *
 */
public class DateTimeUtils {
    public static final String DATE_PATTEN1 = "yyyy-MM-dd";
    public static final String DATE_PATTEN2 = "yyyyMMdd";
    public static final String DATE_PATTEN3 = "yy/MM/dd";
    public static final String DATE_PATTEN4 = "MM月dd日";
    public static final String DATE_PATTEN5 = "yyyy-MM";

    public static final String TIME_PATTEN1 = "HH:mm:ss";
    public static final String TIME_PATTEN2 = "HHmmss";

    public static final String DATETIME_PATTEN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_PATTEN2 = "yyyyMMddHHmmss";
    public static final String DATETIME_PATTEN3 = "MM月dd日HH时mm分";

    private static final String CLASS_NAME = "DateTimeUtils";

    DateTimeUtils() {

    }

    public static Date floorDate(Date date) {
        return DateUtils.truncate(date, Calendar.SECOND);
    }

    public static Date getDayStart(int day) {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DATE, day);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();
    }

    public static Date getDayStart(Date day, int offset) {
        Calendar start = Calendar.getInstance();
        start.setTime(day);
        start.add(Calendar.DATE, offset);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();
    }

    public static Date getDayEnd(int day) {
        Calendar end = Calendar.getInstance();
        end.add(Calendar.DATE, day);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);
        return end.getTime();
    }

    public static Date getMonthStart(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthEnd(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getYesterdayStart() {
        return getDayStart(-1);
    }

    public static Date getYesterdayEnd() {
        return getDayEnd(-1);
    }

    public static Date getHoursLater(int hours) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, hours);
        return date.getTime();
    }

    public static Date getSecondsAfter(Date inputDate, int seconds) {
        Calendar date = Calendar.getInstance();
        date.setTime(inputDate);
        date.add(Calendar.SECOND, seconds);
        return date.getTime();
    }

    public static Date parseDate(String dateStr, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }

    public static Date parseDate(String dateStr) throws ParseException {
        Date lDate = null;
        try {
            lDate = parseDate(dateStr, DATETIME_PATTEN1);
        } catch (ParseException e) {
            lDate = parseDate(dateStr, DATE_PATTEN1);
        }
        return lDate;
    }

    public static boolean isSameDay(Date dateOne, Date dateTwo) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(dateOne);
        cal2.setTime(dateTwo);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static String formatYMD(Date date) {
        return DateFormatUtils.format(date, DATE_PATTEN1);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(true);
        return sdf.format(date);
    }

    public static Date parse(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse(String date, String pattern1, String pattern2) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern1);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            sdf = new SimpleDateFormat(pattern2);
            try {
                return sdf.parse(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    public static Date parseFromYMD(String dateStr) {
        return parse(dateStr, DATE_PATTEN1);
    }

    /**
     * 日期检查, yyyy-mm-dd
     *
     * @param pInput
     *            要检查的字符串
     * @return boolean 检查结果
     */
    public static boolean isDate(String pInput) {
        if (StringUtils.isEmpty(pInput)) {
            return false;
        }
        String regEx = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }

    /**
     * XMLGregorianCalendar类型和Date类型之间的相互转换
     *
     * @author Xin 2010-06-12
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }

    /**
     *
     * @param cal
     * @return
     * @throws Exception
     */
    public static Date convertToDate(XMLGregorianCalendar cal) {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    /**
     * 获取当前日期所在月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 1 - index);
        return calendar.getTime();
    }

    /**
     * 获取当前日期所在月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -index);
        return calendar.getTime();
    }

    /*
     * 获取两个日期之间的相差时间间隔
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        long milliSecondsGap = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        return (int) (milliSecondsGap / (1000 * 3600 * 24));
    }
}
