package com.lysoft.baseproject.imp;


import com.lysoft.baseproject.manager.LoadViewManager;

public interface HHSoftApplicationInterface {
    LoadViewManager.LoadMode appLoadMode();
    void appLoadViewInfo();
}
