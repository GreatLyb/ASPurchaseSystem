package com.huahansoft.hhsoftlibrarykit.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.adapter.HHSoftBaseAdapter;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftDensityUtils;

import java.util.List;
/**
 * @类说明 底部菜单MenuWindow
 * @作者 hhsoft
 * @创建日期 2019/8/29 16:44
 */
public class HHSoftBottomMenuWindow extends PopupWindow {
    private Context context;
    private ListView menuListView;
    private TextView cancelTextView;
    private List<String> menuList;

    public HHSoftBottomMenuWindow(Context context, List<String> menuList,AdapterItemClickListener itemClickListener) {
        super(context);
        this.context = context;
        this.menuList = menuList;
        initPopupWindow(context,itemClickListener);
    }

    private void initPopupWindow(Context context, AdapterItemClickListener itemClickListener) {
        LinearLayout contentView = new LinearLayout(context);
        contentView.setGravity(Gravity.BOTTOM);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setBackgroundColor(ContextCompat.getColor(context, R.color.defaultHalfTransparent));
        int padding10 = HHSoftDensityUtils.dip2px(context, 10);
        contentView.setPadding(padding10, padding10, padding10, padding10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuListView = new ListView(context);
        menuListView.setBackgroundResource(R.drawable.hhsoft_shape_dialog_frame_bg);
        menuListView.setDividerHeight(0);
        menuListView.setFadingEdgeLength(0);
        menuListView.setVerticalFadingEdgeEnabled(false);
        menuListView.setVerticalScrollBarEnabled(false);
        menuListView.setCacheColorHint(Color.TRANSPARENT);
        menuListView.setSelector(R.color.defaultTransparent);
        contentView.addView(menuListView, params);
        cancelTextView = new TextView(context);
        cancelTextView.setTextSize(14);
        cancelTextView.setTextColor(ContextCompat.getColor(context, R.color.defaultBlackText));
        cancelTextView.setText(R.string.huahansoft_cancel);
        cancelTextView.setGravity(Gravity.CENTER);
        cancelTextView.setBackgroundResource(R.drawable.hhsoft_shape_dialog_frame_bg);
        int padding12 = HHSoftDensityUtils.dip2px(context, 12);
        cancelTextView.setPadding(padding12, padding12, padding12, padding12);
        params.setMargins(0, padding10, 0, 0);
        contentView.addView(cancelTextView, params);
        setContentView(contentView);
        cancelTextView.setOnClickListener(view -> HHSoftBottomMenuWindow.this.dismiss());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setAnimationStyle(R.style.HuaHanSoft_Window_Fade_Anim);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.defaultBlackTranslucent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        menuListView.setAdapter(new MenuAdapter(context, this.menuList));
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClickListener(position);
                }
            }
        });
    }

    private class MenuAdapter extends HHSoftBaseAdapter<String> {

        public MenuAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout contentLayout = new LinearLayout(context);
            contentLayout.setOrientation(LinearLayout.VERTICAL);
            TextView contentTextView = new TextView(context);
            contentTextView.setTextSize(14);
            contentTextView.setTextColor(ContextCompat.getColor(context, R.color.defaultBlackText));
            contentTextView.setText(menuList.get(position));
            contentTextView.setGravity(Gravity.CENTER);
            contentTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int padding12 = HHSoftDensityUtils.dip2px(context, 12);
            contentTextView.setPadding(padding12, padding12, padding12, padding12);
            contentLayout.addView(contentTextView);
            if (menuList != null && menuList.size() > 0) {
                if (position != (menuList.size() - 1)) {
                    View lineView = new View(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(padding12, 0, padding12, 0);
                    params.height = HHSoftDensityUtils.dip2px(context, 1);
                    lineView.setLayoutParams(params);
                    lineView.setBackgroundColor(ContextCompat.getColor(context, R.color.defaultBackground));
                    contentLayout.addView(lineView);
                }
            }
            return contentLayout;
        }
    }
    public interface AdapterItemClickListener{
        void onItemClickListener(int position);
    }
}
