package cn.hoxise.common.base.utils.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author hoxise
 * @Description: 时间工具类
 * @Date 2025-08-12 下午11:51
 */
public class DateUtil {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH_DAY);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

    /**
     * @Author: hoxise
     * @Description: 时间戳转LocalDateTime
     * @Date: 2025/8/13 上午1:14
     */
    public static LocalDateTime ofInstant(long timestampMillis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.of(TIME_ZONE_DEFAULT));
    }

    /**
     * @Author: hoxise
     * @Description: 时间字符串转LocalDate
     * @param dateStr 日期字符串 不带时间
     * @Date: 2025/8/13 上午1:14
     */
    public static LocalDateTime handleDateStr(String dateStr){
        try{
            return LocalDate.parse(dateStr, DateUtil.DATE_FORMATTER).atStartOfDay();
        }catch (Exception e){
            return null;
        }
    }
}
