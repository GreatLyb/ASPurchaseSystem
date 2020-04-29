package com.lyb.purchasesystem.ui.clapatwill

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.DetailImageAdapter
import com.lyb.purchasesystem.bean.ClapAtWillBean
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_clap_at_detail.view.*
import java.text.SimpleDateFormat


/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-28 14:07
 */
class ClapAtWillDetailActivity : BaseUIActivity() {
    lateinit var model: ClapAtWillBean
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "随手拍详情"
        val view = View.inflate(pageContext, R.layout.activity_clap_at_detail, null);
        containerView().addView(view)
        model = intent.getSerializableExtra("modelData") as ClapAtWillBean
        initPageData()


    }

    private fun initPageData() {

        containerView().tv_clap_at_title.text = model.clapAtTitle
        containerView().tv_clap_at_user.text = model.clapAtUserName
        containerView().tv_clap_at_time.text = model.clapAtTime
        containerView().tv_clap_at_content.text = model.clapAtContent

        when (model.dealState) {
            //0.等待处理  1.正在处理中 2.完成处理  3.处理时间超时 4超时未处理
            "0" -> {
                containerView().llayout_deal_user_info.visibility = View.GONE
                containerView().tv_deal_state.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.deal_wait, 0, 0)
                containerView().tv_deal_state.text = "等待处理"
                containerView().tv_btn_deal.visibility = View.VISIBLE
                containerView().tv_deal_state.setTextColor(ContextCompat.getColor(pageContext, R.color.wait_deal_color))

            }
            "1" -> {
                containerView().llayout_deal_user_info.visibility = View.VISIBLE
                containerView().tv_clap_at_deal_user.text = "处理人：" + model.dealPersonName
                containerView().tv_clap_at_deal_content.visibility = View.GONE
                containerView().tv_clap_at_deal_time.text = "预计完成处理时间：" + model.dealNeedTime
                containerView().tv_deal_state.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.deal_ing, 0, 0)
                containerView().tv_deal_state.text = "正在处理中"
                containerView().tv_btn_deal.text = "完成处理"
                containerView().tv_btn_deal.setBackgroundResource(R.drawable.base_shape_tv_sure)
                containerView().tv_btn_deal.visibility = View.VISIBLE
                containerView().tv_deal_state.setTextColor(ContextCompat.getColor(pageContext, R.color.deal_ing_color))
            }
            "2" -> {
                containerView().llayout_deal_user_info.visibility = View.VISIBLE
                containerView().tv_clap_at_deal_user.text = "处理人：" + model.dealPersonName
                containerView().tv_clap_at_deal_content.visibility = View.VISIBLE
                containerView().tv_clap_at_deal_content.text = "处理意见：" + model.dealContent
                containerView().tv_clap_at_deal_time.text = "预计完成处理时间：" + model.dealNeedTime
                containerView().tv_deal_state.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.deal_finish, 0, 0)
                containerView().tv_deal_state.text = "完成处理"
                containerView().tv_deal_state.setTextColor(ContextCompat.getColor(pageContext, R.color.main_color))

            }
            "3" -> {
                containerView().llayout_deal_user_info.visibility = View.VISIBLE
                containerView().tv_clap_at_deal_user.text = "处理人：" + model.dealPersonName
                containerView().tv_clap_at_deal_content.text = "处理意见：无"
                containerView().tv_clap_at_deal_time.text = "预计完成处理时间：" + model.dealNeedTime
                containerView().tv_deal_state.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.deal_out, 0, 0)
                containerView().tv_deal_state.text = "处理时间超时"
                containerView().tv_deal_state.setTextColor(ContextCompat.getColor(pageContext, R.color.red))
            }
            "4" -> {
                containerView().llayout_deal_user_info.visibility = View.GONE
                containerView().tv_deal_state.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.deal_out_time, 0, 0)
                containerView().tv_deal_state.text = "超时未处理"
                containerView().tv_deal_state.setTextColor(ContextCompat.getColor(pageContext, R.color.red))
            }

        }


        //图片
        val imageList = model.mutableList
        val pathImageList = mutableListOf<String>()
        for (imageBean in imageList) {
            pathImageList.add(imageList[0].ThumImage)
        }
        containerView().gv_detail_img.adapter = DetailImageAdapter(this, imageList, pathImageList)

        containerView().tv_btn_deal.setOnClickListener { v ->
            //
            if (model.dealState == "0") {
                //去处理
                val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                    val simpleDateFormat = SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT_S)
                    MessageDialog.show(this, "提示", "你已接受此任务！预计完成时间：" + simpleDateFormat.format(date) + "   快去处理吧", "确定");
                }).setTitleText("选择处理完成时间").build()
                        .show()
            } else {
                //确认完成处理
                var intent = Intent(pageContext, SubmitCompleteInfoActivity::class.java)
                intent.putExtra("model", model)
                startActivity(intent)
            }
        }
    }

}