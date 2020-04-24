package com.lyb.purchasesystem.ui.suggestions

import android.content.Intent
import android.os.Bundle
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.ui.main.AddCommentActivity
import com.lysoft.baseproject.activity.BaseUIActivity

/**
 * ASPurchaseSystem
 * 类描述：领导的意见箱页面
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class SuggestionsBoxLeaderActivity : BaseUIActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "意见箱"
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.add_white, 0, 0, 0)
        topViewManager().moreLayout().setOnClickListener { v ->
            startActivity(Intent(pageContext, AddCommentActivity::class.java))
        }
    }

}