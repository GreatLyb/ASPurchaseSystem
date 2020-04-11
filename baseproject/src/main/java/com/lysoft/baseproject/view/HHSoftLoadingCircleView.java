package com.lysoft.baseproject.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 类描述：
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/5
 */

public class HHSoftLoadingCircleView extends AppCompatImageView {
    private AnimationDrawable mAnimationDrawable;

    public HHSoftLoadingCircleView(Context context) {
        super(context);
    }

    public HHSoftLoadingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HHSoftLoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startLoadingAnim() {
        if (this.getBackground() instanceof AnimationDrawable) {
            mAnimationDrawable = (AnimationDrawable) this.getBackground();
            this.post(new Runnable() {

                @Override
                public void run() {
                    mAnimationDrawable.start();
                }
            });
        }
    }

    public void stopLoaddingAnim() {
        if (this == null) {
            return;
        }
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}
