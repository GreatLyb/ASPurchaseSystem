package com.lyb.purchasesystem.adapter.main

import android.view.View
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.main.BannerData
import com.zhpan.bannerview.BaseBannerAdapter

class BannerAdapter : BaseBannerAdapter<BannerData, NetViewHolder>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner_view
    }

    override fun createViewHolder(itemView: View?, viewType: Int): NetViewHolder {
        return NetViewHolder(itemView!!)
    }

    override fun onBind(holder: NetViewHolder?, data: BannerData?, position: Int, pageSize: Int) {
        holder?.bindData(data,position,pageSize)
    }

}