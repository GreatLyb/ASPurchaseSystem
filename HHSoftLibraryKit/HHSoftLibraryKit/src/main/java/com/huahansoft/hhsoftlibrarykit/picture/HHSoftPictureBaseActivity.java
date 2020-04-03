package com.huahansoft.hhsoftlibrarykit.picture;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.picture.compress.CompressionPredicate;
import com.huahansoft.hhsoftlibrarykit.picture.compress.Luban;
import com.huahansoft.hhsoftlibrarykit.picture.compress.OnCompressListener;
import com.huahansoft.hhsoftlibrarykit.picture.config.PictureConfig;
import com.huahansoft.hhsoftlibrarykit.picture.config.PictureMimeType;
import com.huahansoft.hhsoftlibrarykit.picture.config.PictureSelectionConfig;
import com.huahansoft.hhsoftlibrarykit.picture.entity.EventEntity;
import com.huahansoft.hhsoftlibrarykit.picture.entity.LocalMedia;
import com.huahansoft.hhsoftlibrarykit.picture.rxbus2.RxBus;
import com.huahansoft.hhsoftlibrarykit.picture.rxbus2.RxUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.BitmapUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.DateUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.PictureFileUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.SdkVersionUtils;
import com.huahansoft.hhsoftlibrarykit.ui.HHSoftBaseActivity;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftFileUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftTipUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HHSoftPictureBaseActivity extends HHSoftBaseActivity {

    protected PictureSelectionConfig config;
    protected String originalPath;
    protected String cameraPath, outputCameraPath;

    protected List<LocalMedia> selectionMediaPhotos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        config = PictureSelectionConfig.getInstance();
        setTheme(config.themeStyleId);
        super.onCreate(savedInstanceState);
        initConfig();
    }

    private void initConfig() {
        outputCameraPath = config.outputCameraPath;
    }

    /**
     * compressImage
     */
    protected void compressImage(final List<LocalMedia> result) {
//        showCompressDialog();
        HHSoftTipUtils.getInstance().showProgressDialog(getPageContext(), R.string.huahansoft_waiting, false);
        if (config.synOrAsy) {
            Flowable.just(result)
                    .observeOn(Schedulers.io())
                    .map(list -> {
                        List<File> files =
                                Luban.with(getPageContext())
                                        .loadMediaData(list)
                                        .setTargetDir(config.compressSavePath)
                                        .ignoreBy(config.minimumCompressSize)
                                        .filter(new CompressionPredicate() {
                                            @Override
                                            public boolean apply(String path) {
                                                return !(TextUtils.isEmpty(path) || HHSoftFileUtils.FileType.IMAGE_GIF == HHSoftFileUtils.fileTypeForImageData(path));
                                            }
                                        })
                                        .get();
                        if (files == null) {
                            files = new ArrayList<>();
                        }
                        return files;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(files -> handleCompressCallBack(result, files));
        } else {
            Luban.with(this)
                    .loadMediaData(result)
                    .ignoreBy(config.minimumCompressSize)
                    .setTargetDir(config.compressSavePath)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || HHSoftFileUtils.FileType.IMAGE_GIF == HHSoftFileUtils.fileTypeForImageData(path));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(List<LocalMedia> list) {
                            RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
                            onResult(list);
                        }

                        @Override
                        public void onError(Throwable e) {
                            RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
                            onResult(result);
                        }
                    }).launch();
        }
    }

    /**
     * 重新构造已压缩的图片返回集合
     *
     * @param images
     * @param files
     */
    private void handleCompressCallBack(List<LocalMedia> images, List<File> files) {
        if (files.size() == images.size()) {
            for (int i = 0, j = images.size(); i < j; i++) {
                // 压缩成功后的地址
                String path = files.get(i).getPath();
                LocalMedia image = images.get(i);
                // 如果是网络图片则不压缩
                boolean http = PictureMimeType.isHttp(path);
                boolean eqTrue = !TextUtils.isEmpty(path) && http;
                image.setCompressed(eqTrue ? false : true);
                image.setCompressPath(eqTrue ? "" : path);
            }
        }
        RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
        onResult(images);
    }

    /**
     * return image result
     *
     * @param images
     */
    protected void onResult(List<LocalMedia> images) {
        boolean androidQ = SdkVersionUtils.checkedAndroid_Q();
        boolean isVideo = PictureMimeType.isVideo(images != null && images.size() > 0
                ? images.get(0).getPictureType() : "");
        if (androidQ && !isVideo) {
//            showCompressDialog();
        }
        RxUtils.io(new RxUtils.RxSimpleTask<List<LocalMedia>>() {
            @NonNull
            @Override
            public List<LocalMedia> doSth(Object... objects) {
                if (androidQ && !isVideo) {
                    // Android Q 版本做拷贝应用内沙盒适配
                    int size = images.size();
                    for (int i = 0; i < size; i++) {
                        LocalMedia media = images.get(i);
                        if (media == null || TextUtils.isEmpty(media.getPath())) {
                            continue;
                        }
                        if (media.isCompressed()) {
                            media.setPath(media.getCompressPath());
                        } else if (media.isCut()) {
                            media.setPath(media.getCutPath());
                        } else {
                            String cachedDir = PictureFileUtils.getDiskCacheDir(getApplicationContext());
                            String imgType = PictureMimeType.getLastImgType(media.getPath());
                            String newPath = cachedDir + File.separator + System.currentTimeMillis() + imgType;
                            Bitmap bitmapFromUri = BitmapUtils.getBitmapFromUri(getApplicationContext(),
                                    Uri.parse(media.getPath()));
                            BitmapUtils.saveBitmap(bitmapFromUri, newPath);
                            media.setPath(newPath);
                        }

                    }
                    return images;
                }
                // 非Q版本不做处理
                return images;
            }

            @Override
            public void onNext(List<LocalMedia> mediaList) {
                super.onNext(mediaList);
                HHSoftTipUtils.getInstance().dismissProgressDialog();
                if (config.camera
                        && config.selectionMode == PictureConfig.MULTIPLE
                        && selectionMediaPhotos != null) {
                    mediaList.addAll(mediaList.size() > 0 ? mediaList.size() - 1 : 0, selectionMediaPhotos);
                }
                Intent intent = PictureSelector.putIntentResult(mediaList);
                setResult(RESULT_OK, intent);
                closeActivity();
            }
        });
    }

    /**
     * Close Activity
     */
    protected void closeActivity() {
        finish();
        if (config.camera) {
            overridePendingTransition(0, R.anim.fade_out);
        } else {
            overridePendingTransition(0, R.anim.a3);
        }
    }

    /**
     * 判断拍照 图片是否旋转
     *
     * @param degree
     * @param file
     */
    protected void rotateImage(int degree, File file) {
        if (degree > 0) {
            // 针对相片有旋转问题的处理方式
            try {
                //获取缩略图显示到屏幕上
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                Bitmap bmp = PictureFileUtils.rotaingImageView(degree, bitmap);
                PictureFileUtils.saveBitmapFile(bmp, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取DCIM文件下最新一条拍照记录
     *
     * @return
     */
    protected int getLastImageId(boolean eqVideo) {
        try {
            //selection: 指定查询条件
            String absolutePath = PictureFileUtils.getDCIMCameraPath(this);
            String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";
            String selection = eqVideo ? MediaStore.Video.Media.DATA + " like ?" :
                    MediaStore.Images.Media.DATA + " like ?";
            //定义selectionArgs：
            String[] selectionArgs = {absolutePath + "%"};
            Cursor imageCursor = this.getContentResolver().query(eqVideo ?
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    selection, selectionArgs, ORDER_BY);
            if (imageCursor.moveToFirst()) {
                int id = imageCursor.getInt(eqVideo ?
                        imageCursor.getColumnIndex(MediaStore.Video.Media._ID)
                        : imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                long date = imageCursor.getLong(eqVideo ?
                        imageCursor.getColumnIndex(MediaStore.Video.Media.DURATION)
                        : imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                int duration = DateUtils.dateDiffer(date);
                imageCursor.close();
                // DCIM文件下最近时间30s以内的图片，可以判定是最新生成的重复照片
                return duration <= 30 ? id : -1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除部分手机 拍照在DCIM也生成一张的问题
     *
     * @param id
     * @param eqVideo
     */
    protected void removeImage(int id, boolean eqVideo) {
        try {
            ContentResolver cr = getContentResolver();
            Uri uri = eqVideo ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String selection = eqVideo ? MediaStore.Video.Media._ID + "=?"
                    : MediaStore.Images.Media._ID + "=?";
            cr.delete(uri,
                    selection,
                    new String[]{Long.toString(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 录音
     *
     * @param data
     */
    protected String getAudioPath(Intent data) {
        boolean compare_SDK_19 = Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT;
        if (data != null && config.mimeType == PictureMimeType.ofAudio()) {
            try {
                Uri uri = data.getData();
                final String audioPath;
                if (compare_SDK_19) {
                    audioPath = uri.getPath();
                } else {
                    audioPath = getAudioFilePathFromUri(uri);
                }
                return audioPath;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取刚录取的音频文件
     *
     * @param uri
     * @return
     */
    protected String getAudioFilePathFromUri(Uri uri) {
        String path = "";
        try {
            Cursor cursor = getContentResolver()
                    .query(uri, null, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
            path = cursor.getString(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
