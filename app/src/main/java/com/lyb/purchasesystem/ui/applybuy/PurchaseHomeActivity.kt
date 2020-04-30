package com.lyb.purchasesystem.ui.applybuy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.fragment.suggestion.SuggestionsAllFragment
import com.lyb.purchasesystem.fragment.suggestion.SuggestionsMineFragment
import com.lyb.purchasesystem.ui.main.AddCommentActivity
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
 * 类描述：采购申请首页
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class PurchaseHomeActivity : BaseUIActivity() {
    lateinit var mViewPager: ViewPager
    lateinit var suggestionsAllFragment: SuggestionsAllFragment
    lateinit var suggestionsMineFragment: SuggestionsMineFragment
    var fragmenList = mutableListOf<Fragment>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "采购单"
        topViewManager().moreTextView().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.add_white, 0, 0, 0)
        topViewManager().moreLayout().setOnClickListener { v ->
            startActivity(Intent(pageContext, AddCommentActivity::class.java))
        }
        containerView().addView(View.inflate(pageContext, R.layout.activity_suggestion_leader, null))
        suggestionsAllFragment = SuggestionsAllFragment(this)
        suggestionsMineFragment = SuggestionsMineFragment(this)
        fragmenList.add(suggestionsAllFragment)
        fragmenList.add(suggestionsMineFragment)
        fragmenList.add(SuggestionsMineFragment(this))
        fragmenList.add(SuggestionsMineFragment(this))
        initTopIndicator()
    }

    private fun initTopIndicator() {
        var mTitleDataList = mutableListOf<String>()
        mTitleDataList.add("全部")
        mTitleDataList.add("待处理")
        mTitleDataList.add("已处理")
        mTitleDataList.add("我的")
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