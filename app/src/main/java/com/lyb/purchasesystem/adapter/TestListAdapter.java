package com.lyb.purchasesystem.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.bean.UserBean;
import com.lysoft.baseproject.adapter.LyBaseAdapter;

import java.util.List;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-11 9:03
 */
public class TestListAdapter extends LyBaseAdapter<UserBean> {
    public TestListAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Houder houder;
        if (convertView == null) {
            houder = new Houder();
            convertView = View.inflate(getContext(), R.layout.item_test_list, null);
            houder.textView = convertView.findViewById(R.id.tv_name);
            convertView.setTag(houder);
        } else {
            houder = (Houder) convertView.getTag();
        }
        houder.textView.setText(getList().get(position).getUserName());
        return convertView;
    }

    private class Houder {
        private TextView textView;
    }
}
