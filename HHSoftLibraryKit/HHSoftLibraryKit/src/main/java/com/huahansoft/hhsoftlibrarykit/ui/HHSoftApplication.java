package com.huahansoft.hhsoftlibrarykit.ui;

import android.app.Application;

import com.huahansoft.hhsoftlibrarykit.manager.HHSoftLoadViewManager;
import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftApplicationInterface;

public abstract class HHSoftApplication extends Application {
    private HHSoftApplicationInterface applicationInfo;
    @Override
    public void onCreate() {
        super.onCreate();
        initAppTopViewInfo();
        applicationInfo=new HHSoftApplicationInterface() {
            @Override
            public HHSoftLoadViewManager.LoadMode appLoadMode() {
                return initAppLoadMode();
            }

            @Override
            public void appLoadViewInfo() {

            }
        };
    }
    public HHSoftApplicationInterface applicationInfo(){
        return applicationInfo;
    }
    protected abstract void initAppTopViewInfo();
    protected abstract HHSoftLoadViewManager.LoadMode initAppLoadMode();
}
