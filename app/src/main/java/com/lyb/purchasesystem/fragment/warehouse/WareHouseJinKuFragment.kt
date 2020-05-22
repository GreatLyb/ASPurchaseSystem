package com.lyb.purchasesystem.fragment.warehouse

import android.content.Intent
import android.view.View
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.lyb.goodssystem.adapter.warehouse.WareHouseHomeAdapter
import com.lyb.purchasesystem.bean.warehouse.FirstLevelBean
import com.lyb.purchasesystem.bean.warehouse.GoodsBean
import com.lyb.purchasesystem.consta.Constants
import com.lyb.purchasesystem.ui.warehouse.WareHouseItemDetailActivity
import com.lysoft.baseproject.activity.BaseUIListFragment
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import com.lysoft.baseproject.imp.LoadStatus

/**
 * ASPurchaseSystem
 * 类描述： 进库列表
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class WareHouseJinKuFragment(var appCompatActivity: AppCompatActivity) : AdapterViewClickListener, BaseUIListFragment<GoodsBean>() {
    val firstLevelBeans = mutableListOf<FirstLevelBean>()


    override fun onCreate() {
        super.onCreate()
        topViewManager().topView().visibility = View.GONE
        loadViewManager().changeLoadState(LoadStatus.LOADING)
    }


    override fun getListData(callBack: BaseCallBack) {
        var purchaseList = mutableListOf<GoodsBean>()
        for (a in 0..30) {
            purchaseList.add(GoodsBean("", "电脑", "i7 16G", "9000/台", "10台", "2020-05-06"))
        }
        callBack.callBack(purchaseList)
    }

    override fun instanceAdapter(list: MutableList<GoodsBean>): BaseAdapter {
        return WareHouseHomeAdapter(pageContext, list, this)

    }

    override fun itemClickListener(position: Int) {
        val intent = Intent(pageContext, WareHouseItemDetailActivity::class.java)
        intent.putExtra("model", pageListData[position])
        startActivity(intent)
    }

    override fun adapterViewClick(position: Int, view: View?) {

    }


    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }


}