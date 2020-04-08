package com.lysoft.baseproject.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lysoft.baseproject.R;
import com.lysoft.baseproject.manager.DefaultTopViewManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public abstract class BaseUIActivity extends BaseActivity {
    private LinearLayout contentView;
    private FrameLayout containerView;
    private DefaultTopViewManager topViewManager;
    private View statuBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = new LinearLayout(getPageContext());
        statuBarView = new View(getPageContext());
        statuBarView.setBackgroundColor(ContextCompat.getColor(getPageContext(),R.color.main_color));
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        topViewManager = new DefaultTopViewManager(this);
        contentView.addView(statuBarView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));

        contentView.addView(topViewManager.topView(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        containerView = new FrameLayout(getPageContext());
        contentView.addView(containerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        setContentView(contentView);
    }

    /**
     * 头部管理器
     *
     * @return
     */
    protected DefaultTopViewManager topViewManager() {
        return topViewManager;
    }


    /**
     * 父布局，包含头部
     *
     * @return
     */
    protected LinearLayout contentView() {
        return contentView;
    }

    /**
     * 父布局，包含头部
     *
     * @return
     */
    protected View statuBarView() {
        return statuBarView;
    }

    /**
     * 内容布局，不包含头部
     *
     * @return
     */
    protected FrameLayout containerView() {
        return containerView;
    }
}
