package com.lysoft.baseproject.clipview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lysoft.baseproject.R;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ClipImageActivity";

    public static final String TYPE = "type";
    public static final int REQ_CLIP_AVATAR = 50;

    private ClipViewLayout mClipViewLayout;
    private ImageView mBack;
    private TextView mBtnCancel;
    private TextView mBtnOk;

    private int mType;

    public static void goToClipActivity(Fragment fragment, Uri uri) {
        Intent clipIntent = getClipIntent(fragment, uri);
        fragment.startActivityForResult(clipIntent, REQ_CLIP_AVATAR);
    }



    @NonNull
    public static Intent getClipIntent(Fragment fragment, Uri uri) {
        Intent intent = new Intent();
        intent.setClass(fragment.getContext(), ClipImageActivity.class);
        intent.putExtra(TYPE, ClipView.TYPE_ROUND);
        intent.setData(uri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        mType = getIntent().getIntExtra(TYPE, ClipView.TYPE_ROUND);
        Log.i(TAG, "onCreate: mType =" + mType);
        initView();
        mClipViewLayout.setClipType(mType);
    }

    /**
     * 初始化组件
     */
    public void initView() {
        mClipViewLayout = findViewById(R.id.clipViewLayout);
        mBack = findViewById(R.id.iv_back);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnOk = findViewById(R.id.bt_ok);
        //设置点击事件监听器
        mBack.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mClipViewLayout.setVisibility(View.VISIBLE);
        mClipViewLayout.setClipType(mType);

        //设置图片资源
        mClipViewLayout.setImageSrc(getIntent().getData());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.btn_cancel) {
            finish();
        } else if (id == R.id.bt_ok) {
            generateUriAndReturn();
        }
    }


    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap = mClipViewLayout.clip();

        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            Log.i("Lyb","RESULT_OK====");
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
