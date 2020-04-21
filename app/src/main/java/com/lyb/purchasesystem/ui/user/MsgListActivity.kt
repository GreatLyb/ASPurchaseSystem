package com.lyb.purchasesystem.ui.user

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.toast.ToastUtils
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.adapter.MsgAdapter
import com.lyb.purchasesystem.bean.MsgBean
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIListActivity
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import com.lysoft.baseproject.imp.LoadStatus

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 14:14
 */
@Route(path = "/app/MsgListActivity")
class MsgListActivity : BaseUIListActivity<MsgBean>(), AdapterViewClickListener {
    var isFirst: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "系统消息"
        topViewManager().moreTextView().text = "清空"
        topViewManager().moreLayout().setOnClickListener({
            Log.i("Lyb", "清空===")
            MessageDialog.show(this, "提示", "确定要清空系统消息吗", "确定", "取消").setOnOkButtonClickListener { baseDialog, v ->
                baseDialog.doDismiss()
                pageListData.clear()
                PageAdapter().notifyDataSetChanged()
                topViewManager().moreTextView().visibility = View.GONE
                loadViewManager().changeLoadState(LoadStatus.NODATA)
                true
            }.onCancelButtonClickListener = OnDialogButtonClickListener { baseDialog, v ->

                baseDialog.doDismiss()
                true
            }


        })
        loadViewManager().changeLoadState(LoadStatus.LOADING)
    }

    override fun getListData(callBack: BaseCallBack) {
        object : CountDownTimer(1500, 1000) {

            override fun onTick(millisUntilFinished: Long) {
//                if (isFirst) {
//                    isFirst = false
//                    onFinish()
//                    cancel()
//                }
            }

            override fun onFinish() {
                loadViewManager().changeLoadState(LoadStatus.SUCCESS)
                var list = mutableListOf<MsgBean>()
                if (pageIndex < 3) {
                    for (a in 1..20) {
                        list.add(MsgBean("消息标题" + a, "我是内我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容我是内容内容容内容我是内容内容我是内容内容", "2020-04-21", "0", a.toString() + "", "www"))
                    }
                }
                callBack.callBack(list)
            }
        }.start()
    }

    override fun instanceAdapter(list: MutableList<MsgBean>?): BaseAdapter {
        return MsgAdapter(this, list!!, this)
    }

    override fun itemClickListener(position: Int) {

    }

    override fun adapterViewClick(position: Int, view: View?) {
        ToastUtils.show("删除成功" + position)
        pageListData.removeAt(position)
        if (pageListData.size < 1) {
            loadViewManager().changeLoadState(LoadStatus.NODATA)
            topViewManager().moreTextView().visibility = View.GONE
        } else {
            PageAdapter().notifyDataSetChanged()
        }
    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }

}