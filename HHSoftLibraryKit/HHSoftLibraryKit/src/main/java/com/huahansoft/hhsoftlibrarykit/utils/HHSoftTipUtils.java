package com.huahansoft.hhsoftlibrarykit.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.view.HHSoftLoadingCircleView;


/**
 * @类说明 用于显示Toast提示和ProgressDialog提示的工具类
 * @作者 hhsoft
 * @创建日期 2019/8/22 16:44
 */
public class HHSoftTipUtils {
    //定义了唯一的实例对象;//增加volatile关键字，确保实例化instance时，编译成汇编指令的执行顺序
    private volatile static HHSoftTipUtils mInstance;
    //显示的Toast
    private Toast mToast;
    //显示Toast的内容
    private TextView mToastContentTextView;
    //显示的ProgressDialog
    private Dialog mProgressDialog;
    //显示Dialog的内容
    private TextView mDialogContentTextView;
    private HHSoftLoadingCircleView mDialogLoadingImageView;

    //私有化构造函数
    private HHSoftTipUtils() {
    }

    /**
     * 获取HHTipUtils的实例
     *
     * @return
     */
    public static HHSoftTipUtils getInstance() {
        if (mInstance == null) {
            synchronized (HHSoftTipUtils.class) {
                /*当第一次调用getInstance方法时，即instance为空时，同步操作，保证多线程实例唯一 ,
				当以后调用getInstance方法时，即instance不为空时，不进入同步代码块，减少了不必要的同步开销*/
                if (mInstance == null) {
                    mInstance = new HHSoftTipUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 显示Toast通知
     *
     * @param context
     * @param msg     显示的内容
     */
    public void showToast(Context context, String msg) {
        Log.i("chen","showToast=="+msg);
        if (mToast == null) {
            Context applicationContext = context.getApplicationContext();
            mToast = new Toast(applicationContext);
            mToastContentTextView = new TextView(context);
            mToastContentTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mToastContentTextView.setTextSize(16);
            mToastContentTextView.setTextColor(Color.WHITE);
            mToastContentTextView.setBackgroundResource(R.drawable.hhsoft_shape_toast_custom_bg);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(mToastContentTextView);
        }
        mToastContentTextView.setText(msg);
        mToast.show();
    }

    /**
     * 显示Toast通知
     *
     * @param context
     * @param resID   资源的ID
     */
    public void showToast(Context context, int resID) {
        showToast(context, context.getString(resID));
    }

    /**
     * 显示提示的Dialog
     *
     * @param context
     * @param msg     显示的内容
     */
    public void showProgressDialog(Context context, String msg, boolean isCancel) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (mProgressDialog == null || mProgressDialog.getContext() != context) {
            mProgressDialog = new Dialog(context, R.style.HuaHanSoft_Dialog_Base);
            LinearLayout view = new LinearLayout(context);
            view.setOrientation(LinearLayout.HORIZONTAL);
            view.setGravity(Gravity.CENTER_VERTICAL);
            view.setBackgroundResource(R.drawable.hhsoft_shape_dialog_tip_bg);
            mDialogLoadingImageView = new HHSoftLoadingCircleView(context);
            mDialogLoadingImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mDialogLoadingImageView.setBackgroundResource(R.drawable.hhsoft_loading_progress_bar);
            view.addView(mDialogLoadingImageView);
            mDialogContentTextView = new TextView(context);
            mDialogContentTextView.setTextSize(16);
            mDialogContentTextView.setTextColor(ContextCompat.getColor(context, R.color.defaultGrayText));
            mDialogContentTextView.setText(context.getString(R.string.huahansoft_load_state_loading));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(HHSoftDensityUtils.dip2px(context, 10), 0, 0, 0);
            view.addView(mDialogContentTextView, params);
            mProgressDialog.setContentView(view);

        }
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDialogLoadingImageView != null) {
                    mDialogLoadingImageView.stopLoaddingAnim();
                }
            }
        });
        mDialogContentTextView.setText(msg);
        mDialogLoadingImageView.startLoadingAnim();
        mProgressDialog.setCanceledOnTouchOutside(isCancel);
        mProgressDialog.show();


    }

    /**
     * 显示的提示的Dialog
     *
     * @param context
     * @param resID   显示的内容的资源的ID
     */
    public void showProgressDialog(Context context, int resID) {
        showProgressDialog(context, context.getString(resID), true);
    }

    /**
     * 显示的提示的Dialog
     *
     * @param context
     * @param resID
     * @param isCancel dialog 是否可以取消
     */
    public void showProgressDialog(Context context, int resID, boolean isCancel) {
        showProgressDialog(context, context.getString(resID), isCancel);
    }

    /**
     * 显示的提示的Dialog
     *
     * @param context
     * @param msg
     */
    public void showProgressDialog(Context context, String msg) {
        showProgressDialog(context, msg, true);
    }

    /**
     * 取消显示提示的Dialog
     */
    public void dismissProgressDialog() {
        if (mDialogLoadingImageView != null) {
            mDialogLoadingImageView.stopLoaddingAnim();
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
