package com.lysoft.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lysoft.baseproject.R;
import com.lysoft.baseproject.imp.BasePageBaseOper;
import com.lysoft.baseproject.manager.ActivityStackManager;
import com.lysoft.baseproject.manager.DefaultTopViewManager;
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
    private DefaultTopViewManager topViewManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().setAttributes(attributes);
        topViewManager=new DefaultTopViewManager(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_base);
        mContext = this;
        initBaseInfo();

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




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        ActivityStackManager.getActivityStackManager().popActivity(this);
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

}
