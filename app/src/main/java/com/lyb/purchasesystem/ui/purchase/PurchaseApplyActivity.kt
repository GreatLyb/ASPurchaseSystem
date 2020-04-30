package com.lyb.purchasesystem.ui.purchase

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.blankj.utilcode.util.ToastUtils
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_purchase_apply.*
import kotlinx.android.synthetic.main.activity_purchase_apply.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * ASPurchaseSystem
 * 类描述：申请采购
 * 类传参：
 * @Author： create by Lyb on 2020-04-30 15:24
 */
class PurchaseApplyActivity : View.OnClickListener, BaseUIActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "申请采购"
        val view = View.inflate(pageContext, R.layout.activity_purchase_apply, null)
        containerView().addView(view)
        containerView().tv_submit_purchase.setOnClickListener(this)
        containerView().tv_purchase_time.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            tv_purchase_time.id -> {
                //选择时间
                val endCalendar = Calendar.getInstance()
                endCalendar.add(Calendar.YEAR, 10)
                val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                    val simpleDateFormat = SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT_S)
                    containerView().tv_purchase_time.text = simpleDateFormat.format(date)

                }).setTitleText("选择时间").setRangDate(Calendar.getInstance(), endCalendar).build()
                        .show()
            }
            tv_submit_purchase.id -> {
                //提交
                submitPurchase()
            }

        }
    }

    /**
     * 提交采购信息
     */
    private fun submitPurchase() {
        var purchaseName = containerView().et_purchase_name.text.toString()//必填
        var specifications = containerView().et_purchase_specifications.text.toString()//必填
        var price = containerView().et_purchase_price.text.toString()
        var num = containerView().et_purchase_num.text.toString()//必填
        var time = containerView().tv_purchase_time.text.toString()
        var mark = containerView().et_purchase_mark.text.toString()
        if (TextUtils.isEmpty(purchaseName)) {
            ToastUtils.showShort("请输入物品名称")
            return
        }
        if (TextUtils.isEmpty(specifications)) {
            ToastUtils.showShort("请输入规格型号")
            return
        }
        if (TextUtils.isEmpty(num)) {
            ToastUtils.showShort("请输入采购数量")
            return
        }

    }
}