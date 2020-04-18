package com.lyb.purchasesystem.ui.user

import android.os.Bundle
import android.view.View
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUIActivity

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-18 10:32
 */
class UserEditPwdActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "修改登录密码"
        val view = View.inflate(pageContext, R.layout.activity_edit_pwd, null)
        containerView().addView(view)
    }
}