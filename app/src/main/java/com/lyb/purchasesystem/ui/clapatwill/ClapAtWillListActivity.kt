package com.lyb.purchasesystem.ui.clapatwill

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.ClapAtWillListAdapter
import com.lyb.purchasesystem.bean.ClapAtWillBean
import com.lyb.purchasesystem.bean.ImageBean
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIListActivity
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import com.lysoft.baseproject.imp.LoadStatus

/**
 * ASPurchaseSystem
 * 类描述：随手拍列表
 * 类传参：
 * @Author： create by Lyb on 2020-04-27 10:25
 */
class ClapAtWillListActivity : AdapterViewClickListener, BaseUIListActivity<ClapAtWillBean>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "随手拍"
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.add_white, 0, 0, 0)
        loadViewManager().changeLoadState(LoadStatus.LOADING)
        topViewManager().moreLayout().setOnClickListener { v ->
            startActivity(Intent(pageContext, AddClapAtWillActivity::class.java))
        }
        onPageLoad()
    }

    override fun getListData(callBack: BaseCallBack?) {
        var clapWillList = mutableListOf<ClapAtWillBean>()
        for (a in 1..30) {
            when (a) {
                1 -> {
                    var imageList = mutableListOf<ImageBean>()
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg"))
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg"))
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))

                    clapWillList.add(ClapAtWillBean("外边的地一直没人扫没人管啊？", "，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨...............", "2020-04-02", "张三", imageList, "0", "李四", "2020-08-05"))
                }
                2 -> {
                    var imageList = mutableListOf<ImageBean>()
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))

                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))
                    clapWillList.add(ClapAtWillBean("外边的地一直没人扫没人管啊？", "，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨...............", "2020-04-02", "张三", imageList, "1", "李四", "2020-08-05"))
                }
                3 -> {
                    var imageList = mutableListOf<ImageBean>()
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))
                    clapWillList.add(ClapAtWillBean("外边的地一直没人扫没人管啊？", "，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨...............", "2020-04-02", "张三", imageList, "2", "李四", "2020-08-05"))
                }
                4 -> {
                    var imageList = mutableListOf<ImageBean>()
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))
                    clapWillList.add(ClapAtWillBean("外边的地一直没人扫没人管啊？", "，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨...............", "2020-04-02", "张三", imageList, "3", "李四", "2020-08-05"))
                }
                else -> {
                    var imageList = mutableListOf<ImageBean>()
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg"))
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg"))
                    imageList.add(ImageBean("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg"))

                    clapWillList.add(ClapAtWillBean("外边的地一直没人扫没人管啊？", "，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨，饿哦从卡机搜啊酒叟阿杰OA祭扫就j及哈思安hiahia晒阿萨及哈还hi啊哎as还好是is奥萨...............", "2020-04-02", "张三", imageList, "0", "李四", "2020-08-05"))
                }
            }
        }
        callBack?.callBack(clapWillList)
    }

    override fun instanceAdapter(list: MutableList<ClapAtWillBean>): BaseAdapter {
        return ClapAtWillListAdapter(pageContext, list, this)
    }

    override fun itemClickListener(position: Int) {
    }

    override fun adapterViewClick(position: Int, view: View?) {

    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }
}