package com.lyb.purchasesystem.ui.main

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.kongzue.dialog.v3.TipDialog
import com.lyb.purchasesystem.R
import com.lysoft.baseproject.activity.BaseUIActivity
import kotlinx.android.synthetic.main.activity_add_comments.view.*

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 14:58
 */

class AddCommentActivity : View.OnClickListener, BaseUIActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "添加建议"
        val view = View.inflate(pageContext, R.layout.activity_add_comments, null)
        containerView().addView(view)
        containerView().tv_user_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val commentName = containerView().et_comment_title.text.toString()
        val commentContent = containerView().et_comment_content.text.toString()
        if (TextUtils.isEmpty(commentName)) {
            TipDialog.show(this, "意见标题不能为空", TipDialog.TYPE.WARNING)
            return
        }
        if (TextUtils.isEmpty(commentContent)) {
            TipDialog.show(this, "意见内容不能为空", TipDialog.TYPE.WARNING)
            return
        }
    }
}