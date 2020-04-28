package com.lyb.purchasesystem.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.ClapAtWillBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.view.LyAtMostGridView

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 15:10
 */
class ClapAtWillListAdapter(context: Context, list: MutableList<ClapAtWillBean>, val adapterViewClickListener: AdapterViewClickListener) : LyBaseAdapter<ClapAtWillBean>(context, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        if (convertView == null) {
            holder = ViewHolder()
            itemView = View.inflate(context, R.layout.item_clap_at, null);
            holder.clapAtTitleTextView = itemView.findViewById(R.id.tv_clap_at_title)
            holder.clapAtContentTextView = itemView.findViewById(R.id.tv_clap_at_content)
            holder.clapAtTimeTextView = itemView.findViewById(R.id.tv_clap_at_time)
            holder.gridView = itemView.findViewById(R.id.gv_img)
            holder.dealstateTextView = itemView.findViewById(R.id.tv_clap_at_deal_state)
            holder.dealImageView = itemView.findViewById(R.id.img_deal)
            holder.clapAtTitleCardView = itemView.findViewById(R.id.cv_clat_list)
            itemView.setTag(holder)
        } else {
            itemView = convertView;
            holder = itemView.getTag() as ViewHolder
        }

        val model = list[position]
        when (model.dealState) {
            "0" -> {
                holder.dealstateTextView.setBackgroundResource(R.drawable.shape_deal_state_1_bg)
                holder.dealstateTextView.text = "等待处理"
            }
            "1" -> {
                holder.dealstateTextView.setBackgroundResource(R.drawable.shape_deal_state_2_bg)
                holder.dealstateTextView.text = "完成处理"
            }
            "2" -> {
                holder.dealstateTextView.setBackgroundResource(R.drawable.shape_deal_state_3_bg)
                holder.dealstateTextView.text = "处理时间超时"
            }
            "3" -> {
                holder.dealstateTextView.setBackgroundResource(R.drawable.shape_deal_state_3_bg)
                holder.dealstateTextView.text = "超时未处理"
            }
        }

        holder.clapAtTitleTextView.text = model.clapAtTitle
        holder.clapAtContentTextView.text = model.clapAtContent
        holder.clapAtTimeTextView.text = "发布时间：" + model.clapAtTime
        holder.dealImageView.setOnClickListener(clickListener(position))
        holder.clapAtTitleCardView.setOnClickListener(clickListener(position))
        holder.gridView.adapter = ImageGridViewAdapter(context, list[position].mutableList)
        return itemView
    }

    inner class ViewHolder {
        lateinit var clapAtTitleCardView: CardView
        lateinit var clapAtTitleTextView: TextView
        lateinit var clapAtContentTextView: TextView
        lateinit var clapAtTimeTextView: TextView
        lateinit var gridView: LyAtMostGridView
        lateinit var dealstateTextView: TextView
        lateinit var dealImageView: ImageView

    }

    inner class clickListener(val position: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            Log.i("Lyb", "aaaaaaaaaa")
            adapterViewClickListener.adapterViewClick(position, v)
        }
    }

}