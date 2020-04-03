package com.lysoft.baseproject.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lysoft.baseproject.R;
import com.lysoft.baseproject.constant.SpConstants;
import com.lysoft.baseproject.dialog.LoadingDialog;
import com.lysoft.baseproject.manager.ActivityStackManager;
import com.lysoft.baseproject.utils.EventBusUtils;
import com.lysoft.baseproject.utils.SPUtils;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Describe：所有Activity的基类
 */

public abstract class BaseTimerActivity extends LyActivity {

    private CountTimer countTimerView;
    protected LoadingDialog loadingDialog;
    protected PromptDialog promptDialog;
    private boolean isNeedScreenSave = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptDialog = new PromptDialog(this);
        EventBusUtils.register(this);
        LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                .setMessage("正在加载...")
                .setCancelable(false)
                .setCancelOutside(false);
        loadingDialog = loadBuilder.create();
        View initView = initView();
        setBaseView(initView);
        initValues();
        if (isNeedScreenSave) {
            initTime();
        }
        initListeners();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }

    public void setNeedScreenSave(boolean needScreenSave) {
        isNeedScreenSave = needScreenSave;
    }

    private void initTime() {
        long millisInfuture = 120000;
        int anInt = SPUtils.getInstance().getInt(SpConstants.SCREEN_SAVE_TIME, -1);
        if (anInt != -1 && anInt != 0) {
            millisInfuture = anInt * 60 * 1000;
        }
        countTimerView = new CountTimer(millisInfuture, 1000, this);
    }

    /**
     * 主要的方法，重写dispatchTouchEvent
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isNeedScreenSave) {
            switch (ev.getAction()) {
                //获取触摸动作，如果ACTION_UP，计时开始。
                case MotionEvent.ACTION_UP:
                    countTimerView.start();
                    break;
                //否则其他动作计时取消
                default:
                    countTimerView.cancel();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void timeStart() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                countTimerView.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isNeedScreenSave) {
            countTimerView.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isNeedScreenSave) {
            countTimerView.cancel();
        }
        if (promptDialog != null) {
            promptDialog.dismiss();
            promptDialog = null;
        }
        EventBusUtils.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedScreenSave) {
            timeStart();
        }
    }

    public void showWarnMsg(String hintMsg, PromptButton promptButton) {
        if (this.isFinishing()) {
            return;
        }
        if (promptButton == null) {
            promptButton = new PromptButton("确定", new PromptButtonListener() {
                @Override
                public void onClick(PromptButton button) {
                    //点击事件
                    promptDialog.dismiss();
                }
            });
        }
        promptDialog.showWarnAlert(hintMsg, promptButton);
    }

    public void showWarnMsg(String hintMsg) {
        showWarnMsg(hintMsg, null);
    }
}
