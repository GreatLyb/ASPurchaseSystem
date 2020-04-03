package com.lysoft.baseproject.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TurnsUtils {

    /**
     * 小数位数补0
     *
     * @param data
     * @param decimalCount
     * @return
     */
    public static String setDecimalCount(double data, int decimalCount) {
        String formatStr = "0.0";
        for (int i = 1; i < decimalCount; i++) {
            formatStr += "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat(formatStr);
        String dataStr = decimalFormat.format(data);
        return dataStr;
    }

    /**
     * @param content
     * @param defaultNum 当输出内容为null 时需要返回的值
     * @return
     */
    public static int getInt(String content, int defaultNum) {
        if (TextUtils.isEmpty(content)) {
            return defaultNum;
        }
        try {
            int num = Integer.valueOf(content);// 把字符串强制转换为数字
            return num;// 如果是数字，返回True
        } catch (Exception e) {

            return defaultNum;// 如果抛出异常，返回False
        }

    }

    /**
     * @param content
     * @param defaultNum 当输出内容为null 时需要返回的值
     * @return
     */
    public static float getFloat(String content, float defaultNum) {
        if (TextUtils.isEmpty(content)) {
            return defaultNum;
        }
        try {
            float num = Float.valueOf(content);// 把字符串强制转换为数字

            return num;// 如果是数字，返回True
        } catch (Exception e) {

            return defaultNum;// 如果抛出异常，返回False
        }

    }

    /**
     * @param content
     * @param defaultNum 当输出内容为null 时需要返回的值
     * @return
     */
    public static double getDouble(String content, double defaultNum) {
        if (TextUtils.isEmpty(content)) {

            return defaultNum;
        }
        try {
            double num = Double.valueOf(content);// 把字符串强制转换为数字

            return num;// 如果是数字，返回True
        } catch (Exception e) {

            return defaultNum;// 如果抛出异常，返回False
        }

    }

    /**
     * 根据生日获取现在年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static String getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }

        return age + "";
    }

    /**
     * 读取本地assets 中文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getStringFromFile(Context context, String fileName) {
        try {
            // Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer);
            Log.i("cyb", "text==" + text);
            return text;
        } catch (Exception e) {
            // Should never happen!
            Log.i("cyb", "异常==" + e.getClass().toString());
            return null;
        }

    }


    /**
     * 判断手机格式是否正确
     *
     * @param tel
     * @return
     */
    public static boolean isTel(String tel) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(tel);
        return m.matches();

    }
}
