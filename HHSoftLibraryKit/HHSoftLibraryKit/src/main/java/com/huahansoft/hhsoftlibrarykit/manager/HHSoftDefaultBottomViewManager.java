package com.huahansoft.hhsoftlibrarykit.manager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class HHSoftDefaultBottomViewManager {
    private Context mContext;
    private RelativeLayout mView;
    private RadioGroup mRadioGroup;
    private HHSoftBottomViewInterface mViewInterface;
    public HHSoftDefaultBottomViewManager(Context context,HHSoftBottomViewInterface bottomViewInterface) {
        this.mContext = context;
        this.mViewInterface=bottomViewInterface;
    }

    public View topView() {
        mView = new RelativeLayout(mContext);
        mRadioGroup = new RadioGroup(mContext);
        mRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
        mRadioGroup.setGravity(Gravity.CENTER);
        mView.addView(mRadioGroup,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return mView;
    }

    public void addView() {
        int[] drawableIDs=mViewInterface.getBottomDrawableIDs();
        String[] names=mViewInterface.getBottomNames();
        if (drawableIDs != null && names != null && drawableIDs.length == names.length) {
            for (int i = 0; i < drawableIDs.length; i++) {
                RadioButton radioButton=mViewInterface.getBottomItemView();
                radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
                radioButton.setGravity(Gravity.CENTER);
                // 设置的ID必须是一个正整数
                radioButton.setId(i + 1);
                radioButton.setText(names[i]);
                radioButton.setCompoundDrawablesWithIntrinsicBounds(0, drawableIDs[i], 0, 0);
                radioButton.setBackgroundResource(0);
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.MATCH_PARENT,1.0f);
                mRadioGroup.addView(radioButton,params);
            }
        }
    }
    public interface HHSoftBottomViewInterface{
        int[] getBottomDrawableIDs();
        String[] getBottomNames();
        RadioButton getBottomItemView();
    }
}
