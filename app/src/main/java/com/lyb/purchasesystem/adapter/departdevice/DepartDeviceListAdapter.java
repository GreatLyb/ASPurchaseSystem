package com.lyb.purchasesystem.adapter.departdevice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.bean.departdevice.DepartDeviceBean;
import com.lysoft.baseproject.adapter.LyBaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * LargeScreenShop
 * 类描述：人员信息列表
 * 类传参：
 * Creat by Lyb on 2019/10/10 15:04
 */
public class DepartDeviceListAdapter extends LyBaseAdapter<DepartDeviceBean> {
    //    private AdapterViewClickListener listener;
    private boolean isSearch = false;//
    private boolean isParent = true;

    //    public DepartDeviceListAdapter(Context context, List<DepartDeviceBean> list, AdapterViewClickListener listener,boolean isParent) {
//        super(context, list);
//        this.listener = listener;
//        this.isParent = isParent;
//    }
    public DepartDeviceListAdapter(Context context, List<DepartDeviceBean> list, boolean isParent) {
        super(context, list);
        this.isParent = isParent;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_depart_home, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DepartDeviceBean model = getList().get(position);
        viewHolder.nameTextView.setText(model.getDeviceName());
        viewHolder.brandTextView.setText(model.getDeviceBrand());
        viewHolder.priceTextView.setText("￥" + model.getDevicePrice());
        viewHolder.numTextView.setText(model.getDeviceNum());
//        viewHolder.accTextView.setVisibility(isParent ? View.VISIBLE : View.GONE);
        if (!isParent) {
            viewHolder.accTextView.setText("详情");
            viewHolder.accTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
//            viewHolder.nameTextView.setTextColor(ContextCompat.getColor(getContext(),R.color.gray));
//            viewHolder.brandTextView.setTextColor(ContextCompat.getColor(getContext(),R.color.text_gray));
//            viewHolder.priceTextView.setTextColor(ContextCompat.getColor(getContext(),R.color.text_gray));
//            viewHolder.numTextView.setTextColor(ContextCompat.getColor(getContext(),R.color.text_gray));

//            viewHolder.nameTextView.setTextColor(Color.parseColor("#dfe1e3"));
//            viewHolder.brandTextView.setTextColor(Color.parseColor("#dfe1e3"));
//            viewHolder.priceTextView.setTextColor(Color.parseColor("#dfe1e3"));
//            viewHolder.numTextView.setTextColor(Color.parseColor("#dfe1e3"));

        } else {
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_dd_name)
        TextView nameTextView;
        @BindView(R.id.tv_dd_brand)
        TextView brandTextView;
        @BindView(R.id.tv_dd_price)
        TextView priceTextView;
        @BindView(R.id.tv_dd_num)
        TextView numTextView;
        @BindView(R.id.tv_dd_acc)
        TextView accTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

//    private class AdapterOnClickLisnear implements View.OnClickListener {
//        private int position = 0;
//
//        AdapterOnClickLisnear(int position) {
//            this.position = position;
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (listener != null) {
//                listener.adapterViewClick(position, view);
//            }
//        }
//    }
}
