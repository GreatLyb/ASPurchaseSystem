package com.lyb.purchasesystem.ui.suggestions

import android.os.Bundle
import android.view.View
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUIActivity

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-26 15:30
 */
class SuggestionsInfoActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "意见详情"
        val view = View.inflate(pageContext, R.layout.activity_suggestion_info, null)
        containerView().addView(view)
    }
}