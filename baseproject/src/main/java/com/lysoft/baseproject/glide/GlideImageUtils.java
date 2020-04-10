package com.lysoft.baseproject.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.lysoft.baseproject.R;
import com.lysoft.baseproject.utils.DensityUtils;
import com.lysoft.baseproject.view.CornerTransform;

;

/**
 * glide加载图片类
 *
 * @author xiao
 */
public class GlideImageUtils {
    public enum ImageShape {
        RECTANGLE, ROUND, CIRCLE
    }

    public static class Builder {
        private GlideImageParam mImageParam;

        public Builder(ImageView imageView, String imagePath) {
            mImageParam = new GlideImageParam();
            mImageParam.imageView = imageView;
            mImageParam.imagePath = imagePath;
        }

        public static Builder getNewInstance(ImageView imageView, String imagePath) {
            return new Builder(imageView, imagePath);
        }

        public Builder widthDes(int width) {
            mImageParam.widthDes = width;
            return this;
        }

        public Builder heightDes(int height) {
            mImageParam.heightDes = height;
            return this;
        }

        public Builder shape(GlideImageUtils.ImageShape shape) {
            mImageParam.imageShape = shape;
            return this;
        }

        public Builder defaultImgaeId(int defaultImageId) {
            mImageParam.defaultImageId = defaultImageId;
            return this;
        }

        public void load() {
            GlideImageUtils.getInstance().loadImage(mImageParam);
        }
    }


    private static GlideImageUtils imageUtils;

    public static GlideImageUtils getInstance() {
        if (imageUtils == null) {
            imageUtils = new GlideImageUtils();
        }
        return imageUtils;
    }

    public void pauseRequests(Context context) {
        if (Util.isOnBackgroundThread()) {
            Glide.with(context).pauseRequests();
        }
    }

    /*加载普通图片*/
    public void loadImage(Context context, int resDefImg, String imagePath, ImageView imageView) {
        try {
            if (Util.isOnMainThread()) {
                Glide.with(context).load(imagePath).placeholder(resDefImg <= 0 ? R.drawable.default_img : resDefImg).error(resDefImg <= 0 ? R.drawable.default_img : resDefImg).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*加载圆角图片
     * xml文件不需要设置CenterCrop
     * */
    public void loadRoundImage(Context context, int resDefImg, String imagePath, ImageView imageView) {
        try {
            if (Util.isOnMainThread()) {
                Glide.with(context).load(imagePath).placeholder(resDefImg <= 0 ? R.drawable.default_img : resDefImg).error(resDefImg <= 0 ? R.drawable.default_img : resDefImg).transform(new GlideRoundTransform(context)).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*加载圆形图片*/
    public void loadCircleImage(Context context, int resDefImg, String imagePath, ImageView imageView) {
        try {
            if (Util.isOnMainThread()) {
                Glide.with(context).load(imagePath).placeholder(resDefImg <= 0 ? R.drawable.default_img : resDefImg).error(resDefImg <= 0 ? R.drawable.default_img : resDefImg).transform(new GlideCircleTransform()).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadHalfRoundImage(Context context, int resDefImg, String imagePath, ImageView imageView) {
        try {
            if (Util.isOnMainThread()) {
                CornerTransform transformation = new CornerTransform(context, DensityUtils.dip2px(context, 5));
                //只是绘制左上角和右上角圆角
                transformation.setExceptCorner(false, false, true, true);
                Glide.with(context).
                        load(imagePath).
                        placeholder(resDefImg <= 0 ? R.drawable.default_img : resDefImg).
                        error(resDefImg <= 0 ? R.drawable.default_img : resDefImg).
                        transform(transformation).
                        into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadImage(GlideImageParam imageParam) {
        switch (imageParam.imageShape) {
            case ROUND:
                loadRoundImage(imageParam.imageView.getContext(), imageParam);
                break;
            case CIRCLE:
                loadCircleImage(imageParam.imageView.getContext(), imageParam);
                break;
            default:
                loadImage(imageParam.imageView.getContext(), imageParam);
                break;
        }
    }

    public void loadCircleImage(Context context, GlideImageParam imageParam) {
        try {
            if (Util.isOnMainThread()) {
                if (imageParam.defaultImageId == 0) {
                    imageParam.defaultImageId = R.drawable.default_head_circle;
                }
                if (imageParam.widthDes != 0 && imageParam.heightDes != 0) {
                    Glide.with(context).load(imageParam.imagePath).placeholder(imageParam.defaultImageId).error(imageParam.defaultImageId).transform(new GlideCircleTransform()).override(imageParam.widthDes, imageParam.heightDes).into(imageParam.imageView);
                } else {
                    Glide.with(context).load(imageParam.imagePath).placeholder(imageParam.defaultImageId).error(imageParam.defaultImageId).transform(new GlideCircleTransform()).into(imageParam.imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadRoundImage(Context context, GlideImageParam imageParam) {
        try {
            if (Util.isOnMainThread()) {
                if (imageParam.defaultImageId == 0) {
                    imageParam.defaultImageId = R.drawable.default_img_round;
                }
                if (imageParam.widthDes != 0 && imageParam.heightDes != 0) {
                    Glide.with(context).load(imageParam.imagePath).placeholder(imageParam.defaultImageId).error(imageParam.defaultImageId).transform(new GlideRoundTransform(context)).override(imageParam.widthDes, imageParam.heightDes).into(imageParam.imageView);
                } else {
                    Glide.with(context).load(imageParam.imagePath).placeholder(imageParam.defaultImageId).error(imageParam.defaultImageId).transform(new GlideRoundTransform(context)).into(imageParam.imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadImage(Context context, GlideImageParam imageParam) {
        try {
            if (Util.isOnMainThread()) {
                if (imageParam.defaultImageId == 0) {
                    imageParam.defaultImageId = R.drawable.default_img;
                }
                if (imageParam.widthDes != 0 && imageParam.heightDes != 0) {
                    Glide.with(context).load(imageParam.imagePath).placeholder(imageParam.defaultImageId).error(imageParam.defaultImageId).override(imageParam.widthDes, imageParam.heightDes).into(imageParam.imageView);
                } else {
                    Glide.with(context).load(imageParam.imagePath).placeholder(imageParam.defaultImageId).error(imageParam.defaultImageId).into(imageParam.imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /*查看大图*/
//    public void lookBigImage(Context context, ArrayList<? extends HHSmallBigImageImp> imageList, int position) {
//        Intent intent = new Intent(context, ImageBrowerActivity.class);
//        intent.putExtra(HHImageBrowerActivity.FLAG_DEFAULT_IMAGE_ID, R.drawable.default_img);
//        intent.putExtra(HHImageBrowerActivity.FLAG_IMAGE_LIST, imageList);
//        intent.putExtra(HHImageBrowerActivity.FLAG_IMAGE_POSITION, position);
//        intent.putExtra(ImageBrowerActivity.FLAG_IMAGE_LENGTH, imageList.size());
//        context.startActivity(intent);
//    }

}
