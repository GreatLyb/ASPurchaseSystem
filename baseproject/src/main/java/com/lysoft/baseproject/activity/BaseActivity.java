package com.lysoft.baseproject.activity;

import android.os.Bundle;
import android.view.View;

import com.lysoft.baseproject.dialog.LoadingDialog;

import androidx.annotation.Nullable;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Describe：所有Activity的基类
 */

public abstract class BaseActivity extends LyActivity {
    protected PromptDialog promptDialog;
    protected LoadingDialog loadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                .setMessage("正在加载...")
                .setCancelable(false)
                .setCancelOutside(false);
        loadingDialog = loadBuilder.create();
        View initView = initView();
        promptDialog = new PromptDialog(this);

        setBaseView(initView);
        initValues();
        initListeners();

    }

    public void showWarnMsg(String hintMsg, PromptButton promptButton) {
        if (promptButton == null) {
            promptButton = new PromptButton("确定", new PromptButtonListener() {
                @Override
                public void onClick(PromptButton button) {
                    //点击事件
                    promptDialog.dismiss();
                }
            });
        }
        promptDialog.showWarnAlert(hintMsg, promptButton);
    }

    public void showWarnMsg(String hintMsg) {
        showWarnMsg(hintMsg, null);
    }
}
