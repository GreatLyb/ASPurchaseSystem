package com.lyb.purchasesystem.fragment;

import com.lysoft.baseproject.activity.BaseUIFragment;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 14:39
 */
public class UserFragment extends BaseUIFragment {

    @Override
    protected void onCreate() {
        topViewManager().titleTextView().setText("个人中心");
    }
}
