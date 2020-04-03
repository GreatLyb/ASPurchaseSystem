package com.lysoft.baseproject.imp;

import android.view.View;

/**
 * @author lyb 用于处理Adaper中的view被点击
 */
public interface AdapterViewClickListener {
    /**
     * 接口默认方法可实现可不实现
     *
     * @param position
     * @param view
     */
    default void adapterViewClick(int position, View view) {
    }

    /**
     * 接口默认方法可实现可不实现
     *
     * @param position
     * @param num
     * @param view
     */
    default void adapterViewClick(int position, int num, View view) {
    }
}
