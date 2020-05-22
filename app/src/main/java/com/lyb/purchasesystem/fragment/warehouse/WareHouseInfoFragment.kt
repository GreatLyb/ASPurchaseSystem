package com.lyb.purchasesystem.fragment.warehouse

import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.BaseAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.hjq.toast.ToastUtils
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.purchase.PurchaseListAdapter
import com.lyb.purchasesystem.adapter.warehouse.WareHouseMenuAdapter
import com.lyb.purchasesystem.bean.purchase.PurchaseBean
import com.lyb.purchasesystem.bean.warehouse.FirstLevelBean
import com.lyb.purchasesystem.bean.warehouse.SecondLevelBean
import com.lyb.purchasesystem.consta.Constants
import com.lyb.purchasesystem.ui.purchase.PurchaseInfoActivity
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import com.lysoft.baseproject.imp.LoadStatus
import kotlinx.android.synthetic.main.frag_ware_house_list.view.*
import java.util.*

/**
 * ASPurchaseSystem
 * 类描述： 采购列表
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class WareHouseInfoFragment(var appCompatActivity: AppCompatActivity) : AdapterViewClickListener, WareHouseListFragment<PurchaseBean>() {
    val firstLevelBeans = mutableListOf<FirstLevelBean>()
    var lastFirstPosition = -1
    var lastSecondPosition = -1
    lateinit var drawerLayout: DrawerLayout
    lateinit var wareHouseMenuAdapter: WareHouseMenuAdapter;
    override fun onCreate() {
        super.onCreate()
        topViewManager().topView().visibility = View.GONE
        loadViewManager().changeLoadState(LoadStatus.LOADING)
        val classRadioButton = containerView().findViewById<RadioButton>(R.id.rb_filter)
        drawerLayout = containerView().findViewById<DrawerLayout>(R.id.draw_layout)
        setDrawListViewInfo();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        classRadioButton.setOnClickListener { v ->
            drawerLayout.openDrawer(Gravity.RIGHT)
//            drawerLayout.setScrimColor(ContextCompat.getColor(pageContext,R.color.text_gray))
        }
    }

    fun setDrawListViewInfo() {
        for (a in 1..50) {
            val secondList = mutableListOf<SecondLevelBean>()
            for (b in 1..20) {
                val secondLevelBean = SecondLevelBean(b, "二级类别" + b, a)
                secondList.add(secondLevelBean)
            }
            val firstLevelBean = FirstLevelBean(a, "一级类别" + a, secondList as ArrayList<SecondLevelBean>?)
            firstLevelBeans.add(firstLevelBean)
        }
        wareHouseMenuAdapter = WareHouseMenuAdapter(pageContext, firstLevelBeans, this)
        containerView().ex_list.setAdapter(wareHouseMenuAdapter)
        containerView().ex_list.setOnGroupClickListener({ parent, v, groupPosition, id ->
            false
        })
        //设置分组的监听
        containerView().ex_list.setOnChildClickListener({ parent, v, groupPosition, childPosition, id ->
            ToastUtils.show("点击了子布局")
            true
        })
        //设置子项布局监听
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
            purchaseList.add(PurchaseBean("电脑", "i7 16G", "9000", "10台", "2020-05-06", "2020-04-05", "我们需要几台电脑办公，请尽快采买，谢谢！！！", "张三", state, "李四", "2020-05-01", "你这个建议不错", "人资部"))
        }
        callBack.callBack(purchaseList)
    }

    override fun itemClickListener(position: Int) {
        val intent = Intent(pageContext, PurchaseInfoActivity::class.java)
        intent.putExtra("model", pageListData[position])
        startActivity(intent)
    }

    override fun adapterViewClick(position: Int, view: View?) {
        if (pageListData[position].purchaseDealState.equals("0")) {
            MessageDialog.show(appCompatActivity, "提示", "确认要删除该条采购申请吗？", "确定", "取消").setOnOkButtonClickListener { baseDialog, v ->
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

    /**
     * 二级菜单的点击事件
     */
    override fun adapterViewClick(firstPosition: Int, secondPosition: Int, view: View?) {
        if (firstPosition == lastFirstPosition && secondPosition == lastSecondPosition) {
            //不做处理
            return
        }
        if (lastFirstPosition >= 0 && lastSecondPosition >= 0) {
            firstLevelBeans.get(lastFirstPosition).isSelect = false
            firstLevelBeans.get(lastFirstPosition).secondLevelMenus[lastSecondPosition].isSelect = false
        }
        lastFirstPosition = firstPosition
        lastSecondPosition = secondPosition
        val secondLevelBean = firstLevelBeans.get(firstPosition).secondLevelMenus[secondPosition]
        secondLevelBean.isSelect = true
        firstLevelBeans.get(lastFirstPosition).isSelect = true
        wareHouseMenuAdapter.notifyDataSetChanged()
        drawerLayout.closeDrawers()
        ToastUtils.show(secondLevelBean.secondName)
    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }

    override fun instanceAdapter(list: MutableList<PurchaseBean>): BaseAdapter {
        return PurchaseListAdapter(pageContext, list, this, false)

    }

}