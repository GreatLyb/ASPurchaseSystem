package com.lyb.purchasesystem.fragment

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ScreenUtils
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.BannerAdapter
import com.lyb.purchasesystem.adapter.MainGrideAdapter
import com.lyb.purchasesystem.adapter.NetViewHolder
import com.lyb.purchasesystem.bean.BannerData
import com.lyb.purchasesystem.bean.MainIconBean
import com.lysoft.baseproject.activity.BaseUIFragment
import com.lysoft.baseproject.utils.DensityUtils
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.frag_main.*
import kotlinx.android.synthetic.main.frag_main.view.*
import kotlinx.android.synthetic.main.item_banner_view.*


/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 14:39
 */
class MainFragment : BaseUIFragment() {
    var bannerView: BannerViewPager<BannerData, NetViewHolder>? = null
    val bannerDatas = mutableListOf<BannerData>()
    override fun onCreate() {
        topViewManager().backTextView().visibility = View.GONE
        topViewManager().titleTextView().text = "首页"
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.scan_icon, 0)
        val view = View.inflate(pageContext, R.layout.frag_main, null)
        bannerView = getViewByID(view, R.id.banner_view)
        containerView().addView(view)
        initListener()
        initBannerView()
        initGrideView()

    }

    private fun initGrideView() {
        val iconList = mutableListOf<MainIconBean>()
        for (i in 1..9) {
            when (i) {
                1 -> iconList.add(MainIconBean("申请采购"))
                2 -> iconList.add(MainIconBean("预约时间"))
                3 -> iconList.add(MainIconBean("添加报告"))
                4 -> iconList.add(MainIconBean("团队管理"))
                5 -> iconList.add(MainIconBean("客户服务"))
                6 -> iconList.add(MainIconBean("库管服务"))
                7 -> iconList.add(MainIconBean("金融课堂"))
                8 -> iconList.add(MainIconBean("互动社区"))
                9 -> iconList.add(MainIconBean("便民生活"))
            }
        }
        containerView().gv_fmp_icon.adapter = MainGrideAdapter(pageContext, iconList)
    }

    private fun initBannerView() {
        bannerDatas.clear()
        var a = 1;
        while (a < 5) {
            when (a) {
                1 -> bannerDatas.add(BannerData("http://attach.bbs.miui.com/forum/201611/17/205602xkjqzg8bjltxlzcx.jpg", "第1张图片"))
                2 -> bannerDatas.add(BannerData("http://attach.bbs.miui.com/forum/201511/25/134309qwnz8s4tnn8yeycf.jpg", "第2张图片"))
                3 -> bannerDatas.add(BannerData("http://attach.bbs.miui.com/forum/201505/18/202426t80hghjqxr0k5gar.jpg", "第3张图片"))
                4 -> bannerDatas.add(BannerData("http://attach.bbs.miui.com/forum/201612/04/104905pc5g5c54hhqcyoh9.jpg", "第4张图片"))
            }
            ++a
        }
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth() * 3 / 5)
        params.setMargins(DensityUtils.dip2px(pageContext, 8f), DensityUtils.dip2px(pageContext, 8f), DensityUtils.dip2px(pageContext, 8f), DensityUtils.dip2px(pageContext, 8f))
        bannerView!!.layoutParams = params
        bannerView!!
                .setAutoPlay(true)
                .setPageStyle(PageStyle.MULTI_PAGE_SCALE)
                .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorSliderGap(resources.getDimensionPixelOffset(R.dimen.dp4x))
                .setIndicatorSliderWidth(resources.getDimensionPixelOffset(R.dimen.dp4x), resources.getDimensionPixelOffset(R.dimen.dp10x))
                .setIndicatorSliderColor(resources.getColor(R.color.white), resources.getColor(R.color.main_color))
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setInterval(3000)
                .setScrollDuration(1000)
                .setRoundCorner(10)
//                    .setPageMargin(20)
//                    .setRevealWidth(50)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setAdapter(BannerAdapter())
                .registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        val bannerData: BannerData = bannerDatas.get(position)
                        tv_describe.setText(bannerData.titleName)
                    }
                })?.create(bannerDatas)
    }

    private fun initListener() {
        topViewManager().moreLayout().setOnClickListener { v: View? -> ARouter.getInstance().build("/qrcodelibrary/ScanCodeActivity").navigation() }
    }

    override fun onPause() {
        super.onPause()
        if (banner_view != null) {
            banner_view.stopLoop()
        }
    }

    override fun onResume() {
        super.onResume()
        if (banner_view != null) banner_view.startLoop()
    }
}