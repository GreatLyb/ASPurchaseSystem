package com.lyb.purchasesystem.ui.departdevice;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lyb.purchasesystem.R;
import com.lysoft.baseproject.activity.BaseUIActivity;

/**
 * ASPurchaseSystem
 * 类描述：设备详情
 * * 类传参：
 *
 * @Author： create by Lyb on 2020-05-29 16:06
 */
public class DepartDeviceDetailActivity extends BaseUIActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("喷墨打印器");
        View view = View.inflate(this, R.layout.activity_depart_device_detail, null);
        containerView().addView(view);
        initView();
    }

    /**
     * 初始化VIew
     */
    private void initView() {

    }
}
