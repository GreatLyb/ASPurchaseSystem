package com.lysoft.baseproject.manager;

import android.view.View;

import com.lysoft.baseproject.imp.LoadStatus;
import com.lysoft.baseproject.imp.LoadViewInterface;


public class LoadViewManager {
    /**
     * 加载页面模式
     */
    public enum LoadMode {
        DRAWABLE,
        PROGRESS
    }

    private LoadViewInterface mAvalibleLoadViewInterface;

    /**
     * 加载布局管理器初始化
     *
     * @param loadMode   加载页面模式
     * @param parentView 必须是帧布局
     */
    public LoadViewManager(LoadMode loadMode, View parentView, IPageLoad pageLoad) {
        if (parentView == null) {
            new Throwable("parentView is not null");
        }
        if (LoadMode.DRAWABLE == loadMode) {
            mAvalibleLoadViewInterface = new DefaultLoadViewManager(parentView.getContext(), parentView, pageLoad);
        } else {
            mAvalibleLoadViewInterface = new HHSoftProgressLoadViewManager(parentView.getContext(), parentView, pageLoad);
        }
    }

    public void changeLoadState(LoadStatus status) {
        mAvalibleLoadViewInterface.changeLoadState(status);
    }

    public void changeLoadStateWithHint(LoadStatus status, String hint) {
        mAvalibleLoadViewInterface.changeLoadStateWithHint(status, hint);
    }

    public void setOnClickListener(LoadStatus status, View.OnClickListener listener) {
        mAvalibleLoadViewInterface.setOnClickListener(status, listener);
    }

    public interface IPageLoad {
        void onPageLoad();
    }
}
