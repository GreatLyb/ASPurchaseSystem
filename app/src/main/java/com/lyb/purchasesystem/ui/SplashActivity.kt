package com.lyb.purchasesystem.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.consta.PermissionsConstant
import com.lyb.purchasesystem.ui.main.MainActivity
import com.lyb.purchasesystem.utils.UserInfoUtils
import com.lysoft.baseproject.activity.BaseUIActivity
import com.lysoft.baseproject.utils.StatusBarUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * ASPurchaseSystem
 * 类描述：启动页
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-03 14:39
 */
@Route(path = "/app/SplashActivity")
class SplashActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View.inflate(pageContext, R.layout.activity_splash, null)
        topViewManager().topView().visibility = View.GONE
        containerView().addView(view)
        if (!checkPermission(PermissionsConstant.PERMISSIONS_CAMERA_AND_STORAGE)) {
            requestPermission("请授予APP读写和相机权限", PermissionsConstant.PERMISSIONS_CAMERA_AND_STORAGE)
        } else {
            splashEnd()
        }
        setStatusBarState()
    }

    fun splashEnd() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Long ->
                    if (UserInfoUtils.isLogin(pageContext)) {
                        startActivity(Intent(pageContext, MainActivity::class.java))
                    } else {
                        startActivity(Intent(pageContext, MainActivity::class.java))
//                        startActivity(Intent(pageContext, LoginActivity::class.java))
                    }
                    finish()
                })

//        object : CountDownTimer(1000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {}
//            override fun onFinish() {
//                //                ARouter.getInstance().build("/app/WeChatActivity").navigation();
////                                ARouter.getInstance().build("/app/TestListActivity").navigation();
////                ARouter.getInstance().build("/app/LoginActivity").navigation();
//                //                startActivity(new Intent(getPageContext(), ScanCodeActivity.class));
//                finish()
//            }
//        }.start()
    }

    fun setStatusBarState() {
        //这里注意下 因为在评论区发现有网友调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
        //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(pageContext, R.color.white))

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }
    }

    override fun permissionsGranted() {
        super.permissionsGranted()
        //权限成功
        splashEnd()
    }


    override fun permissionsDenied(perms: List<String>) {
        super.permissionsDenied(perms)
        //权限失败
    }
}