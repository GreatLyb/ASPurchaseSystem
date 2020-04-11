package com.lyb.purchasesystem.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hjq.toast.ToastUtils
import com.kongzue.dialog.util.TextInfo
import com.linchaolong.android.imagepicker.ImagePicker
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUIFragment
import com.lysoft.baseproject.clipview.ClipImageActivity
import kotlinx.android.synthetic.main.frag_user_center.*
import kotlinx.android.synthetic.main.frag_user_center.view.*


/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 14:39
 */
class UserCenterFragment constructor(appCompatActivity: AppCompatActivity) : View.OnClickListener, BaseUIFragment() {
    var activity: AppCompatActivity
    lateinit var imagePicker: ImagePicker

    init {
        activity = appCompatActivity;
    }

    override fun onCreate() {
        topViewManager().backTextView().visibility = View.GONE
        topViewManager().titleTextView().text = "个人中心"
        val view = View.inflate(pageContext, R.layout.frag_user_center, null)
        containerView().addView(view)
        containerView().img_head.setOnClickListener(this)
        containerView().tv_name.setOnClickListener(this)
        var textInfo = TextInfo();
        textInfo.fontColor = R.color.main_color
//        DialogSettings.contentTextInfo=textInfo;

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            img_head.id -> {
                val textInfo = TextInfo()
                val menutextInfo = TextInfo()
                textInfo.fontColor = resources.getColor(R.color.black_dim)
                menutextInfo.fontColor = resources.getColor(R.color.main_color)
                imagePicker = ImagePicker()
                imagePicker.setTitle("设置头像")
                imagePicker.setCropImage(false)
                imagePicker.startChooser(this, object : ImagePicker.Callback() {
                    override fun onPickImage(imageUri: Uri?) {
                        Log.i("Lyb", "onPickImage===")
                        ClipImageActivity.goToClipActivity(getActivity(), imageUri)
//                        Glide.with(pageContext).load(imageUri).into(img_head)

                    }
                })

//                BottomMenu.show(activity, arrayOf("修改头像"), OnMenuItemClickListener { text, index ->
//                }).setCancelButtonTextInfo(textInfo).setMenuTextInfo(menutextInfo)
            }
            tv_name.id -> (ToastUtils.show("名字"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker.onActivityResult(this, requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

        }
    }
}