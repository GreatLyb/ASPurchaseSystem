package com.lyb.purchasesystem.adapter.classroom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.classroom.ClassRoomBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.view.swip.SwipeMenuLayout

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 15:10
 */
class ClassRoomAppointmentListAdapter(context: Context, list: MutableList<ClassRoomBean>, val adapterViewClickListener: AdapterViewClickListener, val isShowDel: Boolean) : LyBaseAdapter<ClassRoomBean>(context, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        if (convertView == null) {
            holder = ViewHolder()
            itemView = View.inflate(context, R.layout.item_class_room_appointment, null);
            holder.classRoomNameTextView = itemView.findViewById(R.id.tv_class_room_name)
            holder.startTimeTextView = itemView.findViewById(R.id.tv_class_room_start_time)
            holder.endTimeTextView = itemView.findViewById(R.id.tv_class_room_end_time)
            holder.delTextView = itemView.findViewById(R.id.tv_appointment_del)
            holder.classRoomSwipeMenuLayout = itemView.findViewById(R.id.sw_class_room_layout)
            holder.itemLinearLayout = itemView.findViewById(R.id.llayout_class_item)

            itemView.setTag(holder)
        } else {
            itemView = convertView;
            holder = itemView.getTag() as ViewHolder
        }

        val model = list[position]
        if (position % 2 == 0) {
            holder.classRoomSwipeMenuLayout.setBackgroundResource(R.color.background_color)
        } else {
            holder.classRoomSwipeMenuLayout.setBackgroundResource(R.color.white)
        }
        holder.classRoomNameTextView.text = model.classRoomName
        holder.startTimeTextView.text = model.startTime
        holder.endTimeTextView.text = model.endTime
        if (isShowDel) {
            holder.classRoomSwipeMenuLayout.isSwipeEnable = true
            holder.delTextView.setOnClickListener(clickListener(position))
        } else {
            holder.classRoomSwipeMenuLayout.isSwipeEnable = false
        }
        holder.itemLinearLayout.setOnClickListener(clickListener(position))
        return itemView
    }

    inner class ViewHolder {
        lateinit var classRoomSwipeMenuLayout: SwipeMenuLayout
        lateinit var classRoomNameTextView: TextView
        lateinit var startTimeTextView: TextView
        lateinit var endTimeTextView: TextView
        lateinit var delTextView: TextView
        lateinit var itemLinearLayout: LinearLayout
    }

    inner class clickListener(val position: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
//            Log.i("Lyb","position=="+position)
            adapterViewClickListener.adapterViewClick(position, v)
        }
    }

}