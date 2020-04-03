package com.huahansoft.hhsoftlibrarykit.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.adapter.HHSoftBaseAdapter;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftShareInfo;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftShareItemInfo;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdConstants;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdTools;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftDensityUtils;

import java.util.List;

/**
 * @类说明 底部菜单MenuWindow
 * @作者 hhsoft
 * @创建日期 2019/8/29 16:44
 */
public class HHSoftShareWindow extends PopupWindow {
    private GridView shareMenuGridView;
    private TextView cancelTextView;
    private List<HHSoftShareItemInfo> shareItemInfos;

    public HHSoftShareWindow(Context context, List<HHSoftShareItemInfo> shareItemInfos) {
        super(context);
        this.shareItemInfos = shareItemInfos;
        initPopupWindow(context, shareItemInfos);
    }

    private void initPopupWindow(Context context, List<HHSoftShareItemInfo> shareItemInfos) {
        LinearLayout contentView = new LinearLayout(context);
        contentView.setGravity(Gravity.BOTTOM);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setBackgroundColor(ContextCompat.getColor(context, R.color.defaultHalfTransparent));
        int padding10 = HHSoftDensityUtils.dip2px(context, 10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        shareMenuGridView = new GridView(context);
        shareMenuGridView.setBackgroundResource(R.drawable.hhsoft_shape_share_frame_bg);
        shareMenuGridView.setNumColumns(4);
        shareMenuGridView.setHorizontalSpacing(padding10);
        shareMenuGridView.setVerticalSpacing(padding10 * 2);
        shareMenuGridView.setFadingEdgeLength(0);
        shareMenuGridView.setVerticalFadingEdgeEnabled(false);
        shareMenuGridView.setVerticalScrollBarEnabled(false);
        shareMenuGridView.setCacheColorHint(Color.TRANSPARENT);
        shareMenuGridView.setSelector(R.color.defaultTransparent
        );
        shareMenuGridView.setPadding(0, padding10 * 4, 0, padding10 * 2);
        contentView.addView(shareMenuGridView, params);
        cancelTextView = new TextView(context);
        cancelTextView.setTextSize(16);
        cancelTextView.setTextColor(ContextCompat.getColor(context, R.color.defaultBlackLight));
        cancelTextView.setText(R.string.huahansoft_cancel);
        cancelTextView.setGravity(Gravity.CENTER);
        cancelTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.defaultWhite));
        int padding12 = HHSoftDensityUtils.dip2px(context, 12);
        cancelTextView.setPadding(padding12, padding12, padding12, padding12);
        contentView.addView(cancelTextView, params);
        setContentView(contentView);
        cancelTextView.setOnClickListener(view -> HHSoftShareWindow.this.dismiss());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setAnimationStyle(R.style.HuaHanSoft_Window_Fade_Anim);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.defaultBlackTranslucent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        shareMenuGridView.setAdapter(new MenuAdapter(context, shareItemInfos));
    }

    public void share(HHSoftShareInfo shareInfo) {
        shareMenuGridView.setOnItemClickListener(((parent, view, position, id) -> {
            HHSoftThirdTools.getInstance().thirdShare(shareItemInfos.get(position).getPlatformID(), shareInfo);
        }));

    }

    public void shareWithListener(AdapterItemClickListener itemClickListener) {
        shareMenuGridView.setOnItemClickListener(((parent, view, position, id) -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClickListener(shareItemInfos.get(position).getPlatformID());
            }
        }));
    }

    private class MenuAdapter extends HHSoftBaseAdapter<HHSoftShareItemInfo> {

        public MenuAdapter(Context context, List<HHSoftShareItemInfo> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HHSoftShareItemInfo shareItemInfo = getList().get(position);
            LinearLayout contentLayout = new LinearLayout(getContext());
            contentLayout.setOrientation(LinearLayout.VERTICAL);
            contentLayout.setGravity(Gravity.CENTER);
            ImageView iconImageView = new ImageView(getContext());
            int iconSize = HHSoftDensityUtils.dip2px(getContext(), 50);
            iconImageView.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize));
            iconImageView.setImageResource(shareItemInfo.getDrawableResID());
            iconImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            contentLayout.addView(iconImageView);
            TextView contentTextView = new TextView(getContext());
            contentTextView.setTextSize(12);
            contentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultBlackLight));
            contentTextView.setText(getContext().getString(shareItemInfo.getNameResID()));
            contentTextView.setGravity(Gravity.CENTER);
            int padding10 = HHSoftDensityUtils.dip2px(getContext(), 10);
            contentTextView.setPadding(padding10, 0, padding10, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, padding10, 0, 0);
            contentTextView.setLayoutParams(params);
            contentLayout.addView(contentTextView);
            return contentLayout;
        }
    }

    public interface AdapterItemClickListener {
        void onItemClickListener(int platformID);
    }
}
