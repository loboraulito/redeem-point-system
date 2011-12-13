package com.integral.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Tools {
    /**
     * 将String型日期换为Date型日期
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        if ("".equals(date) || date == null) {
            return null;
        }
        return df.parse(date);
    }

    /**
     * 将String型日期换为Date型日期 默认：yyyy-MM-dd
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String date, String fomart)
            throws ParseException {
        if (fomart == null || "".equals(fomart)) {
            fomart = "yyyy-MM-dd";
        }
        DateFormat df = new SimpleDateFormat(fomart);
        df.setLenient(false);
        if ("".equals(date) || date == null) {
            return null;
        }
        return df.parse(date);
    }

    /**
     * 将String型日期换为Date型日期 yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDate2(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setLenient(false);
        if ("".equals(date) || date == null) {
            return null;
        }
        return df.parse(date);
    }

    /**
     * 将String型日期换为Date型日期 yyyy-MM-dd HH:mm
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDate3(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df.setLenient(false);
        if ("".equals(date) || date == null) {
            return null;
        }
        return df.parse(date);
    }

    /**
     * 将Long型的date日期转换为String型的yyyy-MM-dd型日期
     * 
     * @param date
     * @return
     */
    public static String LongToDate(Long date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        Date d = new Date(date);
        String dateTime = df.format(d);
        return dateTime;
    }

    /**
     * 将Long型的date日期转换为String型的yyyy-MM-dd HH:mm:ss型日期
     * 
     * @author swpigris81@126.com Description:
     * @param date
     * @return
     */
    public static String LongToDate2(Long date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setLenient(false);
        Date d = new Date(date);
        String dateTime = df.format(d);
        return dateTime;
    }

    /**
     * 获取当前时间的Long型
     * 
     * @return
     */
    public static String getNowTime() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    /**
     * Date型数据转换为String数据 格式yyyy-mm-dd
     * 
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * Date型数据转换为String数据，格式自定义，默认为yyyy-MM-dd
     * 
     * @author swpigris81@126.com Description:
     * @param date
     * @param formater
     * @return
     */
    public static String dateToString(Date date, String formater) {
        if (formater == null || "".equals(formater)) {
            formater = "yyyy-MM-dd";
        }
        Format formatter = new SimpleDateFormat(formater);
        return formatter.format(date);
    }

    /**
     * Date型数据转换为String数据 格式：yyyymmddHHmmss
     * 
     * @param date
     * @return
     */
    public static String dateToString2(Date date) {
        Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }

    /**
     * Date型数据转换为String数据 格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String dateToString3(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * Date型数据转换为String数据 格式：yyyy-MM-dd HH:mm
     * 
     * @param date
     * @return
     */
    public static String dateToString4(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }

    /**
     * Created on 2008-12-25
     * <p>
     * Discription:[获取当前时间]
     * </p>
     * 
     * @author:[代超]
     * @update:[日期2008-12-25] [代超]
     * @return Date .
     */
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 将String型的日期yyyy-MM-dd转换为Long型日期
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Long dateToLong(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        return df.parse(date).getTime();
    }

    /**
     * 获取两个日期之间的时间差，精确到毫秒。
     * 
     * @author swpigris81@126.com Description:
     * @param from：开始时间
     * @param to：结束时间
     * @return
     */
    public static Long getTimeFromTwoDate(Date from, Date to) {
        if (from == null || "".equals(from) || to == null || "".equals(to)) {
            return 0L;
        }
        BigDecimal fromDate = new BigDecimal("" + from.getTime());
        BigDecimal toDate = new BigDecimal("" + to.getTime());
        return toDate.add(fromDate.negate()).longValue();
    }

    /**
     * 判断指定日期是否是闰年
     * 
     * @author swpigris81@126.com Description:
     * @param date
     * @return
     */
    public static boolean isLeapYear(Date date) {
        boolean leapYear = false;
        GregorianCalendar c = new GregorianCalendar();
        leapYear = c.isLeapYear(date.getYear());
        return leapYear;
    }

    /**
     * 获取系统中的唯一值
     * 
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 获取指定月份的最后一天
     * 
     * @author swpigris81@126.com Description:
     * @param month:指定的日期：yyyy-MM-dd
     * @param fomart:返回的日期的格式，默认：yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date getLastDateOfMonth(Date month, String fomart)
            throws ParseException {
        if (fomart == null || "".equals(fomart.trim())) {
            fomart = "yyyy-MM-dd";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);

        int maxInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String lastDate = dateToString(month, "yyyy-MM") + "-" + maxInMonth;

        return StringToDate(lastDate, fomart);
    }
    /**
     * 校验日期是否正常
     * @author swpigris81@126.com
     * Description:
     * @param date
     * @return
     */
    public static boolean checkDate(Date date) {
        boolean bool = true;
        try {
            Calendar cl = Calendar.getInstance();
            cl.setLenient(false);
            cl.setTime(date);
        } catch (Exception e) {
            bool = false;
        }
        return bool;
    }
    /**
     * 校验日期是否正常
     * @author swpigris81@126.com
     * Description:
     * @param date
     * @return
     */
    public static boolean checkDate(Object date) {
        boolean bool = true;
        try {
            if(date instanceof Date){
                return checkDate((Date) date);
            }
            Date d = StringToDate(String.valueOf(date));
            Calendar cl = Calendar.getInstance();
            cl.setLenient(false);
            cl.setTime(d);
        } catch (Exception e) {
            bool = false;
        }
        return bool;
    }

    public static void main(String[] args) throws ParseException {
        Date d = new Date();
        System.out.println(dateToString2(d));
        // GregorianCalendar c = new GregorianCalendar();
        // c.setTime(d);
        // c.setGregorianChange(d);
        //System.out.println(dateToString(getLastDateOfMonth(d, null)));
        System.out.println(checkDate(("2011-02-29")));
    }
}
