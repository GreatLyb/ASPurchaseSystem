package com.lyb.purchasesystem.adapter.warehouse

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.warehouse.SecondLevelBean
import com.lysoft.baseproject.adapter.LyBaseAdapter

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-14 15:41
 */
class SecondGrideAdapter(context: Context?, list: MutableList<SecondLevelBean>?) : LyBaseAdapter<SecondLevelBean>(context, list) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: Holder
        val view: View;
        if (convertView == null) {
            holder = Holder()
            view = View.inflate(context, R.layout.item_second_gride, null)
            holder.tvTitle = view.findViewById(R.id.tv_second_title)
            view.setTag(holder)
        } else {
            view = convertView
            holder = view.getTag() as Holder
        }
        holder.tvTitle.text = list.get(position).secondName
        if (list.get(position).isSelect) {
            holder.tvTitle.setBackgroundResource(R.drawable.shape_second_level_select_bg)
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.main_color))
            holder.tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            holder.tvTitle.setBackgroundResource(R.drawable.shape_second_level_bg)
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.defaultGrayText))
            holder.tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        return view
    }


    private inner class Holder {
        lateinit var tvTitle: TextView;
    }

}