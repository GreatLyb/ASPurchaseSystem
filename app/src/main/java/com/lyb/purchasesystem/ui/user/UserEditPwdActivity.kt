package com.lyb.purchasesystem.ui.user

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.toast.ToastUtils
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_edit_pwd.*

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-18 10:32
 */
@Route(path = "/app/UserEditPwdActivity")
class UserEditPwdActivity : View.OnClickListener, BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "修改登录密码"
        val view = View.inflate(pageContext, R.layout.activity_edit_pwd, null)
        containerView().addView(view)
        tv_set_login_pwd_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                tv_set_login_pwd_submit.id -> submintEdit()
            }
        }
    }

    private fun submintEdit() {
        val oldPwd = et_set_login_pwd_old.text.toString()
        val newPwd = et_set_login_pwd_new.text.toString()
        val againPwd = et_set_login_pwd_new_again.text.toString()
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.show("请输入原密码")
            return
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.show("请输入新密码")
            return
        }
        if (TextUtils.isEmpty(againPwd)) {
            ToastUtils.show("请再次输入新密码")
            return
        }
        if (!newPwd.equals(againPwd)) {
            ToastUtils.show("新密码两次密码不一致")
            return
        }
        //网络请求修改密码

    }
}