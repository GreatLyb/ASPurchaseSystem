package com.lyb.purchasesystem.fragment;

import android.view.View;

import com.lysoft.baseproject.activity.BaseUIFragment;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 14:39
 */
public class MsgFragment extends BaseUIFragment {

    @Override
    protected void onCreate() {
        topViewManager().backTextView().setVisibility(View.GONE);
        topViewManager().titleTextView().setText("消息");
    }
}
