package com.lyb.purchasesystem.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.consta.PermissionsConstant;
import com.lysoft.baseproject.activity.BaseUIActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * ASPurchaseSystem
 * 类描述：启动页
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-03 14:39
 */
public class SplashActivity extends BaseUIActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getPageContext(), R.layout.activity_splash, null);
        topViewManager().topView().setVisibility(View.GONE);
        containerView().addView(view);
        if (!checkPermission(PermissionsConstant.PERMISSIONS_CAMERA_AND_STORAGE)) {
            requestPermission("请授予APP读写和相机权限", PermissionsConstant.PERMISSIONS_CAMERA_AND_STORAGE);
        } else {
            splashEnd();
        }
    }

    @Override
    protected void permissionsGranted() {
        super.permissionsGranted();
        //权限成功
        splashEnd();
    }

    private void splashEnd() {
        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                ARouter.getInstance().build("/app/MainActivity").navigation();
                ARouter.getInstance().build("/app/LoginActivity").navigation();
                finish();
            }
        };
        countDownTimer.start();
    }


    @Override
    protected void permissionsDenied(List<String> perms) {
        super.permissionsDenied(perms);
        //权限失败

    }
}
