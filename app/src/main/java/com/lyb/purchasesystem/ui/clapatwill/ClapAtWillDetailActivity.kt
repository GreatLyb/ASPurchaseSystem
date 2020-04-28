package com.lyb.purchasesystem.ui.clapatwill

import android.os.Bundle
import android.view.View
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.DetailImageAdapter
import com.lyb.purchasesystem.bean.ImageBean
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_clap_at_detail.view.*

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-28 14:07
 */
class ClapAtWillDetailActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "随手拍详情"
        val view = View.inflate(pageContext, R.layout.activity_clap_at_detail, null);
        containerView().addView(view)
        var imageList = mutableListOf<ImageBean>()
        imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg"))
        imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg"))
        imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))
        val pathImageList = mutableListOf<String>()
        for (imageBean in imageList) {
            pathImageList.add(imageList[0].ThumImage)
        }
        containerView().gv_detail_img.adapter = DetailImageAdapter(this, imageList, pathImageList)

    }

}