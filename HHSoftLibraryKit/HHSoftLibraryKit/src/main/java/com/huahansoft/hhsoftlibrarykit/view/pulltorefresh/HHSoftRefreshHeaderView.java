package com.huahansoft.hhsoftlibrarykit.view.pulltorefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftRefreshHeaderInterface;
import com.huahansoft.hhsoftlibrarykit.view.HHSoftLoadingCircleView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：ListView下拉刷新的头部
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/13
 */

public class HHSoftRefreshHeaderView extends LinearLayout implements HHSoftRefreshHeaderInterface {
    private View mHeader;
    private RelativeLayout mProgressRelativeLayout;//刷新时布局
    private HHSoftLoadingCircleView mProgressIconImageView;//刷新进度条图片
    private RelativeLayout mPullRelativeLayout;//下拉时布局
    private ImageView mPullIconImageView;//下拉箭头图片
    private TextView mPullHintTextView;//下拉提示语
    private TextView mPullTimeTextView;//最近刷新时间展示

    private int pullID = R.string.huahansoft_refresh_header_pull;
    private int realseID = R.string.huahansoft_refresh_header_pull_release;
    private int lastID = R.string.huahansoft_refresh_header_last_time;

    private RotateAnimation mPullAnimation;
    private RotateAnimation mReverseAnimation;

    public HHSoftRefreshHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public HHSoftRefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initValue();
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.hhsoft_include_refresh_list_header, this);
        mHeader = findViewById(R.id.hh_ll_refresh_container);
        mProgressRelativeLayout = findViewById(R.id.huahansoft_rl_refresh_progress);
        mProgressIconImageView = findViewById(R.id.huahansoft_iv_refresh_progress_icon);
        mPullRelativeLayout = findViewById(R.id.huahansoft_rl_refresh_pull);
        mPullIconImageView = findViewById(R.id.huahansoft_iv_refresh_pull_icon);
        mPullHintTextView = findViewById(R.id.huahansoft_tv_refresh_pull_hint);
        mPullTimeTextView = findViewById(R.id.huahansoft_tv_refresh_pull_time);
    }

    private void initValue() {
        mProgressRelativeLayout.setVisibility(GONE);
        mPullRelativeLayout.setVisibility(VISIBLE);
        mPullHintTextView.setText(getContext().getString(pullID));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = format.format(new Date());
        mPullTimeTextView.setText(getContext().getString(lastID) + date);

        mPullAnimation = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mPullAnimation.setInterpolator(new LinearInterpolator());
        mPullAnimation.setDuration(250);
        mPullAnimation.setFillAfter(true);

        mReverseAnimation = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseAnimation.setInterpolator(new LinearInterpolator());
        mReverseAnimation.setDuration(200);
        mReverseAnimation.setFillAfter(true);
    }

    @Override
    public View getRefreshHeaderView() {
        return this;
    }

    @Override
    public void pullToRefresh() {
        mProgressIconImageView.stopLoaddingAnim();
        mProgressRelativeLayout.setVisibility(GONE);
        mPullRelativeLayout.setVisibility(VISIBLE);
        mPullIconImageView.clearAnimation();
        mPullIconImageView.startAnimation(mReverseAnimation);
        mPullHintTextView.setText(getContext().getString(pullID));
    }

    @Override
    public void releaseToRefresh() {
        mProgressIconImageView.stopLoaddingAnim();
        mProgressRelativeLayout.setVisibility(GONE);
        mPullRelativeLayout.setVisibility(VISIBLE);
        mPullIconImageView.clearAnimation();
        mPullIconImageView.startAnimation(mPullAnimation);
        mPullHintTextView.setText(getContext().getString(realseID));
    }

    @Override
    public void refreshing() {
        mPullIconImageView.clearAnimation();
        mPullRelativeLayout.setVisibility(GONE);
        mProgressRelativeLayout.setVisibility(VISIBLE);
        mProgressIconImageView.startLoadingAnim();
    }

    @Override
    public void complete() {
        mProgressIconImageView.stopLoaddingAnim();
        mPullIconImageView.clearAnimation();
        mProgressRelativeLayout.setVisibility(GONE);
        mPullRelativeLayout.setVisibility(VISIBLE);
        mPullHintTextView.setText(getContext().getString(pullID));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = format.format(new Date());
        mPullTimeTextView.setText(getContext().getString(lastID) + date);
    }

    @Override
    public void changeViewHeight(int height) {
        ViewGroup.LayoutParams params = mHeader.getLayoutParams();
        params.height = height;
        mHeader.setLayoutParams(params);
    }
}
