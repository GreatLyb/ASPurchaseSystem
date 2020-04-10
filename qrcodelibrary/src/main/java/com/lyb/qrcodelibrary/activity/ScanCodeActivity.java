package com.lyb.qrcodelibrary.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.toast.ToastUtils;
import com.kongzue.dialog.interfaces.OnDismissListener;
import com.kongzue.dialog.v3.TipDialog;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-10 10:23
 */
@Route(path = "/qrcodelibrary/ScanCodeActivity")
public class ScanCodeActivity extends BaseCaptureActivity {
    @Override
    protected void onHandleDecode(final String rawResult) {
        //结果页面
        TipDialog.show(ScanCodeActivity.this, "识别成功", TipDialog.TYPE.SUCCESS).setTipTime(500).setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ToastUtils.show("扫描成功==" + rawResult);
                finish();
            }
        });
    }
}
