package com.lysoft.baseproject.glide;

import android.widget.ImageView;

/**
 * Created by xiao on 2017/10/24.
 */

public class GlideImageParam {
    public ImageView imageView;
    public String imagePath;
    public int widthDes=0;
    public int heightDes=0;
    public GlideImageUtils.ImageShape imageShape= GlideImageUtils.ImageShape.RECTANGLE;
    public int defaultImageId=0;//是0的时候加载R.drawable.default_img

}
