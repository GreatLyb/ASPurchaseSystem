package com.lyb.purchasesystem.fragment.warehouse

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.hjq.toast.ToastUtils
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.MessageDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.R.color
import com.lyb.purchasesystem.R.drawable
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
import kotlinx.android.synthetic.main.frag_ware_house_list.*
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
    lateinit var wareHouseMenuAdapter: WareHouseMenuAdapter


    override fun onCreate() {
        super.onCreate()
        topViewManager().topView().visibility = View.GONE
        loadViewManager().changeLoadState(LoadStatus.LOADING)
        drawerLayout = containerView().findViewById(R.id.draw_layout)
        setDrawListViewInfo();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        containerView().rb_filter.setOnClickListener { v ->
            drawerLayout.openDrawer(Gravity.RIGHT)
//            drawerLayout.setScrimColor(ContextCompat.getColor(pageContext,R.color.text_gray))
        }
        containerView().rb_supply_filter_price.setOnClickListener { v ->
            val tag = v.getTag()
            Log.i("Lyb", "aaaaaaatag==" + tag)

        }
        initListener()
    }

    private fun initListener() {
        containerView().rb_supply_filter_price.setOnClickListener { v ->
            val tag: String = v.getTag() as String
            Log.i("Lyb", "tag==" + tag)
            if (!containerView().rb_supply_filter_publish_time.getTag().toString().equals("0")) {
                containerView().rb_supply_filter_publish_time.setTag("0")
                containerView().rb_supply_filter_publish_time.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_default, 0)
                containerView().rb_supply_filter_publish_time.setTextColor(ContextCompat.getColor(pageContext, color.text_black))
            }
            when (tag) {
                "0" -> {
                    v.setTag("1")
                    containerView().rb_supply_filter_price.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_down, 0)
                    containerView().rb_supply_filter_price.setTextColor(ContextCompat.getColor(pageContext, color.main_color))
                }
                "1" -> {
                    v.setTag("2")
                    containerView().rb_supply_filter_price.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_up, 0)
                    containerView().rb_supply_filter_price.setTextColor(ContextCompat.getColor(pageContext, color.main_color))

                }
                "2" -> {
                    v.setTag("0")
                    containerView().rb_supply_filter_price.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_default, 0)
                    containerView().rb_supply_filter_price.setTextColor(ContextCompat.getColor(pageContext, color.text_black))
                }


            }

        }
        containerView().rb_supply_filter_publish_time.setOnClickListener { v ->
            var tag = v.getTag()
            if (!containerView().rb_supply_filter_price.getTag().toString().equals("0")) {
                containerView().rb_supply_filter_price.setTag("0")
                containerView().rb_supply_filter_price.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_default, 0)
                containerView().rb_supply_filter_price.setTextColor(ContextCompat.getColor(pageContext, color.text_black))
            }
            when (tag.toString()) {
                "0" -> {
                    v.setTag("1")
                    containerView().rb_supply_filter_publish_time.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_down, 0)
                    containerView().rb_supply_filter_publish_time.setTextColor(ContextCompat.getColor(pageContext, color.main_color))
                }
                "1" -> {
                    v.setTag("2")
                    containerView().rb_supply_filter_publish_time.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_up, 0)
                    containerView().rb_supply_filter_publish_time.setTextColor(ContextCompat.getColor(pageContext, color.main_color))

                }
                "2" -> {
                    v.setTag("0")
                    containerView().rb_supply_filter_publish_time.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable.supply_sort_default, 0)
                    containerView().rb_supply_filter_publish_time.setTextColor(ContextCompat.getColor(pageContext, color.text_black))

                }


            }

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
        rb_filter.text = secondLevelBean.secondName
    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }

    override fun instanceAdapter(list: MutableList<PurchaseBean>): BaseAdapter {
        return PurchaseListAdapter(pageContext, list, this, false)

    }

}