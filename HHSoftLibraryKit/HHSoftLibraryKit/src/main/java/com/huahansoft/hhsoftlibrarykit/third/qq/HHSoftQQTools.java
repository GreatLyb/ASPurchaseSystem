package com.huahansoft.hhsoftlibrarykit.third.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.third.Event;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftShareInfo;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdConstants;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdTools;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftAppUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftFileUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftTipUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class HHSoftQQTools {
    private String mAppID;
    private Context mContext;
    private Tencent mTencent;
    private IUiListener loginListener;
    private IUiListener userInfoListener;

    public HHSoftQQTools() {
        if (loginListener == null) {
            loginListener = new LoginListener();
        }
        if (userInfoListener != null) {
            userInfoListener = new UserInfoListener();
        }
    }

    public static void init(Context context, String appID) {
        SingletonHolder.mInstance.mContext = context;
        SingletonHolder.mInstance.mAppID = appID;
        SingletonHolder.mInstance.mTencent = Tencent.createInstance(appID, context);
    }

    /**
     * 登录
     *
     * @param activity
     */
    public void login(Activity activity) {
        WeakReference<Activity> mAcivityReference = new WeakReference<>(activity);
        mTencent.login(mAcivityReference.get(), "all", loginListener);
    }

    /**
     * 获取OpenidAndToken
     *
     * @param jsonObject JSONObject对象
     */
    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户信息
     */
    private void updateUserInfo() {
        UserInfo userInfo = new UserInfo(mContext, mTencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);
    }

    /**
     * 重写OnActivityResult()方法,确保可以接收到回调
     *
     * @param resuestCode 请求码
     * @param resultCode  结果码
     * @param data        intent对象
     */
    public void handleActivityResultData(int resuestCode, int resultCode, Intent data) {
        if (resuestCode == Constants.REQUEST_LOGIN || resuestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(resuestCode, resultCode, data, loginListener);
        }
    }

    /**
     * 登录监听
     */
    private class LoginListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            initOpenidAndToken((JSONObject) o);
            updateUserInfo();
        }

        @Override
        public void onError(UiError uiError) {
            HHSoftTipUtils.getInstance().showToast(mContext, uiError.errorDetail);
            HHSoftTipUtils.getInstance().dismissProgressDialog();
        }

        @Override
        public void onCancel() {
            HHSoftTipUtils.getInstance().showToast(mContext, R.string.huahansoft_cancelled);
            HHSoftTipUtils.getInstance().dismissProgressDialog();
        }
    }

    /**
     * 获取用户信息监听
     */
    private class UserInfoListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            JSONObject jsonObject = (JSONObject) o;
            HHSoftQQUserInfo userInfo = new HHSoftQQUserInfo();
            userInfo.setNickname(jsonObject.optString("nickname"));
            userInfo.setGender(jsonObject.optString("gender"));
            userInfo.setFigureurl_qq_2(jsonObject.optString("figureurl_qq_2"));
            userInfo.setProvince(jsonObject.optString("province"));
            userInfo.setCity(jsonObject.optString("city"));
            EventBus.getDefault().post(new Event.ThirdLoginEvent(HHSoftThirdTools.ThirdLoginType.QQ, userInfo));
        }

        @Override
        public void onError(UiError uiError) {
            HHSoftTipUtils.getInstance().showToast(mContext, uiError.errorDetail);
            HHSoftTipUtils.getInstance().dismissProgressDialog();
        }

        @Override
        public void onCancel() {
            HHSoftTipUtils.getInstance().showToast(mContext, R.string.huahansoft_cancelled);
            HHSoftTipUtils.getInstance().dismissProgressDialog();
        }
    }

    /* ================================================================================================*/
    /* QQ分享开始*/

    private Bundle initShareBundle(HHSoftShareInfo shareInfo) {
        Bundle bundle = new Bundle();
        // 设置分享的类型
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        // 设置分享的标题
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareInfo.getShareTitle());
        // 设置分享的描述
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareInfo.getShareDesc());
        // 设置分享的链接的地址啊
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareInfo.getLinkUrl());
        // 设置分享的图片
        if (TextUtils.isEmpty(shareInfo.getImageUrl())) {
            //分享本地图片
            String localPath = HHSoftFileUtils.writeBitmapToFile(shareInfo.getThumpBitmap(), shareInfo.getLocalImagePath());
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, localPath);
        } else {
            //分享网络图片
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareInfo.getImageUrl());
        }
        // 设置分享到QQ的时候显示的应用的名称
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, HHSoftAppUtils.appName(mContext));
        return bundle;
    }

    public void shareToQQ(HHSoftShareInfo shareInfo) {
        Bundle params = initShareBundle(shareInfo);
        if (mTencent != null) {
            // 保存了Activity的一个弱引用，防止因为持有Activity的引用而导致的activity引起的内存泄漏问题
            WeakReference<Activity> mAcivityReference = new WeakReference<>(shareInfo.getActivity());
            mTencent.shareToQQ(mAcivityReference.get(), params, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_QQ, HHSoftThirdConstants.SHARE_RESULT_SUCCESS));
                }

                @Override
                public void onError(UiError uiError) {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_QQ, HHSoftThirdConstants.SHARE_RESULT_FAILURE));
                }

                @Override
                public void onCancel() {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_QQ, HHSoftThirdConstants.SHARE_RESULT_CANCEL));
                }
            });
        }
    }

    public void shareToQzone(HHSoftShareInfo shareInfo) {
        Bundle params = initShareBundle(shareInfo);
        if (mTencent != null) {
            WeakReference<Activity> mAcivityReference = new WeakReference<>(shareInfo.getActivity());
            mTencent.shareToQzone(mAcivityReference.get(), params, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_QQ, HHSoftThirdConstants.SHARE_RESULT_SUCCESS));
                }

                @Override
                public void onError(UiError uiError) {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_QQ, HHSoftThirdConstants.SHARE_RESULT_FAILURE));
                }

                @Override
                public void onCancel() {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_QQ, HHSoftThirdConstants.SHARE_RESULT_CANCEL));
                }
            });
        }
    }

    public static HHSoftQQTools getInstance() {
        return HHSoftQQTools.SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        static HHSoftQQTools mInstance = new HHSoftQQTools();

        private SingletonHolder() {
        }
    }
}
