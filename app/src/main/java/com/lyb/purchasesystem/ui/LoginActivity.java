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

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
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

        statuBarView().setVisibility(View.GONE);
        topViewManager().topView().setVisibility(View.GONE);
        View view = View.inflate(getPageContext(), R.layout.activity_login, null);
        containerView().addView(view);
        ButterKnife.bind(this, containerView());
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
