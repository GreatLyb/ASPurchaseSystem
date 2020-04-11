package com.lysoft.baseproject.imp;

import android.view.View;

/**
 * 类描述：
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/13
 */

public interface HHSoftRefreshFooterInterface {
    void loading();

    void loadComplete();

    void loadFail();

    void loadNothing();

    View getRefreshFooterView();
}
