package com.lyb.purchasesystem.adapter.warehouse;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.bean.warehouse.FirstLevelBean;
import com.lyb.purchasesystem.view.CustomGridView;
import com.lysoft.baseproject.imp.AdapterViewClickListener;

import java.util.List;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-21 18:35
 */
public class WareHouseMenuAdapter extends BaseExpandableListAdapter {
    int[] group_state_array = new int[]{R.drawable.group_up, R.drawable.group_down};
    private Context context;
    private List<FirstLevelBean> firstLevelBeans;
    private AdapterViewClickListener adapterViewClickListener;

    public WareHouseMenuAdapter(Context context, List<FirstLevelBean> firstLevelBeans, AdapterViewClickListener adapterViewClickListener) {
        this.context = context;
        this.firstLevelBeans = firstLevelBeans;
        this.adapterViewClickListener = adapterViewClickListener;
    }

    /**
     * 获取分组的个数
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return firstLevelBeans.size();
    }

    /**
     * 获取指定分组中的子选项的个数
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * 获取指定的分组数据
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return firstLevelBeans.get(groupPosition);
    }

    /**
     * 获取指定分组中的指定子选项数据
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return firstLevelBeans.get(groupPosition).getSecondLevelMenus().get(childPosition);
    }

    /**
     * 获取指定分组的ID, 这个ID必须是唯一的
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取子选项的ID, 这个ID必须是唯一的
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // 为视图对象指定布局
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.partent_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = convertView.findViewById(R.id.label_group_normal);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvTitle.setText(firstLevelBeans.get(groupPosition).getFirstName());
        if (firstLevelBeans.get(groupPosition).isSelect()) {
            groupViewHolder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.main_color));
        } else {
            groupViewHolder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.text_black));
        }
        if (!isExpanded) {
            groupViewHolder.tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, group_state_array[1], 0);
        } else {
            groupViewHolder.tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, group_state_array[0], 0);
        } // 返回一个布局对象

        return convertView;
    }

    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.gridView = (CustomGridView) convertView.findViewById(R.id.gv_second);
            convertView.setTag(childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        SecondGrideAdapter secondGrideAdapter = new SecondGrideAdapter(context, firstLevelBeans.get(groupPosition).getSecondLevelMenus());
        childViewHolder.gridView.setAdapter(secondGrideAdapter);
        childViewHolder.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        childViewHolder.gridView.setOnItemClickListener((parent1, view, position, id) -> {
            adapterViewClickListener.adapterViewClick(groupPosition, position, parent);
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
    }

    static class ChildViewHolder {
        CustomGridView gridView;

    }

}
