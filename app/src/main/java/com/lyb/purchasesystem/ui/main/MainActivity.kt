package com.lyb.purchasesystem.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.toast.ToastUtils
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.fragment.CommunityFragment
import com.lyb.purchasesystem.fragment.MailListFragment
import com.lyb.purchasesystem.fragment.MainFragment
import com.lyb.purchasesystem.fragment.UserCenterFragment
import com.lyb.purchasesystem.ui.BaseMainActivity
import com.lysoft.baseproject.utils.DensityUtils

@Route(path = "/app/MainActivity")
class MainActivity : BaseMainActivity() {
    var exitTime: Long = 0
    override fun getDrawableIDs(): IntArray {
        return intArrayOf(R.drawable.main_selector_rb_bottom_2, R.drawable.main_selector_rb_bottom_3, R.drawable.main_selector_rb_bottom_4, R.drawable.main_selector_rb_bottom_5)
    }

    //双击退出应用
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            dialog()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun dialog() {
        val nowTime = System.currentTimeMillis()
        if (nowTime - exitTime > 3000) {
            ToastUtils.show("再按一次退出应用")
            exitTime = nowTime
        } else {
            finish()
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

    override fun getItemNames(): Array<String> {
        return resources.getStringArray(R.array.main_bottom_array)
    }

    override fun getItemStyle(): RadioButton {
        val radioButton = RadioButton(this)
        val padding = DensityUtils.dip2px(this, 5f)
        radioButton.setPadding(0, padding, 0, padding)
        radioButton.setTextColor(ContextCompat.getColorStateList(pageContext, R.color.selector_color_main_bottom_text))
        radioButton.textSize = 12f
        radioButton.setBackgroundColor(Color.WHITE)
        radioButton.buttonDrawable = ColorDrawable(Color.TRANSPARENT)
        return radioButton
    }

    override fun getFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MainFragment()
            1 -> fragment = CommunityFragment()
            2 -> fragment = MailListFragment()
            3 -> fragment = UserCenterFragment(this)
            else -> {

            }
        }
        return fragment!!
    }

    override fun getMainBottomBackgroundDrawable(): Drawable {
        return resources.getDrawable(R.drawable.main_bottom_bg)
    }
}