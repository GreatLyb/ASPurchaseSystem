package com.lyb.purchasesystem.ui.departdevice;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.consta.Constants;
import com.lysoft.baseproject.activity.BaseUIListActivity;
import com.lysoft.baseproject.imp.BaseCallBack;
import com.lysoft.baseproject.imp.LoadStatus;

import java.util.List;

/**
 * ASPurchaseSystem
 * 类描述：部门设备首页
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-25 16:12
 */
public class DepartDeviceHomeActivity extends BaseUIListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View topView = View.inflate(this, R.layout.item_depart_home_top, null);
        containerView().addView(topView, 1);
        topViewManager().titleTextView().setText("部门设备");
        loadViewManager().changeLoadState(LoadStatus.SUCCESS);
    }

    @Override
    protected void getListData(BaseCallBack callBack) {

    }

    @Override
    protected BaseAdapter instanceAdapter(List list) {
        return null;
    }

    @Override
    protected void itemClickListener(int position) {

    }

    @Override
    protected int getPageSize() {
        return Constants.PAGE_SIZE;
    }

}
