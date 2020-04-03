package com.huahansoft.hhsoftlibrarykit.proxy;

import android.view.View;

/**
 * 类描述：
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/13
 */

public interface HHSoftRefreshHeaderInterface {
    /**
     * 下拉刷新
     */
    void pullToRefresh();
    /**
     * 释放刷新
     */
    void releaseToRefresh();

    /**
     * 正在刷新
     */
    void refreshing();

    /**
     * 刷新完成
     */
    void complete();

    void changeViewHeight(int height);
    View getRefreshHeaderView();
}
