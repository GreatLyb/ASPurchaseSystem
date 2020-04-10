package com.lyb.purchasesystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lyb.purchasesystem.R;
import com.lyb.purchasesystem.consta.PermissionsConstant;
import com.lyb.qrcodelibrary.activity.ScanCodeActivity;
import com.lysoft.baseproject.activity.BaseUIActivity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * ASPurchaseSystem
 * 类描述：启动页
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-03 14:39
 */
@Route(path = "/app/SplashActivity")
public class SplashActivity extends BaseUIActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statuBarView().setBackgroundColor(ContextCompat.getColor(getPageContext(), com.lysoft.baseproject.R.color.white));
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
        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                ARouter.getInstance().build("/app/MainActivity").navigation();
                //                ARouter.getInstance().build("/app/LoginActivity").navigation();
                startActivity(new Intent(getPageContext(), ScanCodeActivity.class));
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
