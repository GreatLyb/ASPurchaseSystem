package com.huahansoft.hhsoftlibrarykit.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @类说明 关于app的信息
 * @作者 hhsoft
 * @创建日期 2019/8/18 18:28
 */
public class HHSoftAppUtils {
    /**
     * 获取APP的名字
     *
     * @param context 上下文
     * @return
     */
    public static String appName(Context context) {
        return appNameWithPackage(context, context.getPackageName());
    }

    /**
     * 根据包名获取APP的名字
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static String appNameWithPackage(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
            return info.loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取APP的icon
     *
     * @param context 上下文
     * @return 获取失败返回null
     */
    public static Drawable appIcon(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(context.getPackageName(), 0);
            return info.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 获取失败返回-1
     */
    public static int appVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取版本名
     *
     * @param context 上下文
     * @return 获取失败返回空字符串
     */
    public static String appVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取APP的目标SDK版本
     *
     * @param context 上下文
     * @return 获取失败返回-1
     */
    public static int appTargetSdkVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(context.getPackageName(), 0);
            return info.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断某应用是否安装
     *
     * @param context
     * @param packageName 某应用包名
     * @return yes=安装；no=未安装
     */
    public static boolean isInstalliApp(Context context, String packageName) {
        boolean isInstall = false;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            isInstall = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInstall;
    }

    /**
     * 安装应用程序.如果路径合法并且文件存在的时候回安装文件，否则不执行任何操作
     *
     * @param context
     * @param filePath  apk本地路径
     * @param authority uri的authorities，以便于content://android:authorities/uri访问到
     */
    public static void installApp(Context context, String filePath, String authority) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, authority, file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载其他APP
     *
     * @param context
     * @param packageName APP包名
     */
    public static void uninstallApp(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(intent);
    }
}
