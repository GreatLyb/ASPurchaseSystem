package com.lysoft.baseproject.imp;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 定义了页面的基本操作，主要实现在Activity和Fragment
 *
 * @author yuan
 */
public interface BasePageBaseOper {
    /**
     * 获取上下文
     *
     * @return
     */
    Context getPageContext();
    /**
     * 加载页面显示的布局
     * @return
     */
    View initView();
    /**
     * 对页面的基本控件添加基本的值
     */
    void initValues();
    /**
     * 给控件添加监听器
     */
    void initListeners();
    /**
     * 设置页面显示的基本布局
     * @param view
     */
    void setBaseView(View view);

    /**
     * 获取当前页面显示的视图
     */
    View getBaseView();
    /**
     * 获取当前页面显示内容的Layout
     * @return
     */
    FrameLayout getBaseContainerLayout();
    /**
     * 把视图添加到内容的容器当中
     * @param view
     */
    void addViewToContainer(View view);
}	
