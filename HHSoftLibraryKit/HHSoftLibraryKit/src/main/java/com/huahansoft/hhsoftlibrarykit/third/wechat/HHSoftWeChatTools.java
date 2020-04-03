package com.huahansoft.hhsoftlibrarykit.third.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftShareInfo;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdConstants;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftNetworkUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftStreamUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftTipUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @类说明 HHSoftWeChatTools
 * @作者 hhsoft
 * @创建日期 2019/8/30 11:40
 * 一、Application 中初始化工具类：HHSoftWeChatTools.init(getApplicationContext(),"");
 * 二、移动应用微信授权登录
 * 1、第一步：请求CODE，调用sendAuthReq();方法，SDK通过SendAuth的Resp返回数据给调用方
 * 2、第二步：通过code获取access_token:请求以下链接
 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
 * 3、第三步：通过access_token调用接口
 */
public class HHSoftWeChatTools {
    private static final String TAG = "HHSoftWeChatTools";
    private IWXAPI mWXApi;
    private Context mContext;
    private String mAppID;
    private String ACCESS_TOKEN_IP = "https://api.weixin.qq.com/";
    private String ACCESS_TOKEN_METHOD = "sns/oauth2/access_token";
    private String USER_INFO_METHOD = "/sns/userinfo";
    private static final int THUMB_SIZE = 150;

    public HHSoftWeChatTools() {

    }

    /**
     * 微信工具类初始化
     *
     * @param context
     * @param appID
     */
    public static void init(Context context, String appID) {
        HHSoftWeChatTools.SingletonHolder.mInstance.mContext = context;
        HHSoftWeChatTools.SingletonHolder.mInstance.mAppID = appID;
        HHSoftWeChatTools.SingletonHolder.mInstance.registerAppToWX(context, appID);
    }

    /**
     * 注册APP到微信
     *
     * @param context 上下文
     * @param appID   微信注册appid
     */
    public void registerAppToWX(Context context, String appID) {
        if (TextUtils.isEmpty(appID)) {
            throw new RuntimeException("please set weixin appid");
        }
        mWXApi = WXAPIFactory.createWXAPI(context, appID, false);
        mWXApi.registerApp(appID);
    }

    /**
     * 登录
     */
    public void login() {
        if (!mWXApi.isWXAppInstalled()) {
            HHSoftTipUtils.getInstance().showToast(mContext, R.string.hhsoft_install_wechat);
            return;
        }
        sendAuthReq();
    }

