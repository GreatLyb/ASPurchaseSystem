package com.lyb.purchasesystem.consta

import android.Manifest
import android.provider.Settings

/**
 * LargeScreenShop
 * 类描述：
 * 类传参：
 *
 * @Author： Creat by Lyb on 2019/11/26 15:17
 */
object PermissionsConstant {
    /*读写权限*/
    var PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    /*相机+读写权限*/
    var PERMISSIONS_CAMERA_AND_STORAGE = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    /**
     * 悬浮窗权限
     */
    var NEEDED_PERMISSIONS = arrayOf(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
}