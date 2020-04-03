package com.lysoft.baseproject.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;

@SuppressLint("AppCompatCustomView")
public class ShowTiemTextView extends TextView {

    private int time;
    private TimeOutCallback outCallback;
    private MyCountDownTimer countDownTimer;

    public ShowTiemTextView(Context context) {
        super(context);
    }

    public ShowTiemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTime(int time) {  //设定初始值
        this.time = time;
    }

    public void start() {
        destroy();
        countDownTimer = new MyCountDownTimer(time * 1000, 1000);
        countDownTimer.start();
    }

    public void destroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long leftTime = millisUntilFinished / 1000;
            if (leftTime==30){
                ToastUtils.show("购物时长即将耗尽，请在30秒完成本次购物");
            }
            if (leftTime==20){
                ToastUtils.show("购物时长即将耗尽，请在20秒完成本次购物");
            }
            if (leftTime==10){
                ToastUtils.show("购物时长即将耗尽，请在10秒完成本次购物");
            }
            ShowTiemTextView.this.setText("购物倒计时: " + millisUntilFinished / 1000 + "秒");

        }

        @Override
        public void onFinish() {
            if (outCallback != null) {
                outCallback.timeOut();
            }
        }
    }

    public void setOutCallback(TimeOutCallback outCallback) {
        this.outCallback = outCallback;
    }

    public interface TimeOutCallback {
        void timeOut();
    }
}
