package com.lysoft.baseproject.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.lcw.library.imagepicker.utils.ImageLoader;
import com.lysoft.baseproject.R;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-27 15:54
 */
public class GlideLoader implements ImageLoader {
    private Context context;
    private RequestOptions mOptions = new RequestOptions()
            .centerCrop()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.mipmap.icon_image_default)
            .error(R.drawable.default_img);
    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            .error(R.drawable.default_img);

    public GlideLoader(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(ImageView imageView, String imagePath) {
        //小图加载
        Glide.with(imageView.getContext()).load(imagePath).apply(mOptions).into(imageView);
    }

    @Override
    public void loadPreImage(ImageView imageView, String imagePath) {
        //大图加载
        Glide.with(imageView.getContext()).load(imagePath).apply(mPreOptions).into(imageView);

    }

    @Override
    public void clearMemoryCache() {
        //清理缓存
        Glide.get(context).clearMemory();
    }
}
