package com.lyb.qrcodelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.lyb.qrcodelibrary.activity.CaptureResultActivity;
import com.lyb.qrcodelibrary.decode.BitmapDecoder;

/**
 * 二维码处理结果
 * Created by xiao on 2017/5/18.
 */

public class QRCodeUtils {

    /**
     * 二维码结果操作
     *
     * @param context
     * @param content
     */
    public static void qrCodeOper(Context context, String content) {
        if (TextUtils.isEmpty(content)) {
            ((Activity) context).finish();
            return;
        }
        Intent intent = new Intent(context, CaptureResultActivity.class);
        intent.putExtra("result", content);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    /**
     * 长按识别二维码
     *
     * @param bigImage
     * @return
     */
    public static String identificationQRCodeByImageView(Context context, ImageView bigImage) {
        bigImage.setDrawingCacheEnabled(true);
        Bitmap obmp = bigImage.getDrawingCache();
        BitmapDecoder decoder = new BitmapDecoder(context);
        Result result = decoder.getRawResult(obmp);
        bigImage.setDrawingCacheEnabled(false);
        if (result != null) {
            return ResultParser.parseResult(result)
                    .toString();
        }
        return "";
    }

    /**
     * 长按识别二维码
     *
     * @return
     */
    public static String identificationQRCodeByPath(Context context, String photoPath) {
        Bitmap img = BitmapUtils.getCompressedBitmap(photoPath);
        BitmapDecoder decoder = new BitmapDecoder(context);
        Result result = decoder.getRawResult(img);
        if (result != null) {
            return ResultParser.parseResult(result)
                    .toString();
        }
        return "";
    }
}
