package com.lyb.purchasesystem.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import cc.shinichi.library.ImagePreview
import com.blankj.utilcode.util.ScreenUtils
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.ImageBean
import com.lysoft.baseproject.adapter.LyBaseAdapter
import com.lysoft.baseproject.glide.GlideImageUtils
import com.lysoft.baseproject.utils.DensityUtils
import com.lysoft.baseproject.view.RoundImageView

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-27 10:51
 */
class DetailImageAdapter(context: Context, list: MutableList<ImageBean>, val ImagePath: MutableList<String>) : LyBaseAdapter<ImageBean>(context, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: Holder
        var itemView = convertView
        if (convertView == null) {
            holder = Holder()
            itemView = View.inflate(context, R.layout.item_img_grid, null)
            holder.imageView = itemView.findViewById(R.id.img_picture)
            itemView.setTag(holder)
        } else {
            holder = itemView?.getTag() as Holder
        }

        val width = (ScreenUtils.getAppScreenWidth() - DensityUtils.dip2px(context, 50f)) / 3
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width)
        holder.imageView.layoutParams = params

        GlideImageUtils.getInstance().loadImage(context, R.drawable.default_img, list[position].ThumImage, holder.imageView)

        holder.imageView.setOnClickListener({ v ->
            ImagePreview.getInstance().setEnableDragClose(true).setEnableUpDragClose(true).setIndex(position).setContext(context).setImageList(ImagePath).start()
        })
        return itemView!!
    }

    private inner class Holder {
        lateinit var imageView: RoundImageView
    }
}