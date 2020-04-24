package com.lyb.purchasesystem.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.MsgBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.view.swip.SwipeMenuLayout

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 15:10
 */
class MsgListAdapter(context: Context, list: MutableList<MsgBean>, val adapterViewClickListener: AdapterViewClickListener) : LyBaseAdapter<MsgBean>(context, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        if (convertView == null) {
            holder = ViewHolder()
            itemView = View.inflate(context, R.layout.item_msg_layout, null);
            holder.msgImageView = itemView.findViewById(R.id.img_msg_type)
            holder.msgTitleTextView = itemView.findViewById(R.id.tv_msg_title)
            holder.msgContentTextView = itemView.findViewById(R.id.tv_msg_content)
            holder.msgTimeTextView = itemView.findViewById(R.id.tv_msg_time)
            holder.readStateTextView = itemView.findViewById(R.id.tv_msg_read)
            holder.delTextView = itemView.findViewById(R.id.tv_msg_del)
            holder.swipeMenuLayout = itemView.findViewById(R.id.sw_layout)
            itemView.setTag(holder)
        } else {
            itemView = convertView;
            holder = itemView.getTag() as ViewHolder
        }
        val model = list[position]
        when (model.msgType) {
            "1" ->
                holder.msgImageView.setImageResource(R.drawable.warn_msg)
            "5" ->
                holder.msgImageView.setImageResource(R.drawable.error_msg)
            "9" ->
                holder.msgImageView.setImageResource(R.drawable.right_msg)

            else -> {
                holder.msgImageView.setImageResource(R.drawable.sys_msg)
            }
        }
        holder.msgTitleTextView.text = model.msgTitle
        holder.msgContentTextView.text = model.msgContent
        holder.msgTimeTextView.text = model.msgTime
        holder.msgTimeTextView.visibility = if (model.isRead.equals("0")) View.VISIBLE else View.GONE
        holder.delTextView.setOnClickListener(clickListener(holder.swipeMenuLayout, position))
//        holder.delTextView.setOnClickListener(View.OnClickListener {
//           Log.i("Lyb","aaaaaaaaaa")
//        })
        return itemView
    }

    inner class ViewHolder {
        lateinit var msgImageView: ImageView
        lateinit var msgTitleTextView: TextView
        lateinit var msgContentTextView: TextView
        lateinit var msgTimeTextView: TextView
        lateinit var readStateTextView: TextView
        lateinit var delTextView: TextView
        lateinit var swipeMenuLayout: SwipeMenuLayout
    }

    inner class clickListener(val swipeMenuLayout: SwipeMenuLayout, val position: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            Log.i("Lyb", "aaaaaaaaaa")
            swipeMenuLayout.quickClose()
            adapterViewClickListener.adapterViewClick(position, v)
        }
    }

}