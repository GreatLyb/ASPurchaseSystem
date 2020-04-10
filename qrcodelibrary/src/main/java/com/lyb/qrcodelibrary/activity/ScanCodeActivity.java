package com.lyb.qrcodelibrary.activity;

import android.util.Log;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 10:23
 */

public class ScanCodeActivity extends BaseCaptureActivity {
    @Override
    protected void onHandleDecode(String rawResult) {
        //结果页面
        Log.i("Lyb", "rawResult" + rawResult);
    }
}
