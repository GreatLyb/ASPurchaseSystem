package com.huahansoft.hhsoftlibrarykit.manager;

import android.view.View;

import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftLoadStatus;
import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftLoadViewInterface;

public class HHSoftLoadViewManager {
    /**
     * 加载页面模式
     */
    public enum LoadMode {
        DRAWABLE,
        PROGRESS
    }

    private HHSoftLoadViewInterface mAvalibleLoadViewInterface;

    /**
     * 加载布局管理器初始化
     *
     * @param loadMode   加载页面模式
     * @param parentView 必须是帧布局
     */
    public HHSoftLoadViewManager(LoadMode loadMode, View parentView, IPageLoad pageLoad) {
        if (parentView == null) {
            new Throwable("parentView is not null");
        }
        if (LoadMode.DRAWABLE == loadMode) {
            mAvalibleLoadViewInterface = new HHSoftDefaultLoadViewManager(parentView.getContext(), parentView, pageLoad);
        } else {
            mAvalibleLoadViewInterface = new HHSoftProgressLoadViewManager(parentView.getContext(), parentView, pageLoad);
        }
    }

    public void changeLoadState(HHSoftLoadStatus status) {
        mAvalibleLoadViewInterface.changeLoadState(status);
    }

    public void changeLoadStateWithHint(HHSoftLoadStatus status, String hint) {
        mAvalibleLoadViewInterface.changeLoadStateWithHint(status, hint);
    }

    public void setOnClickListener(HHSoftLoadStatus status, View.OnClickListener listener) {
        mAvalibleLoadViewInterface.setOnClickListener(status, listener);
    }

    public interface IPageLoad {
        void onPageLoad();
    }
}
