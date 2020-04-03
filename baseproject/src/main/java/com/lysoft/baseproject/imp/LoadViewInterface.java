package com.lysoft.baseproject.imp;

import android.view.View;

public interface LoadViewInterface {
    void changeLoadState(LoadStatus status);
    void changeLoadStateWithHint(LoadStatus status, String hint);
    void setOnClickListener(LoadStatus status, View.OnClickListener listener);

}
