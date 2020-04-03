package com.huahansoft.hhsoftlibrarykit.view.pulltorefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftRefreshFooterInterface;
import com.huahansoft.hhsoftlibrarykit.view.HHSoftLoadingCircleView;

/**
 * 类描述：ListView加载更多底部
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/13
 */

public class HHSoftRefreshFooterView extends LinearLayout implements HHSoftRefreshFooterInterface {
    private View mHeader;
    private HHSoftLoadingCircleView mProgressIconImageView;//刷新进度条图片
    public HHSoftRefreshFooterView(@NonNull Context context) {
        this(context,null);
    }

    public HHSoftRefreshFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        View view=inflate(context, R.layout.hhsoft_include_refresh_list_footer,this);
        mHeader=view.findViewById(R.id.hh_ll_refresh_container);
        mProgressIconImageView=view.findViewById(R.id.huahansoft_iv_refresh_loadmore_icon);
    }

    @Override
    public View getRefreshFooterView() {
        return this;
    }

    @Override
    public void loading() {
        mProgressIconImageView.startLoadingAnim();
    }

    @Override
    public void loadComplete() {
        mProgressIconImageView.stopLoaddingAnim();
    }

    @Override
    public void loadFail() {

    }

    @Override
    public void loadNothing() {

    }
}
