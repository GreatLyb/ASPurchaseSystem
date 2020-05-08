package com.lyb.purchasesystem.ui.classroom

import android.os.Bundle
import android.view.View
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUILoadActivity
import com.lysoft.baseproject.imp.LoadStatus

/**
 * ASPurchaseSystem
 * 类描述：预约详情
 * 类传参：
 * @Author： create by Lyb on 2020-05-08 8:52
 */
class AppointmentDetailActivity : BaseUILoadActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "教室预约详情"
        val view = View.inflate(pageContext, R.layout.activity_appointment_detail, null)
        containerView().addView(view)
    }

    override fun onPageLoad() {
        loadViewManager().changeLoadState(LoadStatus.SUCCESS)
    }
}