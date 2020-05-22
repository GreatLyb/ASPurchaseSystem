package com.lyb.purchasesystem.ui.warehouse

import android.os.Bundle
import android.view.View
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUIActivity

/**
 * ASPurchaseSystem
 * 类描述：库存商品详情
 * 类传参：goodsbean (model) 库存商品详情
 *
 * @Author： create by Lyb on 2020-04-26 15:30
 */
class WareHouseItemDetailActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "库存物品详情"
        val view = View.inflate(pageContext, R.layout.activity_ware_item_info, null)
        containerView().addView(view)
        initValue()
        initListener()

    }

    private fun initListener() {


    }

    private fun initValue() {


    }
}