package com.lysoft.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

/**
 * LargeScreenShop
 * 类描述：
 * 类传参：
 * Creat by Lyb on 2019/10/25 10:16
 */
public class CountTimer extends CountDownTimer {
    private Context context;

    /**
     * 参数 millisInFuture       倒计时总时间（如60S，120s等）
     * 参数 countDownInterval    渐变时间（每次倒计1s）
     */
    CountTimer(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = context;
    }

    /**
     * 计时完毕时触发
     */
    @Override
    public void onFinish() {
        //去屏保
        context.startActivity(new Intent(context, ScreenSaverActivity.class));
    }

    /**
     * 计时过程显示
     */
    @Override
    public void onTick(long millisUntilFinished) {
        //        Log.i("Lyb", "计时==" + millisUntilFinished);
    }

}
