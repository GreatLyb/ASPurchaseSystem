package com.lyb.purchasesystem.ui.classroom

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.hjq.toast.ToastUtils
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.clat.ClatAtTypeBean
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_add_appointment.*
import kotlinx.android.synthetic.main.activity_add_appointment.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * ASPurchaseSystem
 * 类描述：添加预约教室
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-07 15:46
 */
class AddAppointmentActivity : View.OnClickListener, BaseUIActivity() {

    var cardItem = mutableListOf<ClatAtTypeBean>()
    var roomCustomOptions: OptionsPickerView<OptionsPickerBuilder>? = null
    var departmentCustomOptions: OptionsPickerView<OptionsPickerBuilder>? = null
    var startDate: Calendar? = null
    var endDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "预约职教室"
        val view = View.inflate(pageContext, R.layout.activity_add_appointment, null)
        containerView().addView(view)
        containerView().tv_select_class_room.setOnClickListener(this)
        containerView().tv_select_department.setOnClickListener(this)
        containerView().tv_appointment_start_time.setOnClickListener(this)
        containerView().tv_appointment_end_time.setOnClickListener(this)
        containerView().tv_submit_appointment.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            tv_select_class_room.id -> {
                //选择教室
                initRoomPickView()
            }
            tv_select_department.id -> {
                //选择部门
                initDepartmentPickView()
            }
            tv_appointment_start_time.id -> {
                //选择开始时间
                selectTime(1)
            }
            tv_appointment_end_time.id -> {
                //选择结束时间
                selectTime(2)
            }
            tv_submit_appointment.id -> {
                //提交申请
            }

        }

    }

    private fun selectTime(type: Int) {
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this)
        }
        var currentTime: Calendar? = if (1 == type) startDate else endDate
        if (currentTime == null) {
            currentTime = Calendar.getInstance()
        }
        val booleans = booleanArrayOf(true, true, true, true, false, false)
        val titleText = if (type == 1) "选择开始时间" else "选择结束时间"
        val endCalendar = Calendar.getInstance()
        endCalendar.add(Calendar.YEAR, 2)
        val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
            val simpleDateFormat = SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT_C1)

            if (type == 1) {
                if (endDate != null && date.after(endDate?.time)) {
                    ToastUtils.show("开始时间不能大于结束时间")
                } else {
                    if (startDate == null) {
                        startDate = Calendar.getInstance()
                    }
                    startDate?.time = date
                    tv_appointment_start_time.text = simpleDateFormat.format(date)
                }
            } else {

                if (startDate != null && date.before(startDate?.time)) {
                    ToastUtils.show("结束时间不能小于开始时间")
                } else {
                    if (endDate == null) {
                        endDate = Calendar.getInstance()
                    }
                    endDate?.time = date
                    tv_appointment_end_time.text = simpleDateFormat.format(date)
                }
            }
        }).setTitleText(titleText)
                .setRangDate(Calendar.getInstance(), endCalendar)
                .setDate(currentTime)
                .setType(booleans)//分别对应年月日时分秒，默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(2.0f)
                .build()
                .show()
    }

    private fun initRoomPickView() {
        if (roomCustomOptions == null) {
            cardItem.clear()
            for (a in 1..50) {
                cardItem.add(ClatAtTypeBean("职教室" + a.toString(), a.toString()))
            }
            //自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
            roomCustomOptions = OptionsPickerBuilder(this, OnOptionsSelectListener({ options1, options2, options3, v ->
                run {
                    containerView().tv_select_class_room.text = cardItem[options1].typeName
                }
            })).setLayoutRes(R.layout.pickerview_custom_options, CustomListener { v ->
                val tvSubmit = v.findViewById<View>(R.id.tv_finish)
                val ivCancel = v.findViewById<View>(R.id.iv_cancel)
                val titleView = v.findViewById<TextView>(R.id.tv_select_title)
                titleView.text = "选择职教室"
                tvSubmit.setOnClickListener {
                    roomCustomOptions?.returnData()
                    roomCustomOptions?.dismiss()
                }

                ivCancel.setOnClickListener {
                    roomCustomOptions?.dismiss()
                }

            })
                    .isDialog(true)
                    .setDividerColor(ContextCompat.getColor(pageContext, R.color.main_color))
                    .setTextColorCenter(ContextCompat.getColor(pageContext, R.color.main_color))
                    .setOutSideCancelable(true)
                    .setLineSpacingMultiplier(2f)
                    .build()
//        pvCustomOptions.setpi
            roomCustomOptions?.setPicker(cardItem as List<Nothing>) //添加数据
        }
        roomCustomOptions?.show()

    }

    private fun initDepartmentPickView() {
        if (departmentCustomOptions == null) {
            cardItem.clear()
            for (a in 1..50) {
                cardItem.add(ClatAtTypeBean("部门" + a.toString(), a.toString()))
            }
            //自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
            departmentCustomOptions = OptionsPickerBuilder(this, OnOptionsSelectListener({ options1, options2, options3, v ->
                run {
                    tv_select_department.text = cardItem[options1].typeName
                }
            })).setLayoutRes(R.layout.pickerview_custom_options, CustomListener { v ->
                val tvSubmit = v.findViewById<View>(R.id.tv_finish)
                val ivCancel = v.findViewById<View>(R.id.iv_cancel)
                val titleView = v.findViewById<TextView>(R.id.tv_select_title)
                titleView.text = "选择部门"
                tvSubmit.setOnClickListener {
                    departmentCustomOptions?.returnData()
                    departmentCustomOptions?.dismiss()
                }

                ivCancel.setOnClickListener {
                    departmentCustomOptions?.dismiss()
                }

            })
                    .isDialog(true)
                    .setDividerColor(ContextCompat.getColor(pageContext, R.color.main_color))
                    .setTextColorCenter(ContextCompat.getColor(pageContext, R.color.main_color))
                    .setOutSideCancelable(true)
                    .setLineSpacingMultiplier(2f)
                    .build()
//        pvCustomOptions.setpi
            departmentCustomOptions?.setPicker(cardItem as List<Nothing>) //添加数据
        }
        departmentCustomOptions?.show()

    }

}