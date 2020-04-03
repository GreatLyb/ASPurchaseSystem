package com.huahansoft.hhsoftlibrarykit.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class HHSoftSystemUtils {

    /**
     * 设备令牌 注意：调用该方法之前需要进行权限请求
     *
     * @param context 上下文
     * @return 获取失败返回空字符串
     */
    @SuppressLint("MissingPermission")
    public static String deviceToken(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceToken = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceToken)) {
            deviceToken = "";
        } else {
            deviceToken = "token_" + deviceToken;
        }
        return deviceToken;
    }

    /**
     * 获取手机的型号，如lenovo-a750等
     *
     * @return
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 获取手机的制造厂商
     *
     * @return
     */
    public static String getPhoneMaker() {
        return Build.MANUFACTURER;
    }

}
