package com.huahansoft.hhsoftlibrarykit.third.alipay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.huahansoft.hhsoftlibrarykit.third.Event;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdConstants;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @类说明 支付宝支付工具类
 * @作者 hhsoft
 * @创建日期 2019/9/2 11:47
 */
public class HHSoftAlipayTools {

    public HHSoftAlipayTools() {

    }

    /**
     * 支付宝支付
     *
     * @param activity  上下文
     * @param orderInfo 订单信息 app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。
     */
    public void pay(Activity activity, String orderInfo) {
        new Thread(() -> {
            WeakReference<Activity> mAcivityReference = new WeakReference<>(activity);
            PayTask alipay = new PayTask(mAcivityReference.get());
            Map<String, String> resultMap = alipay.payV2(orderInfo, true);
            boolean isPaySuccess = isPaySuccess(resultMap);
            mAcivityReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new Event.ThirdPayEvent(HHSoftThirdConstants.PAY_TYPE_ALIPAY, isPaySuccess ? HHSoftThirdConstants.PAY_RESULT_SUCCESS : HHSoftThirdConstants.PAY_RESULT_FAILURE));
                }
            });
        }).start();
    }

    /**
     * {
     * "memo" : "xxxxx",
     * "result" : "{
     * \"alipay_trade_app_pay_response\":{
     * \"code\":\"10000\",
     * \"msg\":\"Success\",
     * \"app_id\":\"2014072300007148\",
     * \"out_trade_no\":\"081622560194853\",
     * \"trade_no\":\"2016081621001004400236957647\",
     * \"total_amount\":\"0.01\",
     * \"seller_id\":\"2088702849871851\",
     * \"charset\":\"utf-8\",
     * \"timestamp\":\"2016-10-11 17:43:36\"
     * },
     * \"sign\":\"NGfStJf3i3ooWBuCDIQSumOpaGBcQz+aoAqyGh3W6EqA/gmyPYwLJ2REFijY9XPTApI9YglZyMw+ZMhd3kb0mh4RAXMrb6mekX4Zu8Nf6geOwIa9kLOnw0IMCjxi4abDIfXhxrXyj********\",
     * \"sign_type\":\"RSA2\"
     * }",
     * "resultStatus" : "9000"
     * }
     * <p>
     * 返回码	含义
     * 9000	订单支付成功
     * 8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 4000	订单支付失败
     * 5000	重复请求
     * 6001	用户中途取消
     * 6002	网络连接出错
     * 6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 其它	其它支付错误
     *
     * @param payResultMap
     */
    private boolean isPaySuccess(Map<String, String> payResultMap) {
        if (payResultMap != null) {
            if (payResultMap.containsKey("resultStatus")) {
                if ("9000".equals(payResultMap.get("resultStatus"))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static HHSoftAlipayTools getInstance() {
        return HHSoftAlipayTools.SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        static HHSoftAlipayTools mInstance = new HHSoftAlipayTools();

        private SingletonHolder() {
        }
    }
}
