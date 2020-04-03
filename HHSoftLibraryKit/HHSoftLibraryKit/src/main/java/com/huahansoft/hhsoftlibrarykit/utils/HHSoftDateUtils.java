package com.huahansoft.hhsoftlibrarykit.utils;


import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @类说明 HHSoftDateUtils
 * @作者 hhsoft
 * @创建日期 2019/8/21 10:58
 */
public class HHSoftDateUtils {
    /**
     * 当前时间戳
     *
     * @return
     */
    public static long currentTimestamp() {
        Date currentDate = new Date();
        return currentDate.getTime();
    }

    /**
     * 获取当前时间字符串
     *
     * @param outFormat 字符串输出时的格式
     * @return
     */
    public static String currentDateString(String outFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(outFormat);
            String dateStr = format.format(new Date());
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }
    }

    /**
     * 把时间字符串转换成Date
     *
     * @param dateString 要转换的字符串
     * @param inFormat   符串的格式，例如yyyy-MM-dd HH：mm:ss
     * @return 果转换成功返回转换以后的date，转换失败的话返回null
     */
    public static Date convertStringToDate(String dateString, String inFormat) {
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 把一个Date对象转换成相应格式的字符串
     *
     * @param date      时间
     * @param outFormat 输出的格式
     * @return 返回转换的字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertDateToString(Date date, String outFormat) {
        SimpleDateFormat format = new SimpleDateFormat(outFormat);
        return format.format(date);
    }

    /**
     * 把一个格式的时间字符串转换成时间戳
     *
     * @param dateString 原时间字符串
     * @param inFormat   原时间字符串的格式
     * @return 如果转换失败返回-1
     */
    public static long convertStringToTimestamp(String dateString, String inFormat) {
        Date date = convertStringToDate(dateString, inFormat);
        if (date != null) {
            return date.getTime();
        }
        return -1L;
    }

    /**
     * 把一个格式的时间字符串转换成另外一种格式的时间字符串
     *
     * @param dateString 原始的时间字符串
     * @param inFormat   原始的时间字符串的格式
     * @param outFormat  输出的字符串的格式
     * @return 如果转换失败返回空字符串
     */
    public static String convertStringToString(String dateString, String inFormat, String outFormat) {
        Date date = convertStringToDate(dateString, inFormat);
        if (date != null) {
            return convertDateToString(date, outFormat);
        }
        return "";
    }

    /**
     * 把字符串转换成毫秒值
     *
     * @param dateString 需要转换的字符串
     * @param inFormat   字符串的格式，例如yyyy-MM-dd HH:mm:ss
     * @return 如果转换成功，返回转换以后的毫秒值，如果转换失败返回-1
     */
    public static long convertStringToMilliSecond(String dateString, String inFormat) {
        Date convertToDate = convertStringToDate(dateString, inFormat);
        return convertToDate == null ? -1 : convertToDate.getTime();
    }

    /**
     * 把毫秒值转化为格式化的时间字符串
     *
     * @param millisecond 毫秒数
     * @param outFormat   输出的格式
     * @return 转换失败返回空字符串
     */
    public static String convertMillisecondToString(Long millisecond, String outFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outFormat);
        Date date = new Date(millisecond);
        if (date != null) {
            String timeStr = simpleDateFormat.format(date);
            return timeStr;
        }
        return "";
    }
}
