package com.lyb.purchasesystem.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.classroom.ClassRoomAppointmentListAdapter
import com.lyb.purchasesystem.bean.classroom.ClassRoomBean
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIListActivity
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import kotlinx.android.synthetic.main.item_class_room_appointment.*

/**
 * ASPurchaseSystem
 * 类描述：教室预约 列表
 * 类传参： type 1 首页  2.我的
 * @Author： create by Lyb on 2020-05-07 10:11
 */
class ClassRoomAppointmentActivity : AdapterViewClickListener, BaseUIListActivity<ClassRoomBean>() {
    var type: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View.inflate(pageContext, R.layout.view_classroom_top_view, null)
        contentView().addView(view, 1)
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.add_appiontment, 0, 0, 0)
        topViewManager().moreLayout().setOnClickListener { v ->
            startActivity(Intent(pageContext, AddAppointmentActivity::class.java))
        }
        type = intent.getIntExtra("type", 1)
        var titleText = ""
        if (type == 1) titleText = "教室预约" else titleText = "我的预约"
        topViewManager().titleTextView().text = "$titleText"
        onPageLoad()
    }

    override fun getListData(callBack: BaseCallBack?) {
        var list = mutableListOf<ClassRoomBean>()
        for (a in 1..30) {
            if (a < 10) {
                list.add(ClassRoomBean(a.toString(), "00" + a.toString() + "教室", "李老师", "安全部", a.toString(), "17562011005", "2020-05-0" + a.toString() + " " + a.toString() + "时", "2020-05-0" + a.toString() + " " + (a + 1).toString() + "时"))
            } else {
                list.add(ClassRoomBean(a.toString(), "0" + a.toString() + "教室", "李老师", "安全部", a.toString(), "17562011005", "2020-05-" + a.toString() + " " + a.toString() + "时", "2020-05-" + a.toString() + " " + (a + 1).toString() + "时"))
            }
        }
        callBack?.callBack(list)
    }

    override fun instanceAdapter(list: MutableList<ClassRoomBean>): BaseAdapter {
        return ClassRoomAppointmentListAdapter(pageContext, list, this, type == 2)
    }

    override fun itemClickListener(position: Int) {
    }

    override fun adapterViewClick(position: Int, view: View) {
        if (view.id == tv_appointment_del.id) {
            //删除
            MessageDialog.show(this, "提示", "确认要删除该条预约吗？", "确定", "取消").setOnOkButtonClickListener { baseDialog, v ->
                baseDialog.doDismiss()
                com.hjq.toast.ToastUtils.show("删除")
                true
            }.onCancelButtonClickListener = OnDialogButtonClickListener({ baseDialog, v ->
                baseDialog.doDismiss()
                true
            })
        } else {
            //详情
            val intent = Intent(pageContext, AppointmentDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }
}