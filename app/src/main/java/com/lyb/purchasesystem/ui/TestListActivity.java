package com.lyb.purchasesystem.ui;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.lyb.purchasesystem.adapter.TestListAdapter;
import com.lyb.purchasesystem.bean.UserBean;
import com.lysoft.baseproject.activity.BaseUIListActivity;
import com.lysoft.baseproject.imp.BaseCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.lyb.purchasesystem.consta.Constants.PAGE_SIZE;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-11 8:35
 */
@Route(path = "/app/TestListActivity")
public class TestListActivity extends BaseUIListActivity<UserBean> {
    private boolean isFirst = true;

    @Override
    protected void getListData(BaseCallBack callBack) {
        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isFirst) {
                    isFirst = false;
                    onFinish();
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                List<UserBean> list = new ArrayList<>();
                Log.i("Lyb", "====" + getPageIndex());
                if (getPageIndex() < 3) {
                    for (int i = 0; i < 20; i++) {
                        UserBean bean = new UserBean();
                        bean.setUsername("第" + getPageIndex() + "页" + "第" + (i + 1) + "条数据");
                        list.add(bean);
                    }
                }
                callBack.callBack(list);
            }
        };
        countDownTimer.start();
    }

    @Override
    protected BaseAdapter instanceAdapter(List list) {
        return new TestListAdapter(getPageContext(), list);
    }

    @Override
    protected void itemClickListener(int position) {
        MessageDialog.show(this, "", "", "", "").setOnOkButtonClickListener(new OnDialogButtonClickListener() {
            @Override
            public boolean onClick(BaseDialog baseDialog, View v) {

                return false;
            }
        }).setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
            @Override
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        });
    }

    @Override
    protected int getPageSize() {
        return PAGE_SIZE;
    }
}
