package com.lyb.qrcodelibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ThumbnailUtils;

/**
 * 项目名称 : ZXingScanQRCode<br>
 * 创建人 : skycracks<br>
 * 创建时间 : 2016-4-19下午9:53:29<br>
 * 版本 :	[v1.0]<br>
 * 类描述 : LOGO图片加上白色背景图片<br>
 */
public class LogoConfig {
    /**
     * @return 返回带有白色背景框logo
     */
    public static Bitmap modifyLogo(Bitmap bgBitmap, Bitmap logoBitmap) {

        //读取背景图片，并构建绘图对象
        int bgWidth = bgBitmap.getWidth();
        int bgHeigh = bgBitmap.getHeight();
        int logoWidth = bgWidth / 8;
        int logoHeight = bgHeigh / 8;
        //通过ThumbnailUtils压缩原图片，并指定宽高为背景图的3/4
        logoBitmap = ThumbnailUtils.extractThumbnail(logoBitmap, logoWidth, logoHeight);
        Bitmap cvBitmap = Bitmap.createBitmap(bgWidth, bgHeigh, Config.ARGB_8888);
        Canvas canvas = new Canvas(cvBitmap);
        // 开始绘制图片
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(logoBitmap, (bgWidth - logoBitmap.getWidth()) / 2, (bgHeigh - logoBitmap.getHeight()) / 2, null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF();                           //RectF对象
        rectF.left = (bgWidth - logoBitmap.getWidth()) / 2;                                 //左边
        rectF.top = (bgWidth - logoBitmap.getHeight()) / 2;                                 //上边
        rectF.right = (bgWidth - logoBitmap.getHeight()) / 2 + logoWidth;                                   //右边
        rectF.bottom = (bgWidth - logoBitmap.getHeight()) / 2 + logoHeight;
        canvas.drawRoundRect(rectF, 8, 8, paint);
        canvas.save();// 保存
        canvas.restore();
        return cvBitmap;
    }
}
