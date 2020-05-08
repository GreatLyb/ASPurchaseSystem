package com.lyb.purchasesystem.ui.classroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUILoadActivity
import com.lysoft.baseproject.imp.LoadStatus
import kotlinx.android.synthetic.main.activity_appointment_detail.*

/**
 * ASPurchaseSystem
 * 类描述：预约详情
 * 类传参：
 * @Author： create by Lyb on 2020-05-08 8:52
 */
class AppointmentDetailActivity : View.OnClickListener, BaseUILoadActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "教室预约详情"
        val view = View.inflate(pageContext, R.layout.activity_appointment_detail, null)
        containerView().addView(view)
        tv_class_detail_phone.setOnClickListener(this)
        tv_call_phone.setOnClickListener(this)
    }

    override fun onPageLoad() {
        loadViewManager().changeLoadState(LoadStatus.SUCCESS)
    }

    override fun onClick(v: View?) {

        callPhone("17562011005")
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    private fun callPhone(phoneNum: String) {
        MessageDialog.show(this, "提示", "确认要拨打" + phoneNum + "吗？", "确定", "取消").setOnOkButtonClickListener { baseDialog, v ->
            baseDialog.doDismiss()
            val intent = Intent(Intent.ACTION_CALL)
            val data = Uri.parse("tel:$phoneNum")
            intent.data = data
            ActivityUtils.startActivity(intent)
            true
        }.onCancelButtonClickListener = OnDialogButtonClickListener({ baseDialog, v ->
            baseDialog.doDismiss()
            true
        })

    }
}