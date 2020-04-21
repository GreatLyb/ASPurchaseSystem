package com.lyb.purchasesystem.fragment

import android.view.View
import com.lysoft.baseproject.activity.BaseUIFragment

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 14:39
 */
class CommunityFragment : BaseUIFragment() {
    override fun onCreate() {
        topViewManager().backTextView().visibility = View.GONE
        topViewManager().titleTextView().text = "社区 "
    }
}