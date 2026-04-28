package cn.hoxise.common.base.utils.date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * DateUtil 时间工具
 *
 * @author hoxise
 * @since 2026/01/14 06:50:38
 */
public class DateUtil {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH_DAY);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

    private static final String[] FORMATTERS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyyMMdd", "yyyyMMddHHmmss",
    };

    /**
     * 时间戳转LocalDateTime
     *
     * @param timestampMillis 时间戳
     * @return LocalDateTime
     * @author hoxise
     * @since 2026/01/14 06:50:49
     */
    public static LocalDateTime ofInstant(long timestampMillis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
    }

    /**
     * 时间字符串转LocalDate
     *
     * @param dateStr  yyyy-MM-dd 日期字符串
     * @return LocalDateTime
     * @author hoxise
     * @since 2026/01/14 06:51:04
     */
    public static LocalDateTime handleDateStr(String dateStr){
        if (StrUtil.isBlank(dateStr)){
            return null;
        }
        return LocalDate.parse(dateStr, DateUtil.DATE_FORMATTER).atStartOfDay();
    }

    /**
     * 格式化为Date类型 兼容多种格式
     *
     * @param dateStr 日期字符串
     * @author hoxise
     * @since 2026/04/15 10:02:06
     */
    public static DateTime parseDateTime(String dateStr){
        if (StrUtil.isBlank(dateStr)){
            return null;
        }
        return cn.hutool.core.date.DateUtil.parse(dateStr, FORMATTERS);
    }
}
