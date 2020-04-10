package com.lyb.qrcodelibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.lyb.qrcodelibrary.decode.BitmapDecoder;

/**
 * 二维码处理结果
 * Created by xiao on 2017/5/18.
 */

public class QRCodeUtils {


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
