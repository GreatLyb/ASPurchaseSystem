package com.lysoft.baseproject.mvp;

import android.os.Bundle;

import com.lysoft.baseproject.activity.BaseUIActivity;

import androidx.annotation.Nullable;


/**
 * Describe：所有需要Mvp开发的Activity的基类
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseUIActivity implements IBaseView {
    protected P presenter;
//    private PromptDialog promptDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        //创建present
        if (presenter != null) {
            presenter.attachView(this);
        }
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }

    }


    //***************************************IBaseView方法实现*************************************
    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }


    //***************************************IBaseView方法实现*************************************

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();


}
