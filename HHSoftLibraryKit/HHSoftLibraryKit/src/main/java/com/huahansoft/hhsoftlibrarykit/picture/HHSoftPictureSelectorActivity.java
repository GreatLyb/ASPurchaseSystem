package com.huahansoft.hhsoftlibrarykit.picture;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.picture.adapter.HHSoftPictureFolderAdapter;
import com.huahansoft.hhsoftlibrarykit.picture.adapter.HHSoftPictureGridAdapter;
import com.huahansoft.hhsoftlibrarykit.picture.config.PictureConfig;
import com.huahansoft.hhsoftlibrarykit.picture.config.PictureMimeType;
import com.huahansoft.hhsoftlibrarykit.picture.decoration.GridSpacingItemDecoration;
import com.huahansoft.hhsoftlibrarykit.picture.entity.EventEntity;
import com.huahansoft.hhsoftlibrarykit.picture.entity.LocalMedia;
import com.huahansoft.hhsoftlibrarykit.picture.entity.LocalMediaFolder;
import com.huahansoft.hhsoftlibrarykit.picture.model.LocalMediaLoader;
import com.huahansoft.hhsoftlibrarykit.picture.observable.ImagesObservable;
import com.huahansoft.hhsoftlibrarykit.picture.rxbus2.RxBus;
import com.huahansoft.hhsoftlibrarykit.picture.rxbus2.Subscribe;
import com.huahansoft.hhsoftlibrarykit.picture.rxbus2.ThreadMode;
import com.huahansoft.hhsoftlibrarykit.picture.tools.DoubleUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.PhotoTools;
import com.huahansoft.hhsoftlibrarykit.picture.tools.PictureFileUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.SdkVersionUtils;
import com.huahansoft.hhsoftlibrarykit.picture.tools.StringUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftDensityUtils;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftTipUtils;
import com.huahansoft.hhsoftlibrarykit.window.HHSoftBottomMenuWindow;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class HHSoftPictureSelectorActivity extends HHSoftPictureBaseActivity implements View.OnClickListener {
    /**
     * 权限
     */
    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ImageView backImageView;
    private TextView titleTextView;
    private TextView sureTextView;
    private RecyclerView photoRecyclerView;
    private TextView folderTextView;
    private TextView previewTextView;
    private FrameLayout folderLayout;
    private ListView folderListView;
    private LinearLayout originalLayout;
    private ImageView originalImageView;

    private LocalMediaLoader mediaLoader;
    private List<LocalMediaFolder> mediaFolders = new ArrayList<>();
    private List<LocalMedia> mediaPhotos = new ArrayList<>();
    private HHSoftPictureGridAdapter photoAdapter;
    private HHSoftPictureFolderAdapter folderAdapter;


    private HHSoftBottomMenuWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }
        if (!checkPermission(permissions)) {
            requestPermission("", permissions);
            setContentView(new TextView(this));
        } else {
            initView();
        }
    }

    private void initView() {
        setContentView(R.layout.hhsoft_picture_activity_selector);
        backImageView = findViewById(R.id.hhsoft_iv_picture_top_back);
        titleTextView = findViewById(R.id.hhsoft_tv_picture_top_title);
        sureTextView = findViewById(R.id.hhsoft_tv_picture_top_sure);
        photoRecyclerView = findViewById(R.id.hhsoft_picture_recycler);
        folderTextView = findViewById(R.id.hhsoft_tv_picture_folder_select);
        previewTextView = findViewById(R.id.hhsoft_tv_picture_preview);
        originalLayout = findViewById(R.id.hhsoft_ll_picture_original);
        originalImageView = findViewById(R.id.hhsoft_iv_picture_original);
        folderLayout = findViewById(R.id.hhsoft_fl_picture_floder);
        folderListView = findViewById(R.id.hhsoft_lv_picture_floder);
        backImageView.setOnClickListener(this);
        sureTextView.setOnClickListener(this);
        folderTextView.setOnClickListener(this);
        originalLayout.setOnClickListener(this);
        previewTextView.setOnClickListener(this);
        if (config.mimeType == PictureMimeType.ofAll()) {
            titleTextView.setText(R.string.hhsoft_picture_title_name);
            folderTextView.setText(R.string.hhsoft_picture_title_name);
        } else if (config.mimeType == PictureMimeType.ofImage()) {
            titleTextView.setText(R.string.hhsoft_picture_title_name_image);
            folderTextView.setText(R.string.hhsoft_picture_title_name_image);
        } else if (config.mimeType == PictureMimeType.ofVideo()) {
            titleTextView.setText(R.string.hhsoft_picture_title_name_video);
            folderTextView.setText(R.string.hhsoft_picture_title_name_video);
        }
        if (config.mimeType == PictureMimeType.ofAll() || config.mimeType == PictureMimeType.ofImage()) {
            previewTextView.setVisibility(View.VISIBLE);
        } else {
            previewTextView.setVisibility(View.GONE);
        }
        originalLayout.setVisibility(config.isOriginal ? View.VISIBLE : View.GONE);
        //当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
        photoRecyclerView.setHasFixedSize(true);
        photoRecyclerView.addItemDecoration(new GridSpacingItemDecoration(config.imageSpanCount, HHSoftDensityUtils.dip2px(this, 2), false));
        photoRecyclerView.setLayoutManager(new GridLayoutManager(this, config.imageSpanCount));
        // 解决调用 notifyItemChanged 闪烁问题,取消默认动画
        ((SimpleItemAnimator) photoRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mediaLoader = new LocalMediaLoader(this, config.mimeType, config.isGif, config.videoMaxSecond, config.videoMinSecond);

        photoAdapter = new HHSoftPictureGridAdapter(this, config);
        photoAdapter.setOnPhotoSelectChangedListener(new HHSoftPictureGridAdapter.OnPhotoSelectChangedListener() {
            @Override
            public void onTakePhoto() {
//                Log.i("chen", "onTakePhoto==");
                startCamera();
            }

            @Override
            public void onChange(List<LocalMedia> selectImages) {
//                Log.i("chen", "onChange==");
                changeImageNumber(selectImages);
            }

            @Override
            public void onPictureClick(LocalMedia media, int position) {
//                Log.i("chen", "onPictureClick==");
                List<LocalMedia> images = photoAdapter.getPhotos();
                startPreview(images, position);
            }
        });
        photoAdapter.bindSelectImages(selectionMediaPhotos);
        photoRecyclerView.setAdapter(photoAdapter);
        folderAdapter = new HHSoftPictureFolderAdapter(this);
        folderAdapter.bindFolderData(mediaFolders);
        folderAdapter.setOnItemClickListener((folderName, photos) -> {
            boolean camera = StringUtils.isCamera(folderName);
            camera = config.isCamera ? camera : false;
            photoAdapter.setShowCamera(camera);
            folderTextView.setText(folderName);
            photoAdapter.bindImagesData(photos);
            setFolderState();
        });
        folderListView.setAdapter(folderAdapter);
        readLocalMedia();
    }

    private void readLocalMedia() {
        mediaLoader.loadAllMedia(folders -> {
            if (folders.size() > 0) {
                mediaFolders = folders;
                LocalMediaFolder folder = folders.get(0);
                folder.setChecked(true);
                List<LocalMedia> localImg = folder.getImages();
                // 这里解决有些机型会出现拍照完，相册列表不及时刷新问题
                // 因为onActivityResult里手动添加拍照后的照片，
                // 如果查询出来的图片大于或等于当前adapter集合的图片则取更新后的，否则就取本地的
                if (localImg.size() >= mediaPhotos.size()) {
                    mediaPhotos = localImg;
                    folderAdapter.bindFolderData(folders);
                }
            }
            if (photoAdapter != null) {
                if (mediaPhotos == null) {
                    mediaPhotos = new ArrayList<>();
                }
                photoAdapter.bindImagesData(mediaPhotos);
            }
        });
    }

    private void setFolderState() {
        //选择图片文件夹
        if (folderListView.getVisibility() == View.VISIBLE) {
            //设置Layout不可用，以免出现重复点击的问题
            folderTextView.setEnabled(true);
            folderLayout.setVisibility(View.GONE);
            folderListView.setVisibility(View.GONE);
        } else {
            folderTextView.setEnabled(false);
            //显示分类的Layout
            ObjectAnimator animator = ObjectAnimator.ofFloat(folderListView, "translationY",
                    folderListView.getHeight(), 0).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    folderListView.setVisibility(View.VISIBLE);
                    folderLayout.setVisibility(View.VISIBLE);
                    folderTextView.setEnabled(true);
                }
            });
            animator.start();
        }
    }

    /**
     * open camera
     */
    public void startCamera() {
        // 防止快速点击，但是单独拍照不管
        if (!DoubleUtils.isFastDoubleClick() || config.camera) {
            switch (config.mimeType) {
                case PictureConfig.TYPE_ALL:
                    // 如果是全部类型下，单独拍照就默认图片 (因为单独拍照不会new此PopupWindow对象)
                    if (popupWindow == null) {
                        List<String> menuList = new ArrayList<>();
                        menuList.add(getString(R.string.hhsoft_picture_photograph));
                        menuList.add(getString(R.string.hhsoft_picture_video));
                        popupWindow = new HHSoftBottomMenuWindow(getPageContext(), menuList, position -> {
                            switch (position) {
                                case 0:
                                    popupWindow.dismiss();
                                    startOpenCamera();
                                    break;
                                case 1:
                                    popupWindow.dismiss();
                                    startOpenCameraVideo();
                                    break;
                                default:
                                    break;
                            }
                        });
                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    popupWindow.showAtLocation(findViewById(R.id.hhsoft_fl_content), Gravity.BOTTOM, 0, 0);
                    break;
                case PictureConfig.TYPE_IMAGE:
                    // 拍照
                    startOpenCamera();
                    break;
                case PictureConfig.TYPE_VIDEO:
                    // 录视频
                    startOpenCameraVideo();
                    break;
                case PictureConfig.TYPE_AUDIO:
                    // 录音
                    startOpenCameraAudio();
                    break;
            }
        }
    }

    /**
     * start to camera、preview、crop
     */
    public void startOpenCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Uri imageUri;
            if (SdkVersionUtils.checkedAndroid_Q()) {
                imageUri = PhotoTools.createImagePathUri(getApplicationContext());
                cameraPath = imageUri.toString();
            } else {
                int type = config.mimeType == PictureConfig.TYPE_ALL ? PictureConfig.TYPE_IMAGE
                        : config.mimeType;
                File cameraFile = PictureFileUtils.createCameraFile(getApplicationContext(),
                        type, outputCameraPath, config.suffixType);
                cameraPath = cameraFile.getAbsolutePath();
                imageUri = parUri(cameraFile);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, PictureConfig.REQUEST_CAMERA);
        }
    }

    /**
     * 生成uri
     *
     * @param cameraFile
     * @return
     */
    private Uri parUri(File cameraFile) {
        Uri imageUri;
        String authority = getPackageName() + ".provider";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(getPageContext(), authority, cameraFile);
        } else {
            imageUri = Uri.fromFile(cameraFile);
        }
        return imageUri;
    }

    /**
     * start to camera、video
     */
    public void startOpenCameraVideo() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Uri imageUri;
            if (SdkVersionUtils.checkedAndroid_Q()) {
                imageUri = PhotoTools.createImageVideoUri(getApplicationContext());
                cameraPath = imageUri.toString();
            } else {
                File cameraFile = PictureFileUtils.createCameraFile(getApplicationContext(), config.mimeType ==
                                PictureConfig.TYPE_ALL ? PictureConfig.TYPE_VIDEO : config.mimeType,
                        outputCameraPath, config.suffixType);
                cameraPath = cameraFile.getAbsolutePath();
                imageUri = parUri(cameraFile);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, config.recordVideoSecond);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, config.videoQuality);
            startActivityForResult(cameraIntent, PictureConfig.REQUEST_CAMERA);
        }
    }

    /**
     * start to camera audio
     */
    public void startOpenCameraAudio() {
        Intent cameraIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, PictureConfig.REQUEST_CAMERA);
        }
    }

    /**
     * preview image and video
     *
     * @param previewImages
     * @param position
     */
    public void startPreview(List<LocalMedia> previewImages, int position) {
        LocalMedia media = previewImages.get(position);
        String pictureType = media.getPictureType();
        Bundle bundle = new Bundle();
        List<LocalMedia> result = new ArrayList<>();
        int mediaType = PictureMimeType.isPictureType(pictureType);
        switch (mediaType) {
            case PictureConfig.TYPE_IMAGE:
                // image
                List<LocalMedia> selectedImages = photoAdapter.getSelectedPhotos();
                ImagesObservable.getInstance().saveLocalMedia(previewImages);
                bundle.putSerializable(PictureConfig.EXTRA_SELECT_LIST, (Serializable) selectedImages);
                bundle.putInt(PictureConfig.EXTRA_POSITION, position);
                startActivity(HHSoftPicturePreviewActivity.class, bundle, 0);
//                overridePendingTransition(R.anim.a5, 0);
                break;
            case PictureConfig.TYPE_VIDEO:
                // video
                if (config.selectionMode == PictureConfig.SINGLE) {
                    result.add(media);
                    onResult(result);
                } else {
                    bundle.putString("video_path", media.getPath());
                    startActivity(HHSoftPictureVideoPlayActivity.class, bundle);
                }
                break;
            case PictureConfig.TYPE_AUDIO:
                // audio
//                if (config.selectionMode == PictureConfig.SINGLE) {
//                    result.add(media);
//                    onResult(result);
//                } else {
//                    audioDialog(media.getPath());
//                }
                break;
        }
    }

    /**
     * change image selector state
     *
     * @param selectImages
     */
    public void changeImageNumber(List<LocalMedia> selectImages) {
        // 如果选择的视频没有预览功能
        String pictureType = selectImages.size() > 0
                ? selectImages.get(0).getPictureType() : "";
        sureTextView.setText(getString
                (R.string.hhsoft_picture_select_info, selectImages.size(),
                        config.selectionMode == PictureConfig.SINGLE ? 1 : config.maxSelectNum));
        if (config.mimeType == PictureMimeType.ofAudio()) {
            previewTextView.setVisibility(View.GONE);
        } else {
            boolean isVideo = PictureMimeType.isVideo(pictureType);
            boolean eqVideo = config.mimeType == PictureConfig.TYPE_VIDEO;
            previewTextView.setVisibility(isVideo || eqVideo ? View.GONE : View.VISIBLE);
        }
        boolean enable = selectImages.size() != 0;
        if (enable) {
            sureTextView.setEnabled(true);
            sureTextView.setSelected(true);
            previewTextView.setEnabled(true);
            previewTextView.setSelected(true);
        } else {
            sureTextView.setEnabled(false);
            sureTextView.setSelected(false);
            previewTextView.setEnabled(false);
            previewTextView.setSelected(false);
        }
    }

    protected void startActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void startActivity(Class clz, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            intent.putExtras(bundle);
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 拍照后处理结果
     *
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void requestCamera(Intent data) {
        List<LocalMedia> medias = new ArrayList<>();
        if (config.mimeType == PictureMimeType.ofAudio()) {
            cameraPath = getAudioPath(data);
        }
        // on take photo success
        final File file = new File(cameraPath);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        String toType;
        boolean androidQ = SdkVersionUtils.checkedAndroid_Q();
        if (androidQ) {
            String path = PictureFileUtils.getPath(getApplicationContext(), Uri.parse(cameraPath));
            toType = PictureMimeType.fileToType(new File(path));
        } else {
            toType = PictureMimeType.fileToType(file);
        }
        if (config.mimeType != PictureMimeType.ofAudio()) {
            int degree = PictureFileUtils.readPictureDegree(file.getAbsolutePath());
            rotateImage(degree, file);
        }
        // 生成新拍照片或视频对象
        LocalMedia media = new LocalMedia();
        media.setPath(cameraPath);
        boolean eqVideo = toType.startsWith(PictureConfig.VIDEO);
        int duration;
        if (eqVideo && androidQ) {
            duration = PictureMimeType.getLocalVideoDurationToAndroidQ(getApplicationContext(), cameraPath);
        } else {
            duration = eqVideo ? PictureMimeType.getLocalVideoDuration(cameraPath) : 0;
        }
        String pictureType;
        if (config.mimeType == PictureMimeType.ofAudio()) {
            pictureType = "audio/mpeg";
            duration = PictureMimeType.getLocalVideoDuration(cameraPath);
        } else {
            pictureType = eqVideo ? PictureMimeType.createVideoType(getApplicationContext(), cameraPath)
                    : PictureMimeType.createImageType(cameraPath);
        }
        media.setPictureType(pictureType);
        media.setDuration(duration);
        media.setMimeType(config.mimeType);

        // 因为加入了单独拍照功能，所有如果是单独拍照的话也默认为单选状态
        if (config.camera) {
            cameraHandleResult(medias, media, toType);
        } else {
            // 多选 返回列表并选中当前拍照的
            mediaPhotos.add(0, media);
            if (photoAdapter != null) {
                List<LocalMedia> selectedImages = photoAdapter.getSelectedPhotos();
                // 没有到最大选择量 才做默认选中刚拍好的
                if (selectedImages.size() < config.maxSelectNum) {
                    pictureType = selectedImages.size() > 0 ? selectedImages.get(0).getPictureType() : "";
                    boolean toEqual = PictureMimeType.mimeToEqual(pictureType, media.getPictureType());
                    // 类型相同或还没有选中才加进选中集合中
                    if (toEqual || selectedImages.size() == 0) {
                        if (selectedImages.size() < config.maxSelectNum) {
                            // 如果是单选，则清空已选中的并刷新列表(作单一选择)
                            if (config.selectionMode == PictureConfig.SINGLE) {
                                singleRadioMediaImage();
                            }
                            selectedImages.add(media);
                            photoAdapter.bindSelectImages(selectedImages);
                        }
                    }
                }
                photoAdapter.notifyDataSetChanged();
            }
        }
        if (photoAdapter != null) {
            // 解决部分手机拍照完Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
            // 不及时刷新问题手动添加
            manualSaveFolder(media);
//            tv_empty.setVisibility(mediaPhotos.size() > 0
//                    ? View.INVISIBLE : View.VISIBLE);
        }

        if (config.mimeType != PictureMimeType.ofAudio()) {
            int lastImageId = getLastImageId(eqVideo);
            if (lastImageId != -1) {
                removeImage(lastImageId, eqVideo);
            }
        }
    }

    /**
     * 单选图片
     */
    private void singleRadioMediaImage() {
        if (photoAdapter != null) {
            List<LocalMedia> selectImages = photoAdapter.getSelectedPhotos();
            if (selectImages != null
                    && selectImages.size() > 0) {
                selectImages.clear();
            }
        }
    }


    /**
     * 手动添加拍照后的相片到图片列表，并设为选中
     *
     * @param media
     */
    private void manualSaveFolder(LocalMedia media) {
        try {
            createNewFolder(mediaFolders);
            LocalMediaFolder folder = getImageFolder(media.getPath(), mediaFolders);
            LocalMediaFolder cameraFolder = mediaFolders.size() > 0 ? mediaFolders.get(0) : null;
            if (cameraFolder != null && folder != null) {
                // 相机胶卷
                cameraFolder.setFirstImagePath(media.getPath());
                cameraFolder.setImages(mediaPhotos);
                cameraFolder.setImageNum(cameraFolder.getImageNum() + 1);
                // 拍照相册
                int num = folder.getImageNum() + 1;
                folder.setImageNum(num);
                folder.getImages().add(0, media);
                folder.setFirstImagePath(cameraPath);
                folderAdapter.bindFolder(mediaFolders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 如果没有任何相册，先创建一个最近相册出来
     *
     * @param folders
     */
    protected void createNewFolder(List<LocalMediaFolder> folders) {
        if (folders.size() == 0) {
            // 没有相册 先创建一个最近相册出来
            LocalMediaFolder newFolder = new LocalMediaFolder();
            String folderName = config.mimeType == PictureMimeType.ofAudio() ?
                    getString(R.string.picture_all_audio) : getString(R.string.picture_camera_roll);
            newFolder.setName(folderName);
            newFolder.setPath("");
            newFolder.setFirstImagePath("");
            folders.add(newFolder);
        }
    }

    /**
     * 将图片插入到相机文件夹中
     *
     * @param path
     * @param imageFolders
     * @return
     */
    protected LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();

        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }

    /**
     * 摄像头后处理方式
     *
     * @param medias
     * @param media
     * @param toType
     */
    private void cameraHandleResult(List<LocalMedia> medias, LocalMedia media, String toType) {
        // 如果是单选 拍照后直接返回
        boolean eqImg = toType.startsWith(PictureConfig.IMAGE);
        if (config.enableCrop && eqImg) {
            // 去裁剪
//            originalPath = cameraPath;
//            startCrop(cameraPath);
        } else if (config.isCompress && eqImg && !originalImageView.isSelected()) {
            // 去压缩
            medias.add(media);
            compressImage(medias);
            if (photoAdapter != null) {
                mediaPhotos.add(0, media);
                photoAdapter.notifyDataSetChanged();
            }
        } else {
            // 不裁剪 不压缩 直接返回结果
            medias.add(media);
            onResult(medias);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                case UCrop.REQUEST_CROP:
//                    singleCropHandleResult(data);
//                    break;
//                case UCropMulti.REQUEST_MULTI_CROP:
//                    multiCropHandleResult(data);
//                    break;
                case PictureConfig.REQUEST_CAMERA:
                    requestCamera(data);
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (config.camera) {
//                closeActivity();
                finish();
            }
        }
//        else if (resultCode == UCrop.RESULT_ERROR) {
//            Throwable throwable = (Throwable) data.getSerializableExtra(UCrop.EXTRA_ERROR);
//            ToastManage.s(mContext, throwable.getMessage());
//        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hhsoft_iv_picture_top_back) {
            finish();
        }
        if (v.getId() == R.id.hhsoft_tv_picture_top_sure) {
            //选择图片完成
            List<LocalMedia> images = photoAdapter.getSelectedPhotos();
            LocalMedia image = images.size() > 0 ? images.get(0) : null;
            String pictureType = image != null ? image.getPictureType() : "";
            boolean eqImg = pictureType.startsWith(PictureConfig.IMAGE);
            if (config.enableCrop && eqImg) {
                if (config.selectionMode == PictureConfig.SINGLE) {
                    originalPath = image.getPath();
//                    startCrop(originalPath);
                } else {
                    // 是图片和选择压缩并且是多张，调用批量压缩
                    ArrayList<String> medias = new ArrayList<>();
                    for (LocalMedia media : images) {
                        medias.add(media.getPath());
                    }
//                    startCrop(medias);
                }
            } else if (config.isCompress && eqImg && !originalImageView.isSelected()) {
                // 图片才压缩，视频不管
                compressImage(images);
            } else {
                onResult(images);
            }
        }
        if (v.getId() == R.id.hhsoft_tv_picture_folder_select) {
            Log.i("chen", "onClick==" + folderListView.getVisibility());
            setFolderState();
        }
        if (v.getId() == R.id.hhsoft_ll_picture_original) {
            //原图
            boolean isSelected = originalImageView.isSelected();
            originalImageView.setSelected(!isSelected);
        }
        if (v.getId() == R.id.hhsoft_tv_picture_preview) {
            //预览
            List<LocalMedia> selectedImages = photoAdapter.getSelectedPhotos();

            List<LocalMedia> medias = new ArrayList<>();
            for (LocalMedia media : selectedImages) {
                medias.add(media);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) medias);
            bundle.putSerializable(PictureConfig.EXTRA_SELECT_LIST, (Serializable) selectedImages);
            bundle.putBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, true);
            startActivity(config.previewClazz, bundle, 0);
            overridePendingTransition(R.anim.a5, 0);
        }
    }

    /**
     * 权限请求成功
     */
    @Override
    protected void permissionsGranted() {
        super.permissionsGranted();
        initView();
    }

    /**
     * 权限请求失败
     *
     * @param perms
     */
    @Override
    protected void permissionsDenied(List<String> perms) {
        super.permissionsDenied(perms);
        HHSoftTipUtils.getInstance().showToast(getPageContext(), R.string.picture_camera);
        finish();
    }

    /**
     * EventBus 3.0 回调
     *
     * @param obj
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBus(EventEntity obj) {
        Log.i("chen", "eventBus==" + obj.what + "==" + obj.position);
        switch (obj.what) {
            case PictureConfig.UPDATE_FLAG:
                // 预览时勾选图片更新回调
                List<LocalMedia> selectImages = obj.medias;
//                anim = selectImages.size() > 0 ? true : false;
                int position = obj.position;
                photoAdapter.bindSelectImages(selectImages);
                photoAdapter.notifyItemChanged(position);
                break;
            case PictureConfig.PREVIEW_DATA_FLAG:
                List<LocalMedia> medias = obj.medias;
                if (medias.size() > 0) {
                    // 取出第1个判断是否是图片，视频和图片只能二选一，不必考虑图片和视频混合
                    String pictureType = medias.get(0).getPictureType();
                    if (config.isCompress && pictureType.startsWith(PictureConfig.IMAGE) && !originalImageView.isSelected()) {
                        compressImage(medias);
                    } else {
                        onResult(medias);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }
        ImagesObservable.getInstance().clearLocalMedia();
//        if (animation != null) {
//            animation.cancel();
//            animation = null;
//        }
//        if (mediaPlayer != null && handler != null) {
//            handler.removeCallbacks(runnable);
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }
}
