package com.lyb.purchasesystem.fragment.purchase

import android.content.Intent
import android.view.View
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.hjq.toast.ToastUtils
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.adapter.purchase.PurchaseListAdapter
import com.lyb.purchasesystem.bean.purchase.PurchaseBean
import com.lyb.purchasesystem.consta.Constants
import com.lyb.purchasesystem.ui.suggestions.SuggestionsInfoActivity
import com.lysoft.baseproject.activity.BaseUIListFragment
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import com.lysoft.baseproject.imp.LoadStatus

/**
 * ASPurchaseSystem
 * 类描述： 采购列表
 * 类传参：type(int) 1.全部  2.待处理 3.已处理 4.我的
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class PurchaseListFragment(var type: Int, var appCompatActivity: AppCompatActivity) : AdapterViewClickListener, BaseUIListFragment<PurchaseBean>() {
    override fun onCreate() {
        super.onCreate()
        topViewManager().topView().visibility = View.GONE
        loadViewManager().changeLoadState(LoadStatus.LOADING)
    }

    override fun getListData(callBack: BaseCallBack) {
        var purchaseList = mutableListOf<PurchaseBean>()
        for (a in 0..30) {
            var state = "";
            if (a < 3) {
                state = a.toString()
            } else {
                state = "2"
            }
            purchaseList.add(PurchaseBean("电脑", "i7 16G", "9000", "10台", "2020-05-06", "2020-04-05", state, "张三"))
        }
        callBack.callBack(purchaseList)
    }

    override fun itemClickListener(position: Int) {
        startActivity(Intent(pageContext, SuggestionsInfoActivity::class.java))
    }

    override fun adapterViewClick(position: Int, view: View?) {
        if (pageListData[position].purchaseDealState.equals("1")) {
            MessageDialog.show(appCompatActivity, "提示", "确认要删除该条申请吗？", "确定", "取消").setOnOkButtonClickListener { baseDialog, v ->
                baseDialog.doDismiss()
                true
            }.onCancelButtonClickListener = OnDialogButtonClickListener({ baseDialog, v ->
                baseDialog.doDismiss()
                true
            })
        } else {
            ToastUtils.show("已处理的申请不能删除")
        }

    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }

    override fun instanceAdapter(list: MutableList<PurchaseBean>): BaseAdapter {
        return PurchaseListAdapter(pageContext, list, this, 4 == type)

    }

}