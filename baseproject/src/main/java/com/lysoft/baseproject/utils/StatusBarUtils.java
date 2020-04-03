package com.lysoft.baseproject.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.graphics.ColorUtils;

/**
 * @类说明 HHSoftStatusBarUtils
 * @作者 hhsoft
 * @创建日期 2019/8/20 17:20
 */
public class StatusBarUtils {
    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     */
    public static boolean isLightColor(int color) {
        double calculate = ColorUtils.calculateLuminance(color);
        return calculate >= 0.5 || calculate == 0.0;
    }

    /**
     * 改变状态栏的背景颜色以及根据颜色值改变状态栏的字体及图标
     * 注意：
     * 一、该方法仅支持6.0及以上版本
     * 二、该方法布局不占用状态来区域，与getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);不同时使用
     *
     * @param activity
     * @param color
     */
    public static void statusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            // 设置状态栏底色颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
            // 如果亮色，设置状态栏文字为黑色
            View decorView = window.getDecorView();
            if (isLightColor(color)) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 改变状态栏背景透明的方法
     * 注意：
     * 一、该方法仅支持4.4及以上版本
     * 二、该方法设置后，状态栏半透明状态，不宜再设置状态栏背景颜色及字体颜色，布局占用状态栏高度
     *
     * @param activity
     * @return true：全屏；false：非全屏
     */
    public static boolean fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return true;
        }
        return false;
    }

    /**
     * 全屏及状态栏设置
     * 注意：
     * 一、该方法仅支持6.0及以上版本
     * 二、该方法设置后，布局占用状态栏高度
     *
     * @param activity
     * @param color
     * @return true：全屏；false：非全屏
     */
    public static boolean fullScreenWithStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            // 设置状态栏底色颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
            // 如果亮色，设置状态栏文字为黑色
            View decorView = window.getDecorView();
            if (isLightColor(color)) {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(option);
            } else {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_VISIBLE;
                decorView.setSystemUiVisibility(option);
            }
            return true;
        }
        return false;
    }
}
