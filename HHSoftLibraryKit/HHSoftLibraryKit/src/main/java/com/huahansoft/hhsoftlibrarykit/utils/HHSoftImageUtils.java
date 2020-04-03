package com.huahansoft.hhsoftlibrarykit.utils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Consumer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.huahansoft.hhsoftlibrarykit.utils.glide.CustomRoundedCorners;
import com.huahansoft.hhsoftlibrarykit.utils.luban.CompressionPredicate;
import com.huahansoft.hhsoftlibrarykit.utils.luban.Luban;
import com.huahansoft.hhsoftlibrarykit.utils.luban.OnCompressListener;
import com.huahansoft.hhsoftlibrarykit.utils.luban.OnRenameListener;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @类说明 图片加载工具类
 * @作者 hhsoft
 * @创建日期 2019/8/21 16:14
 * 注意：
 * 一、在Android P的系统上，所有Http的请求都被默认阻止了，导致glide在9.0加载不出来图片
 * 解决方案：在清单文件中
 * <application
 * ********
 * android:usesCleartextTraffic="true"
 * **********
 * >
 */
public class HHSoftImageUtils {
    /**
     * 加载矩形图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadRoundImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(HHSoftDensityUtils.dip2px(context, 5));
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = new RequestOptions().transform(new CenterCrop(), roundedCorners);
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .apply(options)
                .into(imageView);

    }

    /**
     * 加载自定义圆角图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     * @param radius                 图片圆角数组、按照左、上、右、下的顺序添加，偿长度是4，单位是dp
     */
    public static void loadCustomuRoundImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView, int[] radius) {
        if (radius == null || radius.length != 4) {
            loadRoundImage(context, defaultImageResourceId, imagePath, imageView);
        } else {
            int leftTopRadius = HHSoftDensityUtils.dip2px(context, radius[0]);
            int rightTopRadius = HHSoftDensityUtils.dip2px(context, radius[1]);
            int leftBottomRadius = HHSoftDensityUtils.dip2px(context, radius[2]);
            int rightBottomRadius = HHSoftDensityUtils.dip2px(context, radius[3]);
            CustomRoundedCorners roundedCorners = new CustomRoundedCorners(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius);
            RequestOptions options = new RequestOptions().transform(new CenterCrop(), roundedCorners);
            Glide.with(context)
                    .asBitmap()
                    .load(imagePath)
                    .placeholder(defaultImageResourceId)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadCircleImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        RequestOptions options = RequestOptions.circleCropTransform();
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                .skipMemoryCache(true);//不做内存缓存
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载动画，只播放一次
     * @param context
     * @param defaultImageResourceId
     * @param imagePath
     * @param imageView
     */
    public static void loadGifImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(defaultImageResourceId)
                .error(defaultImageResourceId)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        if (resource instanceof GifDrawable) {
                            GifDrawable gifDrawable = resource;
                            gifDrawable.setLoopCount(2);
                            imageView.setImageDrawable(resource);
                            gifDrawable.start();
                        }
                    }
                });
    }

    /**
     * 清除图片磁盘缓存
     *
     * @param context
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     *
     * @param context
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { // 只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空Glide缓存
     *
     * @param context
     */
    public static void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String imageExternalCatchDir = context.getExternalCacheDir()
                + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        HHSoftFileUtils.deleteFolder(imageExternalCatchDir);
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @param context
     * @return 返回字节长度，获取失败返回0
     */
    public static long cacheSize(Context context) {
        try {
            String filePath = context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
            return HHSoftFileUtils.fileSize(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取格式化的Glide造成的缓存大小
     *
     * @param context
     * @return 格式话字符串，以Byte、KB、MB、GB、TB结尾
     */
    public static String formatCacheSize(Context context) {
        return getFormatSize(cacheSize(context));
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 图片压缩：异步单张压缩图片
     *
     * @param context
     * @param sourceImagePath 原图片路径
     * @param targetDirPath   目标文件夹路径eg:/storage/emulated/0/HHSoftLib/
     * @param callBack        压缩回调，压缩成功返回压缩后的路径eg:/storage/emulated/0/HHSoftLib/1566460459501169.jpeg；压缩失败返回原路径sourceImagePath
     */
    public static void compressAsync(Context context, final String sourceImagePath, final String targetDirPath, final Consumer<String> callBack) {
        Luban.with(context)
                .load(sourceImagePath)
                .ignoreBy(200)//不压缩的阈值，单位为K
                .setTargetDir(targetDirPath)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || HHSoftFileUtils.FileType.IMAGE_GIF == HHSoftFileUtils.fileTypeForImageData(sourceImagePath));
                    }
                })
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return System.currentTimeMillis() + ".jpg";
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.i("chen", "onStart==");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.i("chen", "onSuccess==" + file.getAbsolutePath());
                        callBack.accept(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("chen", "onError==" + Log.getStackTraceString(e));
                        callBack.accept(sourceImagePath);
                    }
                }).launch();
    }

    /**
     * 图片压缩：异步多张压缩图片
     *
     * @param context
     * @param sourceImages  原图片路径集合
     * @param targetDirPath 目标文件夹路径eg:/storage/emulated/0/HHSoftLib/
     * @param callBack      压缩回调，返回集合，压缩成功返回压缩后的路径eg:/storage/emulated/0/HHSoftLib/1566460459501169.jpeg；压缩失败返回原路径sourceImagePath
     */
    public static void compressListAsync(Context context, final List<String> sourceImages, String targetDirPath, final Consumer<List<String>> callBack) {
        final List<String> compressImageList = new ArrayList<>();
        final int[] position = {0};
        Luban.with(context)
                .load(sourceImages)
                .ignoreBy(200)
                .setTargetDir(targetDirPath)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || HHSoftFileUtils.FileType.IMAGE_GIF == HHSoftFileUtils.fileTypeForImageData(path)) || HHSoftFileUtils.isHttpUrl(path);
                    }
                })
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return System.currentTimeMillis() + ".jpg";
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        position[0]++;
                    }

                    @Override
                    public void onSuccess(File file) {
                        compressImageList.add(file.getAbsolutePath());
                        if (compressImageList.size() == sourceImages.size()) {
                            callBack.accept(compressImageList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        compressImageList.add(sourceImages.get(position[0]));
                        if (compressImageList.size() == sourceImages.size()) {
                            callBack.accept(compressImageList);
                        }
                    }
                }).launch();
    }

    /**
     * 图片压缩：同步单张压缩，避免在UI线程使用，阻塞线程
     *
     * @param context
     * @param sourceImagePath 原图片路径
     * @param targetDirPath   目标文件夹路径eg:/storage/emulated/0/HHSoftLib/
     * @return 压缩成功返回压缩后的路径eg:/storage/emulated/0/HHSoftLib/1566460459501169.jpeg；压缩失败返回原路径sourceImagePath
     */
    public static String compressSync(Context context, String sourceImagePath, String targetDirPath) {
        try {
            return Luban.with(context)
                    .load(sourceImagePath)
                    .ignoreBy(200)
                    .setTargetDir(targetDirPath)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || HHSoftFileUtils.FileType.IMAGE_GIF == HHSoftFileUtils.fileTypeForImageData(path));
                        }
                    })
                    .setRenameListener(new OnRenameListener() {
                        @Override
                        public String rename(String filePath) {
                            return System.currentTimeMillis() + ".jpg";
                        }
                    }).get(sourceImagePath).getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceImagePath;
    }

    /**
     * 图片压缩：同步多张压缩，避免在UI线程使用，阻塞线程
     *
     * @param context
     * @param sourceImageList 原图片路径集合
     * @param targetDirPath   目标文件夹路径eg:/storage/emulated/0/HHSoftLib/
     * @return 图片路径集合，压缩成功返回压缩后的路径eg:/storage/emulated/0/HHSoftLib/1566460459501169.jpeg；压缩失败返回原路径sourceImagePath
     */
    public static List<String> compressListSync(Context context, List<String> sourceImageList, String targetDirPath) {
        try {
            List<File> files = Luban.with(context)
                    .load(sourceImageList)
                    .ignoreBy(200)
                    .setTargetDir(targetDirPath)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || HHSoftFileUtils.FileType.IMAGE_GIF == HHSoftFileUtils.fileTypeForImageData(path));
                        }
                    })
                    .setRenameListener(new OnRenameListener() {
                        @Override
                        public String rename(String filePath) {
                            return System.currentTimeMillis() + ".jpg";
                        }
                    }).get();

            List<String> compressList = new ArrayList<>();
            for (File file : files) {
                compressList.add(file.getAbsolutePath());
            }
            return compressList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceImageList;
    }
}
