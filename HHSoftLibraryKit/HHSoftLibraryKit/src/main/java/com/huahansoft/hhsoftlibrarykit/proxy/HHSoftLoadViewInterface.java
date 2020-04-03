package com.huahansoft.hhsoftlibrarykit.proxy;

import android.view.View;

public interface HHSoftLoadViewInterface {
    void changeLoadState(HHSoftLoadStatus status);
    void changeLoadStateWithHint(HHSoftLoadStatus status,String hint);
    void setOnClickListener(HHSoftLoadStatus status, View.OnClickListener listener);

}