    /**
     * 第一步：请求CODE
     */
    public void sendAuthReq() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_hhsoft";
        mWXApi.sendReq(req);
    }

    /**
     * 第二步：通过code获取access_token
     *
     * @param handler Handler
     * @param code    code
     */
    public void getAccessToken(Handler handler, String code) {
        new Thread(() -> {
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("appid", mAppID);
            paraMap.put("secret", "SECRET");
            paraMap.put("code", code);
            paraMap.put("grant_type", "authorization_code");
            HHSoftNetworkUtils.getInstance().getRequest(ACCESS_TOKEN_IP, ACCESS_TOKEN_METHOD, paraMap,
                    result -> {
                        HHSoftWeChatTokenInfo tokenInfo = new HHSoftWeChatTokenInfo();
                        JSONObject jsonObject = new JSONObject(result);
                        tokenInfo.setOpenid(jsonObject.optString("openid"));
                        tokenInfo.setAccessToken(jsonObject.optString("access_token"));
                        tokenInfo.setExpiresIn(jsonObject.optString("expires_in"));
                        tokenInfo.setRefreshToken(jsonObject.optString("refresh_token"));
                        tokenInfo.setScope(jsonObject.optString("scope"));
                        tokenInfo.setUnionid(jsonObject.optString("unionid"));
                        //当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段
                        Message msg = handler.obtainMessage();
                        msg.what = HHSoftThirdConstants.GET_ACCESS_TOKEN;
                        if (!TextUtils.isEmpty(tokenInfo.getUnionid())) {
                            msg.obj = tokenInfo;
                        }
                        handler.sendMessage(msg);
                    }, t -> {
                        Message msg = handler.obtainMessage();
                        msg.what = HHSoftThirdConstants.GET_ACCESS_TOKEN;
                        handler.sendMessage(msg);
                    });
        }).start();
    }

    /**
     * 第三步：通过access_token调用接口
     *
     * @param handler   Handler
     * @param tokenInfo token信息
     */
    public void getUserInfo(Handler handler, HHSoftWeChatTokenInfo tokenInfo) {
        new Thread(() -> {
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("access_token", tokenInfo.getAccessToken());
            paraMap.put("openid", tokenInfo.getOpenid());
            HHSoftNetworkUtils.getInstance().getRequest(ACCESS_TOKEN_IP, USER_INFO_METHOD, paraMap,
                    result -> {
                        HHSoftWeChatUserInfo userInfo = new HHSoftWeChatUserInfo();
                        JSONObject jsonObject = new JSONObject(result);
                        userInfo.setOpenid(jsonObject.optString("openid"));
                        userInfo.setNickname(jsonObject.optString("nickname"));
                        userInfo.setSex(jsonObject.optString("sex"));
                        userInfo.setProvince(jsonObject.optString("province"));
                        userInfo.setCity(jsonObject.optString("city"));
                        userInfo.setCountry(jsonObject.optString("country"));
                        userInfo.setHeadimgurl(jsonObject.optString("headimgurl"));
                        userInfo.setUnionid(jsonObject.optString("unionid"));
                        Message msg = handler.obtainMessage();
                        msg.what = HHSoftThirdConstants.GET_USER_INFO;
                        if (!TextUtils.isEmpty(userInfo.getUnionid())) {
                            msg.obj = userInfo;
                        }
                        handler.sendMessage(msg);
                    },
                    t -> {
                        Message msg = handler.obtainMessage();
                        msg.what = HHSoftThirdConstants.GET_USER_INFO;
                        handler.sendMessage(msg);
                    });
        }).start();
    }

    /**
     * 微信支付
     *
     * @param payInfo 微信支付信息
     */
    public void pay(HHSoftWeChatPayInfo payInfo) {
        if (mWXApi != null) {
            boolean isPaySupported = mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
            if (isPaySupported) {
                PayReq req = new PayReq();
                req.appId = payInfo.getAppid();
                req.partnerId = payInfo.getPartnerid();
                req.prepayId = payInfo.getPrepayid();
                req.packageValue = payInfo.getPackageValue();
                req.nonceStr = payInfo.getNoncestr();
                req.timeStamp = payInfo.getTimestamp();
                req.sign = payInfo.getSign();
                mWXApi.sendReq(req);
            } else {
                HHSoftTipUtils.getInstance().showToast(mContext, R.string.hhsoft_wechat_pay_unsupported);
            }
        }
    }

    /*=============================================================================================*/
    /*微信分享*/

    /**
     * 微信分享
     *
     * @param shareInfo 微信分享信息
     */
    public void shareToWeChat(HHSoftShareInfo shareInfo) {
        if (0 == shareInfo.getWechatShareType()) {
            //网页分享
            shareWebpage(shareInfo);
        } else if (1 == shareInfo.getWechatShareType()) {
            //图片分享
            shareImage(shareInfo);
        }
    }

    /**
     * 分享图片
     *
     * @param shareInfo
     */
    private void shareImage(HHSoftShareInfo shareInfo) {
        try {
            WXImageObject imgObj = null;
            Bitmap thumbBitmap = null;
            // 设置分享的时候显示的图片
            if (TextUtils.isEmpty(shareInfo.getImageUrl())) {
                // 如果mShareModel中包含了一个缩略图，则使用这个缩略图创建一个指定了狂傲的缩略图
                Bitmap mBitmap = shareInfo.getThumpBitmap();
                if (mBitmap == null) {
                    return;
                }
                long byteSize = HHSoftStreamUtils.getBitmapByteSize(mBitmap);
                imgObj = new WXImageObject(mBitmap);
                if (byteSize > 32 * 1024) {
                    thumbBitmap = Bitmap.createScaledBitmap(mBitmap, THUMB_SIZE, THUMB_SIZE, true);
                } else {
                    thumbBitmap = mBitmap;
                }
            } else {
                // 如果mShareModel中没有缩略图，只有一个缩略图的地址，则需要根据这个地址了下载这个缩略图，因此使用的这个缩略图的地址对应的这个
                // 图片不应该适用过大的图片
                Bitmap bmp = BitmapFactory.decodeStream(new URL(shareInfo.getImageUrl()).openStream());
                if (bmp == null) {
                    bmp = shareInfo.getThumpBitmap();
                }
                imgObj = new WXImageObject(bmp);
                // 根据从网上获取的图片创建一个指定大小的缩略图
                thumbBitmap = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            }
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            msg.thumbData = HHSoftStreamUtils.bitmap2Bytes(thumbBitmap, 32 * 1024);
            // 实例化一个发送消息的请求
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            // 设置这个请求的一个唯一的标识信息
            req.transaction = buildTransaction("img");
            // 设置这个请求发送的消息
            req.message = msg;
            // 设置这个请求应用的场景，这个场景确定了分享到微信朋友圈还是微信的好友
            req.scene = shareInfo.getWechatShareScene();
            // 调用微信的api发送这个请求
            mWXApi.sendReq(req);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i(TAG, "shareToWX分享错误" + e.getMessage());
        }
    }

    /**
     * 网页分享
     *
     * @param shareInfo
     */
    private void shareWebpage(HHSoftShareInfo shareInfo) {
        WXWebpageObject webpage = new WXWebpageObject();
        // 设置网页分享的地址
        webpage.webpageUrl = shareInfo.getLinkUrl();
        // 分享的消息
        WXMediaMessage msg = new WXMediaMessage(webpage);
        // 分享的标题
        msg.title = shareInfo.getShareTitle();
        // 分享的描述信息
        msg.description = shareInfo.getShareDesc();
        if (TextUtils.isEmpty(shareInfo.getImageUrl())) {
            Bitmap thumbBmp = Bitmap.createScaledBitmap(shareInfo.getThumpBitmap(), THUMB_SIZE, THUMB_SIZE, true);
//            shareInfo.getThumpBitmap().recycle();
            msg.thumbData = HHSoftStreamUtils.convertBitmapToByteArray(thumbBmp, true);
        } else {
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(new URL(shareInfo.getImageUrl()).openStream());
            } catch (IOException e) {
                e.printStackTrace();
                bitmap = shareInfo.getThumpBitmap();
            }
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//            bitmap.recycle();
            msg.thumbData = HHSoftStreamUtils.convertBitmapToByteArray(thumbBmp, true);
        }
        // 实例化一个发送消息的请求
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // 设置这个请求的一个唯一的标识信息
        req.transaction = buildTransaction("webpage");
        // 设置这个请求发送的消息
        req.message = msg;
        // 设置这个请求应用的场景，这个场景确定了分享到微信朋友圈还是微信的好友
        req.scene = shareInfo.getWechatShareScene();
        // 调用微信的api发送这个请求
        mWXApi.sendReq(req);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public IWXAPI wxapi() {
        return mWXApi;
    }

    public static HHSoftWeChatTools getInstance() {
        return HHSoftWeChatTools.SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        static HHSoftWeChatTools mInstance = new HHSoftWeChatTools();

        private SingletonHolder() {
        }
    }
}
