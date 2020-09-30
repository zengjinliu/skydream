package comskydream.cn.skydream.utils;

import org.apache.tomcat.jni.Local;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jayson
 * @date 2020/9/5  16:57
 */
public class DateUtils {

    /**
     * 当前时间增加或者减少几分钟
     * @param time
     * @return
     */
    public static LocalDateTime add(long time){
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(time);
        return localDateTime;
    }

    /**
     * 获取时间的毫秒数
     * @param localDateTime
     * @return
     */
    public static long getMillis(LocalDateTime localDateTime){
        long l = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return l;
    }
    /**
     * 获取时间的毫秒数
     * @param localDateTime
     * @return
     */
    public static long getSecond(LocalDateTime localDateTime){
        Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("+8"));
        return instant.getEpochSecond();
    }

    /**
     * localDate transfer Date
     * @param localDate
     * @return Date
     */
    public static Date localDate2Date(LocalDate localDate){
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     *  Date transfer localDate
     * @param date
     * @return LocalDate
     */
    public static LocalDate date2LocalDate(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    /**
     * Date transfer LocalDateTime
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date){
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    /**
     * Date transfer LocalDateTime
     * @param localDateTime
     * @return LocalDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime){
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     *  dateStr must adjust to pattern transfer to LocalDate
     * @param dateStr 时间格式字符串
     * @param pattern 格式化 eq yyyy-MM-dd
     * @return
     */
    public static LocalDate str2LocalDate(String dateStr,String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

    /**
     *  dateStr must adjust to pattern transfer to LocalDate
     * @param dateStr 时间格式字符串
     * @param pattern 格式化 eq yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime str2LocalDateTime(String dateStr,String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        LocalDateTime parse = LocalDateTime.parse(dateStr, formatter);
        return parse;
    }

    public static LocalTime startOfDay(){
        return LocalTime.of(0,0,0);
    }

    public static LocalTime endOfTDay(){
        return LocalTime.of(23,59,59);
    }


    /**
     * 获取当月的开始时间
     * @return
     */
    public static LocalDateTime firstDateTime(){
        LocalDateTime localDateTime = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atTime(startOfDay());
        return localDateTime;
    }

    /**
     * 获取当前月的结束时间
     * @return
     */
    public static LocalDateTime endDateTime(){
        LocalDateTime localDateTime = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(endOfTDay());
        return localDateTime;
    }

    /**
     * 获取当前年指定月的开始时间
     * @param month 指定月
     * @return
     */
    public static LocalDateTime firstDateTime(int month){
        LocalDateTime atTime = LocalDate.now().withMonth(month).with(TemporalAdjusters.firstDayOfMonth()).atTime(startOfDay());
        return atTime;
    }

    /**
     * 获取当前时间当前周的开始时间
     * @return
     */
    public static LocalDateTime startWeekTime(){
        LocalDateTime dateTime = LocalDate.now().with(DayOfWeek.MONDAY).atTime(startOfDay());
        return dateTime;
    }

    /**
     * 获取当前时间当前周的结束时间
     * @return
     */
    public static LocalDateTime endWeekTime(){
        LocalDateTime dateTime = LocalDate.now().with(DayOfWeek.SUNDAY).atTime(startOfDay());
        return dateTime;
    }

}
