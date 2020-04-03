package com.huahansoft.hhsoftlibrarykit.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class HHSoftServiceUtils {
    /**
     * 获取正在运行的服务
     *
     * @param context 上下文对象
     * @param maxNum  最多获取的服务的个数
     * @return 正在运行服务集合
     */
    public static List<ActivityManager.RunningServiceInfo> runningServices(Context context, int maxNum) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return manager.getRunningServices(maxNum);
    }

    /**
     * 判断某个服务是否开启
     *
     * @param className 查看的服务的类名
     * @return true:正在运行；false：没有运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        List<ActivityManager.RunningServiceInfo> list = runningServices(context, Integer.MAX_VALUE);
        for (int i = 0; i < list.size(); i++) {
            if (className.equals(list.get(i).service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
