package com.lyb.purchasesystem.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.BarUtils;
import com.lysoft.baseproject.activity.BaseUILoadActivity;
import com.lysoft.baseproject.imp.LoadStatus;

import androidx.annotation.Nullable;

@Route(path = "/app/MainActivity")
public class MainActivity extends BaseUILoadActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarLightMode(getWindow(), true);
        topViewManager().titleTextView().setText("首页");
        loadViewManager().changeLoadState(LoadStatus.LOADING);

    }

    @Override
    protected void onPageLoad() {

    }

}
