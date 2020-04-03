package com.lysoft.baseproject.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * LargeScreenShop
 * 类描述：
 * 类传参：
 * Creat by Lyb on 2019/10/15 9:01
 */
public abstract class BaseLoadActivity extends LyActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View initView = initView();
        setBaseView(initView);
        initValues();
        initListeners();
    }

}
