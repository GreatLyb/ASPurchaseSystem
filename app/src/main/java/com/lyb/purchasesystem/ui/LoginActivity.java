package com.lyb.purchasesystem.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.bean.UserBean;
import com.lyb.purchasesystem.consta.Api;
import com.lyb.purchasesystem.consta.Constants;
import com.lyb.purchasesystem.consta.ParamsMapUtils;
import com.lysoft.baseproject.imp.SingleClick;
import com.lysoft.baseproject.net.callback.JsonCallBack;
import com.lysoft.baseproject.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-07 14:19
 */
@Route(path = "/app/LoginActivity")
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_user_login_phone)
    EditText etUserLoginPhone;
    @BindView(R.id.et_user_login_pwd)
    EditText etUserLoginPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarState();
        View view = View.inflate(this, R.layout.activity_login, null);
        ButterKnife.bind(this, view);
        setContentView(view);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
//            addBottom();

        }
    }

    private void addBottom() {
        View view = View.inflate(R.layout.item_login_bottom,null);    //加载View视图，这个就是我们要显示的内容
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);    //获取WindowManage
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //设置LayoutParams的属性
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;       //该Type描述的是形成的窗口的层级关系，下面会详细列出它的属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |       //该flags描述的是窗口的模式，是否可以触摸，可以聚焦等
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        layoutParams.gravity = Gravity.BOTTOM;                                       //设置窗口的位置
        layoutParams.format = PixelFormat.TRANSLUCENT;                               //不设置这个弹出框的透明遮罩显示为黑色
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;                //窗口的宽
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;               //窗口的高
//        layoutParams.token = ((View)findViewById(R.id.linearlayout)).getWindowToken();           //获取当前Activity中的View中的TOken,来依附Activity，因为设置了该值，纳闷写的这些代码不能出现在onCreate();否则会报错。
        windowManager.addView(view,layoutParams);

    }

    private void setStatusBarState() {
        //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
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
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> param = ParamsMapUtils.getLoginParams(account, pwd);
        param.put("username", account);
        param.put("password", pwd);
        String s = com.alibaba.fastjson.JSON.toJSONString(param);
        RequestBody requestBody = RequestBody.create(JSON, s);
        JsonCallBack jsonCallBack = new JsonCallBack<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean response) {
                if (code == 200) {
                    //登录成功
                    ARouter.getInstance().build("/app/MainActivity").navigation();
                } else {
                    //登录失败
                    ToastUtils.show("登录失败");
                }
            }

            @Override
            public void onFailure(Object tag, Exception e) {

            }
        };
        OkGo.post(Constants.IP + Api.LOGIN).tag(this).upRequestBody(requestBody).execute(jsonCallBack);
    }
}
