package com.lyb.goodssystem.adapter.warehouse

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.warehouse.GoodsBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener

/**
 * ASgoodsSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 15:10
 */
class WareHouseHomeAdapter(context: Context, list: MutableList<GoodsBean>, val adapterViewClickListener: AdapterViewClickListener) : LyBaseAdapter<GoodsBean>(context, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        if (convertView == null) {
            holder = ViewHolder()
            itemView = View.inflate(context, R.layout.item_ware_home_list, null);
            holder.goodsCardView = itemView.findViewById(R.id.cd_goods)
            holder.goodsImage = itemView.findViewById(R.id.image_goods_image)
            holder.goodsNameTextView = itemView.findViewById(R.id.tv_goods_name)
            holder.goodsSpecificationsTextView = itemView.findViewById(R.id.tv_goods_specifications)
            holder.goodsPriceTextView = itemView.findViewById(R.id.tv_goods_price)
            holder.goodsStockTextView = itemView.findViewById(R.id.tv_stock_num)
            holder.updateTimeTextView = itemView.findViewById(R.id.tv_update_time)

            itemView.setTag(holder)
        } else {
            itemView = convertView;
            holder = itemView.getTag() as ViewHolder
        }

        val model = list[position]
//        holder.goodsImage
        holder.goodsNameTextView.text = "名称：" + model.goodsName
        holder.goodsSpecificationsTextView.text = "型号：" + model.goodsSpecifications
        holder.goodsPriceTextView.text = "单价：" + model.goodsPrice
        holder.goodsStockTextView.text = "库存：" + model.goodsStockNum
        holder.updateTimeTextView.text = "更新时间：" + model.goodsUpdateTime
        return itemView
    }

    inner class ViewHolder {
        lateinit var goodsCardView: CardView
        lateinit var goodsImage: ImageView
        lateinit var goodsNameTextView: TextView
        lateinit var goodsSpecificationsTextView: TextView
        lateinit var goodsPriceTextView: TextView
        lateinit var goodsStockTextView: TextView
        lateinit var updateTimeTextView: TextView
    }

    inner class clickListener(val position: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            adapterViewClickListener.adapterViewClick(position, v)
        }
    }

}