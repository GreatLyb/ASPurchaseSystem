package com.huahansoft.hhsoftlibrarykit.utils.dialog;

import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.huahansoft.hhsoftlibrarykit.R;

/**
 * 类描述：
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/8
 */

public class HHSoftDialogInit {
    public static int getTheme(HHSoftDialog.Builder builder) {
        return R.style.HuaHanSoft_Dialog_Base;
    }

    public static void init(HHSoftDialog dialog) {
        dialog.iconImageView = dialog.view.findViewById(R.id.huahansoft_iv_dialog_icon);
        dialog.iconImageView = dialog.view.findViewById(R.id.huahansoft_iv_dialog_icon);
        dialog.titleTextView = dialog.view.findViewById(R.id.huahansoft_tv_dialog_title);
        dialog.contentTextView = dialog.view.findViewById(R.id.huahansoft_tv_dialog_content);
        dialog.actionNeutralTextView = dialog.view.findViewById(R.id.huahansoft_tv_dialog_action_neutral);
        dialog.actionNegativeTextView = dialog.view.findViewById(R.id.huahansoft_tv_dialog_action_negative);
        dialog.actionPositiveTextView = dialog.view.findViewById(R.id.huahansoft_tv_dialog_action_positive);
        final HHSoftDialog.Builder builder = dialog.builder;
        if (dialog.iconImageView != null) {
            if (builder.icon != null) {
                dialog.iconImageView.setVisibility(View.VISIBLE);
                dialog.iconImageView.setImageDrawable(builder.icon);
            } else {
                dialog.iconImageView.setVisibility(View.GONE);
            }
        }
        if (dialog.titleTextView != null) {
            if (builder.title != null) {
                dialog.titleTextView.setVisibility(View.VISIBLE);
                dialog.titleTextView.setText(builder.title);
            } else {
                dialog.titleTextView.setVisibility(View.GONE);
            }
        }
        if (dialog.contentTextView != null) {
            if (builder.content != null) {
                dialog.contentTextView.setVisibility(View.VISIBLE);
                dialog.contentTextView.setText(builder.content);
            } else {
                dialog.contentTextView.setVisibility(View.GONE);
            }
        }
        if (dialog.actionNeutralTextView != null) {
            if (builder.neutralText != null) {
                dialog.actionNeutralTextView.setVisibility(View.VISIBLE);
                dialog.actionNeutralTextView.setText(builder.neutralText);
                dialog.actionNeutralTextView.setTextColor(builder.neutralColor);
                dialog.actionNeutralTextView.setTag(HHSoftDialogActionEnum.NEUTRAL);
                dialog.actionNeutralTextView.setOnClickListener(dialog);
            } else {
                dialog.actionNeutralTextView.setVisibility(View.INVISIBLE);
            }
        }
        if (dialog.actionNegativeTextView != null) {
            if (builder.negativeText != null) {
                dialog.actionNegativeTextView.setVisibility(View.VISIBLE);
                dialog.actionNegativeTextView.setText(builder.negativeText);
                dialog.actionNegativeTextView.setTextColor(builder.negativeColor);
                dialog.actionNegativeTextView.setTag(HHSoftDialogActionEnum.NEGATIVE);
                dialog.actionNegativeTextView.setOnClickListener(dialog);
            } else {
                dialog.actionNegativeTextView.setVisibility(View.GONE);
            }
        }
        if (dialog.actionPositiveTextView != null) {
            if (builder.positiveText != null) {
                dialog.actionPositiveTextView.setVisibility(View.VISIBLE);
                dialog.actionPositiveTextView.setText(builder.positiveText);
                dialog.actionPositiveTextView.setTextColor(builder.positiveColor);
                dialog.actionPositiveTextView.setTag(HHSoftDialogActionEnum.POSITIVE);
                dialog.actionPositiveTextView.setOnClickListener(dialog);
            } else {
                dialog.actionPositiveTextView.setVisibility(View.GONE);
            }
        }
        /*设置Dialog基本属性*/
        dialog.setCancelable(builder.cancelable);
        dialog.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        if (builder.dismissListener != null) {
            dialog.setOnDismissListener(builder.dismissListener);
        }
        if (builder.cancelListener != null) {
            dialog.setOnCancelListener(builder.cancelListener);
        }
        // Min height and max width calculations
        WindowManager wm = dialog.getWindow().getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int windowWidth = size.x;
        final int windowHeight = size.y;

        final int windowVerticalPadding =
                builder.context.getResources().getDimensionPixelSize(R.dimen.huahansoft_dialog_vertical_margin);
        final int windowHorizontalPadding =
                builder.context.getResources().getDimensionPixelSize(R.dimen.huahansoft_dialog_horizontal_margin);
        final int maxWidth =
                builder.context.getResources().getDimensionPixelSize(R.dimen.huahansoft_dialog_max_width);
        final int calculatedWidth = windowWidth - (windowHorizontalPadding * 2);

        WindowManager.LayoutParams attributes = new WindowManager.LayoutParams();
        attributes.copyFrom(dialog.getWindow().getAttributes());
        attributes.width = Math.min(maxWidth, calculatedWidth);
        dialog.getWindow().setAttributes(attributes);
    }
}
