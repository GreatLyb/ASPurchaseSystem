package com.huahansoft.hhsoftlibrarykit.proxy;

import com.huahansoft.hhsoftlibrarykit.manager.HHSoftLoadViewManager;

public interface HHSoftApplicationInterface {
    HHSoftLoadViewManager.LoadMode appLoadMode();
    void appLoadViewInfo();
}
