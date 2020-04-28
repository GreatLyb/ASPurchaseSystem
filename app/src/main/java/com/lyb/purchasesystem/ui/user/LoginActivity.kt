package com.lyb.purchasesystem.ui.user

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.toast.ToastUtils
import com.kongzue.dialog.v3.TipDialog
import com.kongzue.dialog.v3.WaitDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.UserBean
import com.lyb.purchasesystem.consta.Api
import com.lyb.purchasesystem.consta.ParamsMapUtils
import com.lyb.purchasesystem.utils.RequestBodyUtils
import com.lyb.purchasesystem.utils.UserInfoUtils
import com.lysoft.baseproject.dialog.LoadingDialog
import com.lysoft.baseproject.imp.SingleClick
import com.lysoft.baseproject.net.callback.JsonCallBack
import com.lysoft.baseproject.utils.StatusBarUtil
import com.lzy.okgo.OkGo

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-07 14:19
 */
@Route(path = "/app/LoginActivity")
class LoginActivity : AppCompatActivity(), TextView.OnEditorActionListener {
    @JvmField
    @BindView(R.id.et_user_login_phone)
    var etUserLoginPhone: EditText? = null

    @JvmField
    @BindView(R.id.et_user_login_pwd)
    var etUserLoginPwd: EditText? = null
    var loadingDialog: LoadingDialog? = null
    private val hasAddView = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarState()
        val view = View.inflate(this, R.layout.activity_login, null)
        ButterKnife.bind(this, view)
        setContentView(view)
        loadingDialog = LoadingDialog.getInstance(this)
        etUserLoginPhone!!.setOnEditorActionListener(this)
        etUserLoginPwd!!.setOnEditorActionListener(this)
    }

    private fun setStatusBarState() {
        //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white))
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }
    }

    @SingleClick
    @OnClick(R.id.tv_user_login_sure)
    fun onViewClicked() {
        //登录
        Login()
    }

    private fun Login() {
        val account = etUserLoginPhone!!.text.toString()
        val pwd = etUserLoginPwd!!.text.toString()
        if (TextUtils.isEmpty(account)) {
            ToastUtils.show("请输入账号")
            //            Notification.show(this, "提示", "请输入账号").setDurationTime(Notification.DURATION_TIME.SHORT);
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("请输入密码")
            return
        }
        WaitDialog.show(this, "正在登录...")
        val param = ParamsMapUtils.getLoginParams(account, pwd)
        val jsonCallBack: JsonCallBack<UserBean?> = object : JsonCallBack<UserBean?>() {


            override fun onFailure(tag: Any?, e: Exception?) {
                TipDialog.show(this@LoginActivity, e?.message, TipDialog.TYPE.ERROR)
            }

            override fun onSuccess(code: Int, msg: String?, response: UserBean?) {
                if (code == 200) {
                    //登录成功
                    getUserInfo(response!!.token)
                } else {
                    //登录失败
                    TipDialog.show(this@LoginActivity, msg, TipDialog.TYPE.ERROR)
                }

            }
        }
        OkGo.post<UserBean>(Api.LOGIN).tag(this).upRequestBody(RequestBodyUtils.getRequestBody(param)).execute(jsonCallBack)
    }

    private fun getUserInfo(token: String) {
        val jsonCallBack: JsonCallBack<UserBean?> = object : JsonCallBack<UserBean?>() {
            override fun onSuccess(code: Int, msg: String, response: UserBean?) {
                if (code == 200) {
                    //登录成功
                    response!!.token = token
                    UserInfoUtils.saveUserInfo(baseContext, response)
                    TipDialog.show(this@LoginActivity, "登录成功", TipDialog.TYPE.SUCCESS)
                    ARouter.getInstance().build("/app/MainActivity").navigation()
                    finish()
                } else {
                    //登录失败
                    TipDialog.show(this@LoginActivity, msg, TipDialog.TYPE.ERROR)
                }
            }

            override fun onFailure(tag: Any, e: Exception) {
                TipDialog.show(this@LoginActivity, e.message, TipDialog.TYPE.ERROR)
            }
        }
        OkGo.get<UserBean>(Api.INFO + "/" + token).tag(this).execute(jsonCallBack)
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        //当actionId == XX_SEND 或者 XX_DONE时都触发
        //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
        //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
        if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.action) {
            //处理事件
            Login()
        }
        return false
    }
}