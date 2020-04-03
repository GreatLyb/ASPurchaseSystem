package com.lysoft.baseproject.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lysoft.baseproject.HHSoftApplication;
import com.lysoft.baseproject.imp.HHSoftApplicationInterface;
import com.lysoft.baseproject.manager.DefaultTopViewManager;
import com.lysoft.baseproject.manager.LoadViewManager;

import androidx.annotation.Nullable;

public abstract class BaseUILoadActivity extends BaseActivity {
    private LinearLayout contentView;
    private FrameLayout containerView;
    private DefaultTopViewManager topViewManager;
    private LoadViewManager loadViewManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = new LinearLayout(getPageContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        topViewManager = new DefaultTopViewManager(this);
        contentView.addView(topViewManager.topView(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        containerView = new FrameLayout(getPageContext());
        contentView.addView(containerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        setContentView(contentView);
        loadViewManager = new LoadViewManager(initLoadMode() == null ? LoadViewManager.LoadMode.PROGRESS : initLoadMode(), containerView, () -> {
            onPageLoad();
        });
    }

    /**
     * 初始化加载模式
     *
     * @return
     */
    protected LoadViewManager.LoadMode initLoadMode() {
        if (getApplication() instanceof HHSoftApplication) {
            HHSoftApplication application = (HHSoftApplication) getApplication();
            HHSoftApplicationInterface applicationInfo = application.applicationInfo();
            return applicationInfo.appLoadMode() == null ? LoadViewManager.LoadMode.PROGRESS : applicationInfo.appLoadMode();
        }
        return LoadViewManager.LoadMode.PROGRESS;
    }

    /**
     * 页面加载方法
     */
    protected abstract void onPageLoad();

    /**
     * 头部管理器
     *
     * @return
     */
    protected DefaultTopViewManager topViewManager() {
        return topViewManager;
    }

    /**
     * 加载管理器
     *
     * @return
     */
    protected LoadViewManager loadViewManager() {
        return loadViewManager;
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
     * 内容布局，不包含头部
     *
     * @return
     */
    protected FrameLayout containerView() {
        return containerView;
    }
}
