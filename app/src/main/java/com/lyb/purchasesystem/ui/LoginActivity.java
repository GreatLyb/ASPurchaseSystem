package com.lyb.purchasesystem.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.toast.ToastUtils;
import com.lyb.purchasesystem.R;
import com.lysoft.baseproject.activity.BaseUIActivity;
import com.lysoft.baseproject.imp.SingleClick;
import com.lysoft.baseproject.utils.StatusBarUtil;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-07 14:19
 */
@Route(path = "/app/LoginActivity")
public class LoginActivity extends BaseUIActivity {

    @BindView(R.id.et_user_login_phone)
    EditText etUserLoginPhone;
    @BindView(R.id.et_user_login_pwd)
    EditText etUserLoginPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //

        topViewManager().topView().setVisibility(View.GONE);
        View view = View.inflate(getPageContext(), R.layout.activity_login, null);
        containerView().addView(view);
        ButterKnife.bind(this, containerView());
        setStatusBarState();
    }

    private void setStatusBarState() {
        //这里注意下 因为在评论区发现有网友调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
        //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(getPageContext(), R.color.white));

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }
    @SingleClick
    @OnClick(R.id.tv_user_login_sure)
    public void onViewClicked() {
        //登录
        String account = etUserLoginPhone.getText().toString();
        String pwd = etUserLoginPwd.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.show("请输入账号");
            //            Notification.show(this, "提示", "请输入账号").setDurationTime(Notification.DURATION_TIME.SHORT);
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("请输入密码");
            return;
        }

        //        Map<String, String> param = ParamsMapUtils.getLoginParams(account, pwd);
        //        JsonCallBack jsonCallBack = new JsonCallBack<UserBean>() {
        //            @Override
        //            public void onSuccess(int code, String msg, UserBean response) {
        //                if (code == 0) {
        //                    //登录成功
        //
        //                } else {
        //                    //登录失败
        //
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Object tag, Exception e) {
        //
        //            }
        //        };
        //        OkGo.post(Api.LOGIN).tag(this).params(param).execute(jsonCallBack);
    }
}
