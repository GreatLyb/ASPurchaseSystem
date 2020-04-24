package com.lyb.purchasesystem.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lyb.purchasesystem.R;
import com.lysoft.baseproject.activity.BaseUIActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public abstract class BaseMainActivity extends BaseUIActivity {

    //底部布局
    private LinearLayout mBottomLayout;
    private RadioGroup mItemGroup;

    /**
     * 当前选中的是那个Fragment
     */
    private int mItemPosi = -1;
    //表示的是当前的Fragment
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValues();
        initListeners();
    }

    public void initValues() {
        View view = View.inflate(this, R.layout.include_base_main_bottom, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        containerView().addView(view);
        mBottomLayout = findViewById(R.id.hh_rl_base_main);
        mItemGroup = findViewById(R.id.hh_rg_base_main);
        viewPager = findViewById(R.id.view_pager);
        topViewManager().topView().setVisibility(View.GONE);
        addItem(getDrawableIDs(), getItemNames());
        mItemGroup.setBackgroundDrawable(getMainBottomBackgroundDrawable());
    }


    /**
     * 把Item添加到Group中
     *
     * @param item
     */
    private void addItemToGroup(View item) {
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        params.weight = 1;
        mItemGroup.addView(item, params);
    }

    /**
     * 添加显示的Item
     *
     * @param drawableIDs 显示的图片
     * @param itemNames   显示的标题
     */
    protected void addItem(int[] drawableIDs, String[] itemNames) {
        if (drawableIDs == null || itemNames == null || drawableIDs.length != itemNames.length) {
            throw new RuntimeException("please check getDrawableIDs() and getItemNames() method");
        }
        for (int i = 0; i < itemNames.length; i++) {
            RadioButton item = getItemStyle();
            item.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            item.setGravity(Gravity.CENTER);
            //设置的ID必须是一个正整数
            item.setId(i);
            item.setText(itemNames[i]);
            item.setCompoundDrawablesWithIntrinsicBounds(0, drawableIDs[i], 0, 0);
            addItemToGroup(item);
        }

    }


    public void initListeners() {
        viewPager.setOffscreenPageLimit(4);


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return getItemNames().length;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return getFragment(position);
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mItemGroup.check(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mItemGroup.setOnCheckedChangeListener((group, checkedId) -> {
            viewPager.setCurrentItem(checkedId);
        });
        mItemPosi = 0;
        checkItem(mItemPosi);
    }


    /**
     * 选中某个Item
     *
     * @param posi 位置
     */
    protected void checkItem(int posi) {
        mItemPosi = posi;
        RadioButton childAt = (RadioButton) mItemGroup.getChildAt(mItemPosi);
        childAt.setChecked(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("item_posi", mItemPosi);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mItemPosi = savedInstanceState.getInt("item_posi", 0);
        checkItem(mItemPosi);
    }

    /**
     * 获取显示的图片的ID
     *
     * @return
     */
    protected abstract int[] getDrawableIDs();

    /**
     * 返回Item显示的文本信息
     *
     * @return
     */
    protected abstract String[] getItemNames();

    /**
     * 返回Item显示的样式的模板，如果重写了该方法，添加子Item的时候，创建的Item的Item会使用该方法返回的样式
     *
     * @return
     */
    protected abstract RadioButton getItemStyle();

    /**
     * 获取需要显示的Fragment
     *
     * @param position 当前的位置,从0开始
     */
    protected abstract Fragment getFragment(int position);

    /**
     * 设置底部的背景
     *
     * @return
     */
    protected abstract Drawable getMainBottomBackgroundDrawable();

}
