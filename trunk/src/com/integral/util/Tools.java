package com.integral.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.quartz.SchedulerException;

import com.integral.system.message.bean.SystemMessage;
import com.integral.util.dwr.MessageSender;
import com.integral.util.spring.quartz.DynamicJobSchedule;

public class Tools {
    private static DynamicJobSchedule dynamicJobSchedule;
    
    public DynamicJobSchedule getDynamicJobSchedule() {
        return dynamicJobSchedule;
    }

    public void setDynamicJobSchedule(DynamicJobSchedule dynamicJobSchedule) {
        this.dynamicJobSchedule = dynamicJobSchedule;
    }

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
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        leapYear = c.isLeapYear(cal.get(Calendar.YEAR));
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
    /**
     * <p>Discription:[获取两个日期相隔天数]</p>
     * @param from
     * @param to
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getDaysBetweenDates(Date from, Date to){
        if(from.after(to)){
            Date swap = from;
            from = to;
            to = swap;
        }
        BigDecimal bd = new BigDecimal(getTimeFromTwoDate(from, to));
        return bd.divide(new BigDecimal(""+ 24 * 3600 * 1000), RoundingMode.DOWN).intValue();
    }
    /**
     * <p>Discription:[获取某天所在周的星期几的日期]</p>
     * @param date
     * @param week 星期(0 ~ 6), 0: SUN, 1: MON,... 6: SAT
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Date getWeekDate(Date date, int week){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch(week){
            case 0:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
            case 1:
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case 2:
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case 3:
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case 4:
                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case 5:
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case 6:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            default:
                break;
        }
        return c.getTime();
    }
    /**
     * <p>Discription:[获取某日期所在星期几]</p>
     * @param date
     * @return 0 ~ 6 (0: SUN, 1: MON, 6: SAT)
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getWeekFromDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }
    /**
     * <p>Discription:[获取某日期所在周]</p>
     * @param date
     * @return 0 ~ 52
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getWeekNumFromDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR) - 1;
    }
    /**
     * <p>Discription:[获取相对于指定日期的上/下几周的星期几的日期]</p>
     * @param date
     * @param week 相对于date所在周的上/下几周(-1：上一周，-2：上两周，1：下一周，2：下两周)
     * @param day 星期几(0 ~ 6) 0: SUN, 1: MON, 6: SAT
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Date getDateFromWeek(Date date, int week, int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, week);
        switch(day){
            case 0:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
            case 1:
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case 2:
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case 3:
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case 4:
                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case 5:
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case 6:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            default:
                break;
        }
        return c.getTime();
    }
    
    /**
     * <p>Discription:[判断某日期是否周末]</p>
     * @param date
     * @return true: 周末, false: 非周末
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static boolean isWeekendDay(Date date){
        if(getWeekFromDate(date) == 0 || getWeekFromDate(date) == 6){
            return true;
        }else{
            return false;
        }
    }
    /**
     * <p>Discription:[某日期加几天]</p>
     * @param date
     * @param day
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Date addDayToDate(Date date, int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }
    /**
     * <p>Discription:[获取两个日期间的工作日(包括国定假日)的天数]</p>
     * @param from
     * @param to
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getWorkDayBetweenDates(Date from, Date to){
        if(from.after(to)){
            Date swap = from;
            from = to;
            to = swap;
        }
        int dates = 0;
        //开始日期所在周的周末天数
        int startWeekend = 0;
        //结束日期所在周的周末天数
        int endWeekend = 0;
        //判断两个日期是否是周末
        if(isWeekendDay(from)){
            //如果是周末，则从下周星期天开始(星期天为第一天)
            Date temp = from;
            from = getDateFromWeek(from, 1, 0);
            startWeekend = getDaysBetweenDates(temp, from);
        }else{
            //不是周末，开始日期到周末的工作日天数
            Date temp = from;
            Date temp1 = getDateFromWeek(from, 0, 6);
            dates += getDaysBetweenDates(temp, temp1);
            //从本周星期天星期天开始(下周第一天)
            from = getDateFromWeek(from, 1, 0);
        }
        if(isWeekendDay(to)){
            Date temp = to;
            //如果是周末，则计算截止到上周六(周六为最后一天)
            to = getDateFromWeek(to, -1, 6);
            endWeekend = getDaysBetweenDates(temp, to);
        }else{
            //不是周末，周末到结束日期的工作日天数
            Date temp = to;
            Date temp1 = getDateFromWeek(to, 0, 1);
            dates += getDaysBetweenDates(temp, temp1);
            //到上周星期天结束(本周第一天)
            to = getDateFromWeek(to, 0, 0);
        }
        //剩下的天数实际上是周的倍数
        int workDate = getDaysBetweenDates(from, to) / 7 * 5;
        dates = dates + workDate - startWeekend - endWeekend;
        return dates;
    }
    /**
     * <p>Discription:[计算从from日期之后days天之后的日期(如果遇到周末/节假日则往后延周末的天数)]</p>
     * @param from
     * @param days N天后
     * @param Date[] holidays: 节假日
     * @return N天后非周末日期
     * @author:[代超]
     * @throws ParseException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Date getDateAfterDays(Date from, int days, Date[] holidays) throws ParseException{
        Date end = from;
        int i=0;
        while(i < days){
            end = addDayToDate(end, 1);
            if(holidays != null){
                for(Date date : holidays){
                    if(end.compareTo(date) == 0){
                        end = addDayToDate(end, 1);
                    }
                }
            }
            if(!isWeekendDay(end)){
                i++;
            }
        }
        return end;
    }
    
    public static void iLoveBaby() throws ParseException{
        Date today = StringToDate(dateToString(new Date(),"yyyy-MM-dd"));
        Date to = StringToDate("2012-02-29");
        Date from = StringToDate("2011-09-20");
        String msg = "I love Baby : " + getDaysBetweenDates(from, today);
        msg += "<br/>company time : " + getDaysBetweenDates(today, to);
        msg += "<br/>本条信息由系统自动发送，无法查看！";
        MessageSender sender = new MessageSender();
        SystemMessage message = new SystemMessage();
        message.setMessageContent(msg);
        message.setMessageSendTime(new Date());
        sender.sendMessageWithPage("swpigris81", message);
        
        
        //以下代码仅用于测试动态设置quartz
        String newCorn = "0 0/1 10-23 * * ?";
        DynamicJobSchedule sc = new DynamicJobSchedule();
        try {
            dynamicJobSchedule.dynamicSchedule(newCorn);
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException {
        Date today = StringToDate(dateToString(new Date(),"yyyy-MM-dd"));
        Date to = StringToDate("2012-02-29");
        Date from = StringToDate("2011-09-20");
        System.out.println("I love baby : "+getDaysBetweenDates(from, today));
        
        System.out.println("company time : "+getDaysBetweenDates(today, to));
        
        //System.out.println(getDateAfterDays(today, 15));
        //System.out.println(getDateAfterDays(today, 15));
        //Date newYear = StringToDate("2012-01-01");
        //Date newYear2 = StringToDate("2012-01-04");
        //Date hd[] = new Date[]{newYear, newYear2};
        //System.out.println(getDateAfterDays(today, 28, hd));
        //System.out.println(getDateAfterDays(to, 13));
    }
}
