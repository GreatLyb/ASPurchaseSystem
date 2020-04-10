package com.lysoft.baseproject.view;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * 类描述：
 * 类传参：
 *
 * @author android.yml
 * @date 2019/7/5 17:15
 */
public class CornerTransform implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;

    private float radius;

    private boolean exceptLeftTop, exceptRightTop, exceptLeftBottom, exceptRightBotoom;

    /**
     * 除了那几个角不需要圆角的
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    public void setExceptCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        this.exceptLeftTop = leftTop;
        this.exceptRightTop = rightTop;
        this.exceptLeftBottom = leftBottom;
        this.exceptRightBotoom = rightBottom;
    }

    public CornerTransform(Context context, float radius) {
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.radius = radius;
    }


    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        return null;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    //    public int hashCode() {
//        //避免Transformation重复设置,导致图片闪烁,同一个圆角值的Transformation视为同一个对象
//        return Util.hashCode(getId().hashCode(), Util.hashCode(this.radius));
//    }
}