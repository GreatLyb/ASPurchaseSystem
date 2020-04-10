package com.lyb.purchasesystem.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lyb.purchasesystem.R;
import com.lysoft.baseproject.activity.BaseUIFragment;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 14:39
 */
public class MainFragment extends BaseUIFragment {


    @Override
    protected void onCreate() {
        topViewManager().backTextView().setVisibility(View.GONE);
        topViewManager().titleTextView().setText("首页");
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.scan_icon, 0);
        initListener();
    }

    private void initListener() {
        topViewManager().moreLayout().setOnClickListener(v -> {
            ARouter.getInstance().build("/qrcodelibrary/ScanCodeActivity").navigation();
        });
    }
}
