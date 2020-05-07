package com.lyb.purchasesystem.adapter.purchase

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.purchase.PurchaseBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 15:10
 */
class PurchaseListAdapter(context: Context, list: MutableList<PurchaseBean>, val adapterViewClickListener: AdapterViewClickListener, val isShowDel: Boolean) : LyBaseAdapter<PurchaseBean>(context, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        if (convertView == null) {
            holder = ViewHolder()
            itemView = View.inflate(context, R.layout.item_purchase_list, null);
            holder.purchaseCardView = itemView.findViewById(R.id.cd_purchase)
            holder.purchaseNameTextView = itemView.findViewById(R.id.tv_purchase_name)
            holder.purchaseSpecificationsTextView = itemView.findViewById(R.id.tv_purchase_specifications)
            holder.purchasePriceTextView = itemView.findViewById(R.id.tv_purchase_price)
            holder.purchaseNumTextView = itemView.findViewById(R.id.tv_purchase_num)
            holder.purchasePersonTextView = itemView.findViewById(R.id.tv_purchase_publish_person)
            holder.purchaseTimeTextView = itemView.findViewById(R.id.tv_purchase_apply_time)
            holder.purchaseStateTextView = itemView.findViewById(R.id.tv_purchase_state)
            holder.purchaseDealImageView = itemView.findViewById(R.id.img_purchase_deal)
            holder.purchaseDepartmentTextView = itemView.findViewById(R.id.tv_purchase_publish_department)

            itemView.setTag(holder)
        } else {
            itemView = convertView;
            holder = itemView.getTag() as ViewHolder
        }

        val model = list[position]
        when (model.purchaseDealState) {
            "0" -> {
                holder.purchaseStateTextView.setTextColor(ContextCompat.getColor(context, R.color.wait_deal_color))
                holder.purchaseStateTextView.text = "等待处理"
            }
            "1" -> {
                //已同意
                holder.purchaseStateTextView.setTextColor(ContextCompat.getColor(context, R.color.main_color))
                holder.purchaseStateTextView.text = "已同意"
            }
            "2" -> {
                //已拒绝
                holder.purchaseStateTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
                holder.purchaseStateTextView.text = "已拒绝"
            }
        }

        holder.purchaseNameTextView.text = "采购名称：" + model.purchaseName
        holder.purchaseSpecificationsTextView.text = "规格型号：" + model.purchasespecifications
        holder.purchasePriceTextView.text = "物品单价：" + model.purchasePrice
        holder.purchaseNumTextView.text = "采购数量：" + model.purchaseNum
        holder.purchasePersonTextView.text = "采购申请人：" + model.purchasePerson
        holder.purchaseTimeTextView.text = "申请时间：" + model.purchasePublishTime
        holder.purchaseDepartmentTextView.text = "申请部门：" + model.purchaseDepartment
        if (isShowDel && model.purchaseDealState.equals("0")) {
            holder.purchaseDealImageView.visibility = View.VISIBLE
            holder.purchaseDealImageView.setOnClickListener(clickListener(position))
        } else {
            holder.purchaseDealImageView.visibility = View.GONE
        }
        return itemView
    }

    inner class ViewHolder {
        lateinit var purchaseCardView: CardView
        lateinit var purchaseNameTextView: TextView
        lateinit var purchaseSpecificationsTextView: TextView
        lateinit var purchasePriceTextView: TextView
        lateinit var purchaseNumTextView: TextView
        lateinit var purchasePersonTextView: TextView
        lateinit var purchaseTimeTextView: TextView
        lateinit var purchaseStateTextView: TextView
        lateinit var purchaseDepartmentTextView: TextView
        lateinit var purchaseDealImageView: ImageView
    }

    inner class clickListener(val position: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            adapterViewClickListener.adapterViewClick(position, v)
        }
    }

}