package com.lysoft.baseproject;

import android.app.Application;

import com.lysoft.baseproject.imp.HHSoftApplicationInterface;
import com.lysoft.baseproject.manager.LoadViewManager;


public abstract class HHSoftApplication extends Application {
    private HHSoftApplicationInterface applicationInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppTopViewInfo();
        applicationInfo = new HHSoftApplicationInterface() {
            @Override
            public LoadViewManager.LoadMode appLoadMode() {
                return LoadViewManager.LoadMode.PROGRESS;
            }

            @Override
            public void appLoadViewInfo() {

            }
        };
    }

    public HHSoftApplicationInterface applicationInfo() {
        return applicationInfo;
    }

    protected abstract void initAppTopViewInfo();

    protected abstract LoadViewManager.LoadMode initAppLoadMode();
}
