package com.lyb.purchasesystem.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.widget.RadioButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.toast.ToastUtils;
import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.fragment.MainFragment;
import com.lyb.purchasesystem.fragment.MsgFragment;
import com.lyb.purchasesystem.fragment.PhoneNumListFragment;
import com.lyb.purchasesystem.fragment.UserCenterFragment;
import com.lysoft.baseproject.utils.DensityUtils;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

@Route(path = "/app/MainActivity")
public class MainActivity extends BaseMainActivity {

    private long exitTime;


    @Override
    protected int[] getDrawableIDs() {
        return new int[]{R.drawable.main_selector_rb_bottom_2, R.drawable.main_selector_rb_bottom_3, R.drawable.main_selector_rb_bottom_4, R.drawable.main_selector_rb_bottom_5};

    }

    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void dialog() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - exitTime > 3000) {
            ToastUtils.show("再按一次退出应用");
            exitTime = nowTime;
        } else {
            finish();
        }
        //ImagePicker imagePicker=new ImagePicker();
        //
        //        imagePicker.startCamera(this, new ImagePicker.Callback() {
        //            @Override
        //            public void onPickImage(Uri imageUri) {
        //
        //            }
        //        });

    }

    @Override
    protected String[] getItemNames() {
        return getResources().getStringArray(R.array.main_bottom_array);
    }

    @Override
    protected RadioButton getItemStyle() {
        RadioButton radioButton = new RadioButton(this);
        int padding = DensityUtils.dip2px(this, 5);
        radioButton.setPadding(0, padding, 0, padding);
        radioButton.setTextColor(ContextCompat.getColorStateList(getPageContext(), R.color.selector_color_main_bottom_text));
        radioButton.setTextSize(12);
        radioButton.setBackgroundColor(Color.WHITE);
        radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        return radioButton;
    }

    @Override
    protected Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new MsgFragment();
                break;
            case 2:
                fragment = new PhoneNumListFragment();
                break;
            case 3:
                fragment = new UserCenterFragment(this);
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    protected Drawable getMainBottomBackgroundDrawable() {
        return getResources().getDrawable(R.drawable.main_bottom_bg);
    }

}
