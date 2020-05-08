package com.lyb.purchasesystem.ui.warehouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.fragment.purchase.PurchaseListFragment
import com.lyb.purchasesystem.fragment.warehouse.WareHouseInfoFragment
import com.lyb.purchasesystem.ui.purchase.PurchaseApplyActivity
import com.lysoft.baseproject.activity.BaseUIActivity
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


/**
 * ASPurchaseSystem
 * 类描述：仓库首页
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class WareHouseHomeActivity : BaseUIActivity() {
    lateinit var mViewPager: ViewPager
    lateinit var wareHouseInfoFragment: WareHouseInfoFragment
    lateinit var purchaseWaitDealFragment: PurchaseListFragment
    lateinit var purchaseHaveDealFragment: PurchaseListFragment
    lateinit var purchaseMineFragment: PurchaseListFragment
    var fragmenList = mutableListOf<Fragment>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "仓库首页"
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.add_white, 0, 0, 0)
        topViewManager().moreLayout().setOnClickListener { v ->
            startActivity(Intent(pageContext, PurchaseApplyActivity::class.java))
        }
        containerView().addView(View.inflate(pageContext, R.layout.activity_suggestion_leader, null))
        wareHouseInfoFragment = WareHouseInfoFragment(this)
        purchaseWaitDealFragment = PurchaseListFragment(2, this)
        purchaseHaveDealFragment = PurchaseListFragment(3, this)
        purchaseMineFragment = PurchaseListFragment(4, this)
        fragmenList.add(wareHouseInfoFragment)
        fragmenList.add(purchaseWaitDealFragment)
        fragmenList.add(purchaseHaveDealFragment)
        fragmenList.add(purchaseMineFragment)
        initTopIndicator()
    }

    private fun initTopIndicator() {
        var mTitleDataList = mutableListOf<String>()
        mTitleDataList.add("仓库信息")
        mTitleDataList.add("进库列表")
        mTitleDataList.add("出库列表")
        mTitleDataList.add("仓库盘点表")
        val magicIndicator = findViewById(R.id.magic_indicator) as MagicIndicator
        mViewPager = findViewById(R.id.view_pager)

        mViewPager.adapter = (object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return mTitleDataList.size
            }

            override fun getItem(position: Int): Fragment {
                return fragmenList[position]
            }
        })
        val commonNavigator = CommonNavigator(this)

        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mTitleDataList.size
            }

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView? {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = ContextCompat.getColor(pageContext, R.color.text_gray)
                colorTransitionPagerTitleView.selectedColor = ContextCompat.getColor(pageContext, R.color.main_color)
                colorTransitionPagerTitleView.text = mTitleDataList.get(index)
                colorTransitionPagerTitleView.textSize = 16f
                colorTransitionPagerTitleView.right = 80
                colorTransitionPagerTitleView.setOnClickListener { mViewPager.setCurrentItem(index) }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ContextCompat.getColor(pageContext, R.color.main_color))
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }

}