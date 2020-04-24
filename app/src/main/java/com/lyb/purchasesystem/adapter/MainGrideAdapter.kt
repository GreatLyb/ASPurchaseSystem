package com.lyb.purchasesystem.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.MainIconBean
import com.lysoft.baseproject.adapter.LyBaseAdapter

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-14 15:41
 */
class MainGrideAdapter(context: Context?, list: MutableList<MainIconBean>?) : LyBaseAdapter<MainIconBean>(context, list) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: Holder
        val view: View;
        if (convertView == null) {
            holder = Holder()
            view = View.inflate(context, R.layout.item_main_gride, null)
            holder.iconImageView = view.findViewById(R.id.img_icon)
            holder.iconTitle = view.findViewById(R.id.tv_icon_title)
            view.setTag(holder)
        } else {
            view = convertView
            holder = view.getTag() as Holder
        }
        holder.iconTitle.text = list.get(position).iconTitle
        val imageDrawable: Int
        when (position) {
            0 -> {
                imageDrawable = R.drawable.apply_buy
            }

            1 -> imageDrawable = R.drawable.weixiujilu
            2 -> imageDrawable = R.drawable.tijianbaogao
            3 -> imageDrawable = R.drawable.yuyue
            4 -> imageDrawable = R.drawable.cangku_guanli
            5 -> imageDrawable = R.drawable.suishou_camera
            6 -> imageDrawable = R.drawable.yijianxiang
            7 -> imageDrawable = R.drawable.white_zhanwei
            8 -> imageDrawable = R.drawable.white_zhanwei
            else -> {
                imageDrawable = R.drawable.kehu
            }
        }
        holder.iconImageView.setImageResource(imageDrawable)
        return view
    }


    private inner class Holder {
        lateinit var iconImageView: ImageView;
        lateinit var iconTitle: TextView;
    }

}