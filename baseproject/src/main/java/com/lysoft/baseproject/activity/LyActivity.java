package com.lysoft.baseproject.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lysoft.baseproject.R;
import com.lysoft.baseproject.imp.BasePageBaseOper;
import com.lysoft.baseproject.manager.ActivityStackManager;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * LargeScreenShop
 * 类描述：
 * 类传参：
 *
 * @author DELL Creat by Lyb on 2019/10/15 8:35
 */
public abstract class LyActivity extends AppCompatActivity implements BasePageBaseOper {

    private static final String TAG = "LyActivity";
    protected LinearLayout linearLayout;
    /**
     * 倒计时控件
     */
    protected ShowTiemTextView showTiemTextView;
    /**
     * 底部布局
     */
    LinearLayout baseBottomLinearLayout;
    /**
     * 头部布局
     */
    LinearLayout baseTopLinearLayout;
    /**
     * 内容布局
     */
    FrameLayout baseContainerFrameLayout;
    /**
     * 根布局
     */
    RelativeLayout baseParentRelativeLayout;
    /**
     * 保存当前页面的上下文对象
     */
    private Context mContext;
    private View mBaseView;
    //J监听home键和多功能按键
    private HomeReceiver homeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().setAttributes(attributes);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        //        hideBottomUIMenu2();
        setContentView(R.layout.activity_base);
        mContext = this;
        initBaseInfo();

        //动态注册广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //启动广播
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);
    }

    @Override
    public void initListeners() {

    }

    private void initBaseInfo() {
        //获取基本架构的基本控件
        baseBottomLinearLayout = findViewById(R.id.ll_base_bottom);
        baseTopLinearLayout = findViewById(R.id.ll_base_top);
        baseContainerFrameLayout = findViewById(R.id.fl_base_container);
        baseParentRelativeLayout = findViewById(R.id.rl_base_parent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.e("permissions:" + Arrays.toString(permissions) + " grantResults:" + Arrays.toString(grantResults));
        //如果有未授权权限则跳转设置页面
        if (!requestPermissionsResult(grantResults)) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 判断授权结果
     */
    private boolean requestPermissionsResult(int[] grantResults) {
        for (int code : grantResults) {
            if (code == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把创建的View添加到显示内容的中间容器.<br/>
     *
     * @param index 插入的位置
     * @param view  插入的视图
     */
    protected void addViewToContainer(int index, View view) {
        baseContainerFrameLayout.addView(view, index, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setBaseView(View view) {
        if (mBaseView != null) {
            baseContainerFrameLayout.removeView(mBaseView);
        }
        mBaseView = view;
        addViewToContainer(0, view);
    }

    //移除时间视图
    protected void removeCountDownView() {
        if (showTiemTextView != null && linearLayout != null && hasAddView) {
            showTiemTextView.destroy();
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            manager.removeViewImmediate(linearLayout);

            linearLayout.removeView(showTiemTextView);
            linearLayout.removeAllViews();
            showTiemTextView = null;
            linearLayout = null;
        }
    }

    //已经添加过view了
    private boolean hasAddView = false;
    private boolean menuKey = false;

    //初始化时间控件
    protected void initCountDownView() {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        if (Build.VERSION.SDK_INT>=26){
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.RIGHT | Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.y = 0;
        params.x = 0;
        if (showTiemTextView == null) {
            showTiemTextView = new ShowTiemTextView(this);
            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 20;
            layoutParams.rightMargin = 13;
            showTiemTextView.setTextColor(Color.parseColor("#ffffff"));
            showTiemTextView.setTextSize(15);
            linearLayout.addView(showTiemTextView, layoutParams);
        }
        manager.addView(linearLayout, params);
        hasAddView = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!hasAddView && menuKey) {
            menuKey = false;
            initCountDownView();
        }
    }

    class HomeReceiver extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        //                        Toast.makeText(context, "Home键被监听", Toast.LENGTH_SHORT).show();
                        if (hasAddView) {
                            menuKey = true;
                            hasAddView = false;
                            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                            manager.removeViewImmediate(linearLayout);
                        }
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        //                        Toast.makeText(context, "多任务键被监听", Toast.LENGTH_SHORT).show();
                        if (hasAddView) {
                            menuKey = true;
                            hasAddView = false;
                            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                            manager.removeViewImmediate(linearLayout);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        ActivityStackManager.getActivityStackManager().popActivity(this);
        unregisterReceiver(homeReceiver);
    }

    @Override
    public View getBaseView() {
        return mBaseView;
    }

    /**
     * 返回当前页面的跟布局
     *
     * @return
     */
    protected RelativeLayout getBaseParentLayout() {
        return baseParentRelativeLayout;
    }

    @Override
    public FrameLayout getBaseContainerLayout() {
        return baseContainerFrameLayout;
    }

    @Override
    public void addViewToContainer(View view) {
        addViewToContainer(-1, view);
    }

    /**
     * 获取当前页面显示的上边的布局
     *
     * @return
     */
    public LinearLayout getBaseTopLayout() {
        return baseTopLinearLayout;
    }

    /**
     * 获取当前页面显示的下边的布局
     *
     * @return
     */
    protected LinearLayout getBaseBottomLinearLayout() {
        return baseBottomLinearLayout;
    }

    @Override
    public Context getPageContext() {
        return mContext;
    }

    protected void hideBottomUIMenu2() {
        //隐藏虚拟按键，并且全屏
        Window _window = getWindow();
        WindowManager.LayoutParams params = _window.getAttributes();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        _window.setAttributes(params);
    }

}
