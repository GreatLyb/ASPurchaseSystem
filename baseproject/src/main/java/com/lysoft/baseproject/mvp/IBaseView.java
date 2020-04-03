package com.lysoft.baseproject.mvp;

import android.content.Context;


/**
 * Describe：所有View基类
 */

public interface IBaseView {

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void dismissLoading();

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();


    /**
     * 显示警示框
     *
     * @return
     */
    void showWarnPromptDialog(String msg,  int type);


}
