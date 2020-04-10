package com.lysoft.baseproject;

import android.app.Application;
import android.content.Context;

import com.lysoft.baseproject.imp.HHSoftApplicationInterface;
import com.lysoft.baseproject.manager.LoadViewManager;


public abstract class HHSoftApplication extends Application {
    private HHSoftApplicationInterface applicationInfo;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppTopViewInfo();
        context = this;
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

    public static Context getCtx() {
        return context;
    }

    public HHSoftApplicationInterface applicationInfo() {
        return applicationInfo;
    }

    protected abstract void initAppTopViewInfo();

    protected abstract LoadViewManager.LoadMode initAppLoadMode();
}
