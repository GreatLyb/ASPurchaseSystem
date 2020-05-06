package com.lyb.purchasesystem.fragment

import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.main.BannerAdapter
import com.lyb.purchasesystem.adapter.main.MainGrideAdapter
import com.lyb.purchasesystem.adapter.main.NetViewHolder
import com.lyb.purchasesystem.bean.main.BannerData
import com.lyb.purchasesystem.bean.main.MainIconBean
import com.lyb.purchasesystem.ui.clapatwill.ClapAtWillListActivity
import com.lyb.purchasesystem.ui.purchase.PurchaseHomeActivity
import com.lyb.purchasesystem.ui.suggestions.SuggestionsBoxActivity
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
        containerView().sw_main.setColorSchemeResources(R.color.main_color)
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
//        containerView().sw_main.setProgressViewOffset(true, 0, 150);

        containerView().sw_main.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener(fun() {
            //
            object : CountDownTimer(1000, 1000) {
                override fun onFinish() {
                    com.hjq.toast.ToastUtils.show("刷新成功")
                    containerView().sw_main.isRefreshing = false

                }

                override fun onTick(millisUntilFinished: Long) {
                }

            }.start()
        }))
        initListener()
        initBannerView()
        initGrideView()

    }

    private fun initGrideView() {
        val iconList = mutableListOf<MainIconBean>()
        for (i in 1..9) {
            when (i) {
                1 -> iconList.add(MainIconBean("采购申请"))
                2 -> iconList.add(MainIconBean("维修记录"))
                3 -> iconList.add(MainIconBean("部门设备"))
                4 -> iconList.add(MainIconBean("教室预约"))
                5 -> iconList.add(MainIconBean("仓库管理"))
                6 -> iconList.add(MainIconBean("随手拍"))
                7 -> iconList.add(MainIconBean("意见箱"))
                8 -> iconList.add(MainIconBean(""))
                9 -> iconList.add(MainIconBean(""))
            }
        }
        containerView().gv_fmp_icon.adapter = MainGrideAdapter(pageContext, iconList)
        containerView().gv_fmp_icon.setOnItemClickListener({ parent, view, position, id ->
            when (position) {
                0 -> startActivity(Intent(pageContext, PurchaseHomeActivity::class.java))
                1 -> ToastUtils.showShort("维修记录")
                2 -> ToastUtils.showShort("部门设备")
                3 -> ToastUtils.showShort("教室预约")
                4 -> ToastUtils.showShort("仓库管理")
                5 -> {
                    startActivity(Intent(pageContext, ClapAtWillListActivity::class.java))
                }
                6 -> {
//                    startActivity(Intent(pageContext, SuggestionsBoxActivity::class.java))
                    startActivity(Intent(pageContext, SuggestionsBoxActivity::class.java))
                }
            }
        })
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
                .setIndicatorSliderGap(DensityUtils.dip2px(pageContext, 4f))
                .setIndicatorSliderWidth(DensityUtils.dip2px(pageContext, 4f), DensityUtils.dip2px(pageContext, 10f))
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