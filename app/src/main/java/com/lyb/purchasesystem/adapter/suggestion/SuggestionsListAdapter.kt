package com.lyb.purchasesystem.adapter.suggestion

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.suggertion.SuggertionBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：dealType 0:删除 1.处理
 * @Author： create by Lyb on 2020-04-21 15:10
 */
class SuggestionsListAdapter(context: Context, list: MutableList<SuggertionBean>, val adapterViewClickListener: AdapterViewClickListener, val leftScrollType: Int) : LyBaseAdapter<SuggertionBean>(context, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        if (convertView == null) {
            holder = ViewHolder()
            itemView = View.inflate(context, R.layout.item_suggestions, null);
            holder.stateImageView = itemView.findViewById(R.id.img_state_img)
            holder.stateTextView = itemView.findViewById(R.id.tv_state)
            holder.suggestionTitleTextView = itemView.findViewById(R.id.tv_suggestion_title)
            holder.suggestionContentTextView = itemView.findViewById(R.id.tv_suggestion_content)
            holder.suggestionTimeTextView = itemView.findViewById(R.id.tv_suggestion_time)
            holder.dealImageView = itemView.findViewById(R.id.img_deal)
            itemView.setTag(holder)
        } else {
            itemView = convertView;
            holder = itemView.getTag() as ViewHolder
        }

        val model = list[position]
        when (model.status) {
            "0" -> {
                holder.stateImageView.setImageResource(R.drawable.warn_msg)
                holder.stateTextView.text = "建议未被处理"
            }
            "1" -> {
                holder.stateImageView.setImageResource(R.drawable.error_msg)
                holder.stateTextView.text = "建议被驳回"
            }
            "2" -> {
                holder.stateImageView.setImageResource(R.drawable.right_msg)
                holder.stateTextView.text = "建议被采纳"
            }
        }
        when (leftScrollType) {
            0 -> {
                holder.dealImageView.setImageResource(R.drawable.del_icon)
            }
            1 -> {
                holder.dealImageView.setImageResource(R.drawable.deal_icon)
            }
        }

        holder.suggestionTitleTextView.text = model.suggestName
        holder.suggestionContentTextView.text = model.suggestContent
        holder.suggestionTimeTextView.text = model.insertTime
        holder.dealImageView.setOnClickListener(clickListener(position))
        return itemView
    }

    inner class ViewHolder {
        lateinit var stateImageView: ImageView
        lateinit var dealImageView: ImageView
        lateinit var stateTextView: TextView
        lateinit var suggestionTitleTextView: TextView
        lateinit var suggestionContentTextView: TextView
        lateinit var suggestionTimeTextView: TextView
    }

    inner class clickListener(val position: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            adapterViewClickListener.adapterViewClick(position, v)
        }
    }

}