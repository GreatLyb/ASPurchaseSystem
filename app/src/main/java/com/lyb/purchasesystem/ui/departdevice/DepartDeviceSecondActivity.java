package com.lyb.purchasesystem.ui.departdevice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.adapter.departdevice.DepartDeviceListAdapter;
import com.lyb.purchasesystem.bean.departdevice.DepartDeviceBean;
import com.lyb.purchasesystem.consta.Constants;
import com.lysoft.baseproject.activity.BaseUIListActivity;
import com.lysoft.baseproject.imp.BaseCallBack;
import com.lysoft.baseproject.imp.LoadStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ASPurchaseSystem
 * 类描述：部门设备第二级
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-25 16:12
 */
public class DepartDeviceSecondActivity extends BaseUIListActivity<DepartDeviceBean> {
    DepartDeviceBean model;
    @BindView(R.id.et_supply_search_content)
    EditText etSupplySearchContent;
    @BindView(R.id.tv_supply_search_sure)
    TextView tvSupplySearchSure;
    @BindView(R.id.ll_supply_top_search)
    LinearLayout llSupplyTopSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        model = (DepartDeviceBean) getIntent().getSerializableExtra("model");
        super.onCreate(savedInstanceState);
        Log.i("Lyb", "departDeviceBean==" + model.getDepartDeviceBeans().size());
        View topView = View.inflate(this, R.layout.item_depart_home_top, null);
        contentView().addView(topView, 1);
        topViewManager().titleTextView().setText(model.getDeviceName());
        loadViewManager().changeLoadState(LoadStatus.LOADING);
        ButterKnife.bind(this, topView);
//        llSupplyTopSearch.setVisibility(View.GONE);

    }

    @Override
    protected void getListData(BaseCallBack callBack) {
//        departDeviceBeans=new ArrayList<>();
        callBack.callBack(model.getDepartDeviceBeans());

    }

    @Override
    protected BaseAdapter instanceAdapter(List list) {
        return new DepartDeviceListAdapter(this, list, false);
    }

    @Override
    protected void itemClickListener(int position) {
        startActivity(new Intent(getPageContext(), DepartDeviceDetailActivity.class));
        //详情
    }

    @Override
    protected int getPageSize() {
        return Constants.PAGE_SIZE;
    }

}
