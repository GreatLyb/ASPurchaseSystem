package com.lyb.purchasesystem.ui.purchase

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.purchase.PurchaseBean
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_purchase_info.view.*

/**
 * ASPurchaseSystem
 * 类描述：采购单详情
 * 类传参：PurchaseBean (model) 采购详情
 *
 * @Author： create by Lyb on 2020-04-26 15:30
 */
class PurchaseInfoActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "采购单详情"
        val view = View.inflate(pageContext, R.layout.activity_purchase_info, null)
        containerView().addView(view)
        initValue()
        initListener()

    }

    private fun initListener() {

        containerView().rg_purchase.setOnCheckedChangeListener { group, checkedId ->
            containerView().et_purchase_info_deal_content.visibility = View.VISIBLE
        }
        containerView().tv_submit_purchase_deal.setOnClickListener { l ->
            com.hjq.toast.ToastUtils.show("提交")
        }
    }

    private fun initValue() {
        var model: PurchaseBean = intent.getSerializableExtra("model") as PurchaseBean
        containerView().tv_purchase_info_name.text = model.purchaseName
        containerView().tv_purchase_info_specifications.text = model.purchasespecifications
        containerView().tv_purchase_info_price.text = model.purchasePrice
        containerView().tv_purchase_info_num.text = model.purchaseNum
        containerView().tv_purchase_info_person.text = model.purchasePerson
        containerView().tv_purchase_info_department.text = model.purchaseDepartment
        containerView().tv_purchase_info_remarks.text = model.purchaseRemarks
        when (model.purchaseDealState) {
            "0" -> {
                containerView().tv_purchase_info_apply_state.text = "待处理"
                containerView().tv_purchase_info_apply_state.setTextColor(ContextCompat.getColor(pageContext, R.color.wait_deal_color))
                containerView().llayout_deal_info.visibility = View.GONE
                containerView().tv_submit_purchase_deal.visibility = View.VISIBLE
            }
            "1" -> {
                containerView().tv_purchase_info_apply_state.text = "已同意"
                containerView().tv_purchase_info_apply_state.setTextColor(ContextCompat.getColor(pageContext, R.color.main_color))
                containerView().llayout_deal_info.visibility = View.VISIBLE
                containerView().rb_refuse.visibility = View.GONE
                containerView().rb_agree.visibility = View.VISIBLE
                containerView().rb_agree.isChecked = true
                containerView().rb_agree.isEnabled = false
                containerView().tv_submit_purchase_deal.visibility = View.GONE
            }
            "2" -> {
                containerView().tv_purchase_info_apply_state.text = "已拒绝"
                containerView().tv_purchase_info_apply_state.setTextColor(ContextCompat.getColor(pageContext, R.color.red))
                containerView().llayout_deal_info.visibility = View.VISIBLE
                containerView().rb_refuse.visibility = View.VISIBLE
                containerView().rb_agree.visibility = View.GONE
                containerView().rb_agree.isEnabled = false
                containerView().tv_submit_purchase_deal.visibility = View.GONE
                containerView().rb_refuse.isChecked = true
            }
        }
        if (!model.purchaseDealState.equals("0")) {
            containerView().tv_purchase_info_deal_person.text = model.purchaseDealPerson
            containerView().tv_purchase_info_deal_time.text = model.purchaseDealTime
            containerView().tv_purchase_info_deal_content.text = model.purchaseDealContent
        }

    }
}