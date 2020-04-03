package com.huahansoft.hhsoftlibrarykit.third;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.huahansoft.hhsoftlibrarykit.third.qq.HHSoftQQTools;
import com.huahansoft.hhsoftlibrarykit.third.wechat.HHSoftWeChatTools;
import com.huahansoft.hhsoftlibrarykit.third.weibo.HHSoftSinaTools;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.Map;

public class HHSoftThirdTools {
    public static final String KEY_WECHAT_APP_ID = "key_wechat_app_id";
    public static final String KEY_QQ_APP_ID = "key_qq_app_id";
    private ThirdLoginType mLoginType;

    public HHSoftThirdTools() {
    }

    public static void init(Context context, Map<String, String> appIDMap) {
        if (appIDMap == null) {
            throw new RuntimeException("please set appid");
        }
        if (appIDMap.containsKey(KEY_QQ_APP_ID)) {
            HHSoftQQTools.init(context, appIDMap.get(KEY_QQ_APP_ID));
        }
        if (appIDMap.containsKey(KEY_WECHAT_APP_ID)) {
            HHSoftWeChatTools.init(context, appIDMap.get(KEY_WECHAT_APP_ID));
        }
    }

    public void thirdShare(int platformID, HHSoftShareInfo shareInfo) {
        switch (platformID) {
            case HHSoftThirdConstants.SHARE_TYPE_WECHAT:
                shareInfo.setWechatShareScene(SendMessageToWX.Req.WXSceneSession);
                HHSoftWeChatTools.getInstance().shareToWeChat(shareInfo);
                break;
            case HHSoftThirdConstants.SHARE_TYPE_WECHAT_TIMELINE:
                shareInfo.setWechatShareScene(SendMessageToWX.Req.WXSceneTimeline);
                HHSoftWeChatTools.getInstance().shareToWeChat(shareInfo);
                break;
            case HHSoftThirdConstants.SHARE_TYPE_QQ:
                HHSoftQQTools.getInstance().shareToQQ(shareInfo);
                break;
            case HHSoftThirdConstants.SHARE_TYPE_QZONE:
                HHSoftQQTools.getInstance().shareToQzone(shareInfo);
                break;
            case HHSoftThirdConstants.SHARE_TYPE_WEIBO:
                HHSoftSinaTools.getInstance().shareToSina(shareInfo);
                break;
            default:
                break;
        }
    }

    public void thirdLogin(Activity activity, ThirdLoginType loginType) {
        this.mLoginType = loginType;
        switch (loginType) {
            case QQ:
                HHSoftQQTools.getInstance().login(activity);
                break;
            case WECHAT:
                HHSoftWeChatTools.getInstance().login();
                break;
            default:
                break;
        }
    }

    public void handleThirdLoginResult(int resuestCode, int resultCode, Intent data) {
        if (ThirdLoginType.QQ == mLoginType) {
            HHSoftQQTools.getInstance().handleActivityResultData(resuestCode, resultCode, data);
        }
    }

    public void handleThirdShareResult(Intent data) {
        HHSoftSinaTools.getInstance().handleShareResultData(data);
    }

    public enum ThirdLoginType {
        WECHAT, QQ, WEIBO
    }

    public static HHSoftThirdTools getInstance() {
        return HHSoftThirdTools.SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        static HHSoftThirdTools mInstance = new HHSoftThirdTools();

        private SingletonHolder() {
        }
    }
}
