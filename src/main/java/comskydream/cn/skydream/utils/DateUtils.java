package comskydream.cn.skydream.utils;

import java.time.*;
import java.time.temporal.TemporalField;

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


}
