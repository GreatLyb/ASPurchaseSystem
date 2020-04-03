package com.huahansoft.hhsoftlibrarykit.third.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.huahansoft.hhsoftlibrarykit.third.Event;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftShareInfo;
import com.huahansoft.hhsoftlibrarykit.third.HHSoftThirdConstants;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftStreamUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Map;

/**
 * @类说明 新浪工具类
 * @作者 hhsoft
 * @创建日期 2019/9/2 11:47
 */
public class HHSoftSinaTools {
    private Context mContext;
    private String mAppKey;
    private WbShareHandler mShareHandler;
    private static final int THUMB_SIZE = 150;

    public HHSoftSinaTools() {
    }

    public static void init(Context context, String appKey) {
        HHSoftSinaTools.getInstance().mContext = context;
        HHSoftSinaTools.getInstance().mAppKey = appKey;
        WbSdk.install(context, new AuthInfo(context, appKey, HHSoftThirdConstants.REDIRECT_URL, HHSoftThirdConstants.SCOPE));
    }


    public void shareToSina(HHSoftShareInfo shareInfo) {
        if (0 == shareInfo.getWeiboShareType()) {
            shareWebpage(shareInfo);
        }
    }

    private void shareWebpage(HHSoftShareInfo shareInfo) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareInfo.getShareTitle();
        mediaObject.description = shareInfo.getShareDesc();
        Bitmap bitmap;
        if (TextUtils.isEmpty(shareInfo.getImageUrl())) {
            long byteSize = HHSoftStreamUtils.getBitmapByteSize(shareInfo.getThumpBitmap());
            if (byteSize > 32 * 1024) {
                bitmap = Bitmap.createScaledBitmap(shareInfo.getThumpBitmap(), THUMB_SIZE, THUMB_SIZE, true);
            } else {
                bitmap = shareInfo.getThumpBitmap();
            }
        } else {
            try {
                bitmap = BitmapFactory.decodeStream(new URL(shareInfo.getImageUrl()).openStream());
            } catch (IOException e) {
                e.printStackTrace();
                bitmap = shareInfo.getThumpBitmap();
            }
        }
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = shareInfo.getLinkUrl();
        mediaObject.defaultText = shareInfo.getShareTitle();
        weiboMessage.mediaObject = mediaObject;
        WeakReference<Activity> mAcivityReference = new WeakReference<>(shareInfo.getActivity());
        if (mShareHandler == null) {
            mShareHandler = new WbShareHandler(mAcivityReference.get());
            mShareHandler.registerApp();
        }
        mShareHandler.shareMessage(weiboMessage, false);
    }

    public void handleShareResultData(Intent data){
        if (mShareHandler!=null){
            mShareHandler.doResultIntent(data, new WbShareCallback() {
                @Override
                public void onWbShareSuccess() {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_WEIBO, HHSoftThirdConstants.SHARE_RESULT_SUCCESS));
                }

                @Override
                public void onWbShareCancel() {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_WEIBO, HHSoftThirdConstants.SHARE_RESULT_CANCEL));
                }

                @Override
                public void onWbShareFail() {
                    EventBus.getDefault().post(new Event.ThirdShareEvent(HHSoftThirdConstants.SHARE_TYPE_WEIBO, HHSoftThirdConstants.SHARE_RESULT_FAILURE));
                }
            });
        }
    }
    public static HHSoftSinaTools getInstance() {
        return HHSoftSinaTools.SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        static HHSoftSinaTools mInstance = new HHSoftSinaTools();

        private SingletonHolder() {
        }
    }
}
