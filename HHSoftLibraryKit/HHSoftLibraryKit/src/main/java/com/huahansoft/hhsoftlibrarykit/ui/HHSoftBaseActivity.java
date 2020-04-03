package com.huahansoft.hhsoftlibrarykit.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

public class HHSoftBaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    /**
     * 请求码-权限请求
     */
    public static final int REQUEST_CODE_PERMISSION = 1000;
    /**
     * 上下文对象
     */
    private Context mContext;
    private Map<String, Call<String>> mRequestCallMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected void onPause() {
        if (mRequestCallMap != null && mRequestCallMap.size() > 0) {
            for (Map.Entry<String, Call<String>> entry : mRequestCallMap.entrySet()) {
                Call<String> call = entry.getValue();
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mRequestCallMap != null && mRequestCallMap.size() > 0) {
            for (Map.Entry<String, Call<String>> entry : mRequestCallMap.entrySet()) {
                Call<String> call = entry.getValue();
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
            }
            mRequestCallMap.clear();
        }
        super.onDestroy();
    }

    /**
     * 保存请求Call对象
     *
     * @param key
     * @param call
     */
    protected void addRequestCallToMap(String key, Call<String> call) {
        if (call != null) {
            if (mRequestCallMap == null) {
                mRequestCallMap = new HashMap<>();
            }
            mRequestCallMap.put(key, call);
        }
    }

    /**
     * 根据View的ID，在parentView中查找ID为viewID的View
     *
     * @param parentView
     * @param viewID
     * @return
     */
    public <T> T getViewByID(View parentView, int viewID) {
        return (T) parentView.findViewById(viewID);
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    protected Context getPageContext() {
        return mContext;
    }

    /**
     * 权限请求检查
     *
     * @param perms 权限数组
     * @return true:已申请权限；false：为申请权限
     */
    protected boolean checkPermission(String[] perms) {
        return EasyPermissions.hasPermissions(getPageContext(), perms);
    }

    /**
     * 权限请求
     *
     * @param tip   权限请求提示语
     * @param perms 权限数组
     */
    protected void requestPermission(String tip, String[] perms) {
        EasyPermissions.requestPermissions(this, tip, REQUEST_CODE_PERMISSION, perms);
    }

    /**
     * 请求成功
     */
    protected void permissionsGranted() {

    }

    /**
     * 请求失败
     *
     * @param perms
     */
    protected void permissionsDenied(List<String> perms) {

    }

    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            //将请求结果传递EasyPermission库处理
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
    }

    /**
     * 权限成功的回调
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (REQUEST_CODE_PERMISSION == requestCode) {
            permissionsGranted();
        }
    }

    /**
     * 权限失败的回调
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (REQUEST_CODE_PERMISSION == requestCode) {
            permissionsDenied(perms);
        }
    }
}
