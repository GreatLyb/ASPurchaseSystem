package com.lyb.qrcodelibrary.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.Result;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.linchaolong.android.imagepicker.ImagePicker;
import com.lyb.qrcodelibrary.R;
import com.lyb.qrcodelibrary.camera.CameraManager;
import com.lyb.qrcodelibrary.utils.BeepManager;
import com.lyb.qrcodelibrary.utils.CaptureActivityHandler;
import com.lyb.qrcodelibrary.utils.DecodeThread;
import com.lyb.qrcodelibrary.utils.InactivityTimer;
import com.lyb.qrcodelibrary.utils.QRCodeUtils;
import com.lyb.qrcodelibrary.view.ScanView;
import com.lysoft.baseproject.clipview.ClipImageActivity;
import com.lysoft.baseproject.glide.GlideLoade;
import com.lysoft.baseproject.utils.FileUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 二维码扫描
 *
 * @author wjh
 */
public abstract class BaseCaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static String TAG = "CaptureActivity";
    private static final int REQEST_SELECT_IMAGES_CODE = 1000;
    private static final int REQEST_CROP_IMAGES_CODE = 1001;

    /*控件*/
    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private ToggleButton toggleButton;
    private Button albumButton;
    /*功能实现*/
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    /*逻辑判断*/
    private Rect mCropRect = null;
    private boolean isFlashlightOpen;
    private boolean isHasSurface = false;
    private ScanView scanView;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置屏幕常量
        View view = View.inflate(this, R.layout.activity_capture, null);
        setContentView(view);
        scanPreview = findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        toggleButton = findViewById(R.id.capture_flashlight);
        albumButton = findViewById(R.id.capture_scan_photo);
        scanView = findViewById(R.id.scan_view);
        scanView.setBordercolor(R.color.scan_color);

        initValues();
        initListeners();
    }


    public void initValues() {
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        /*扫描动画效果*/
    }

    public void initListeners() {
        toggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashlightOpen) {
                    cameraManager.setTorch(false); // 关闭闪光灯
                    isFlashlightOpen = false;
                } else {
                    cameraManager.setTorch(true); // 打开闪光灯
                    isFlashlightOpen = true;
                }
            }
        });
        albumButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker = new ImagePicker();
                imagePicker.setTitle("选择二维码");
                imagePicker.setCropImage(false);
                imagePicker.startGallery(BaseCaptureActivity.this, new ImagePicker.Callback() {
                    @Override
                    public void onPickImage(Uri imageUri) {
                        identificationQRCode(FileUtils.getRealFilePathFromUri(BaseCaptureActivity.this, imageUri));
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        if (isHasSurface) {
            initCamera(scanPreview.getHolder());
        } else {
            scanPreview.getHolder().addCallback(this);
        }
        inactivityTimer.onResume();
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQEST_SELECT_IMAGES_CODE:
//                    List<String> imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
//                    if (imagePaths != null && imagePaths.size() > 0) {
//                        identificationQRCode(imagePaths.get(0));
//                    }
                    break;
                case 200:
                    imagePicker.onActivityResult(this, requestCode, resultCode, data);
                    break;
                case REQEST_CROP_IMAGES_CODE:
                    break;
                default:
                    break;
            }
        }
    }

    /*初始化相机*/
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }
            initCrop();
        } catch (IOException | RuntimeException ioe) {
            TipDialog.show(this, "相机初始化失败请重试", TipDialog.TYPE.ERROR);

        } // Barcode Scanner has seen crashes in the wild of this variety:
        // java.?lang.?RuntimeException: Fail to connect to camera service

    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanView.getWidth();
        int cropHeight = scanView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    /*获取状态栏高度*/
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
        cameraManager.stopPreview();
    }

    public Handler getActivityHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 识别二维码
     *
     * @param imagePath
     */
    private void identificationQRCode(final String imagePath) {
        WaitDialog.show(this, "识别中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                final String qrCode = QRCodeUtils.identificationQRCodeByPath(BaseCaptureActivity.this, imagePath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WaitDialog.dismiss();
                        onHandleDecode(qrCode);
                    }
                });
            }
        }).start();
    }

    //    /**
    //     * 二维码处理结果
    //     * @param rawResult
    //     * @param bundle
    //     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        onHandleDecode(rawResult.getText().toString().trim());
    }

    protected abstract void onHandleDecode(String rawResult);

}