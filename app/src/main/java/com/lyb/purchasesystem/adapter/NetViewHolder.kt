package com.lyb.purchasesystem.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.BannerData
import com.zhpan.bannerview.BaseViewHolder
import com.zhpan.bannerview.utils.BannerUtils
import kotlinx.android.synthetic.main.item_banner_view.view.*


class NetViewHolder constructor(itemView: View) : BaseViewHolder<BannerData>(itemView) {

    init {
        itemView.banner_image.setRoundCorner(BannerUtils.dp2px(0f))
    }

    override fun bindData(data: BannerData?, position: Int, pageSize: Int) {
        println("data?.imagePath==="+data?.imagePath)
        Glide.with(itemView.banner_image).load(data?.imagePath).placeholder(R.drawable.default_img_round).into(itemView.banner_image);
        itemView.tv_describe.text=data?.titleName
    }

}