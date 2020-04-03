package com.huahansoft.hhsoftlibrarykit.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huahansoft.hhsoftlibrarykit.R;

/**
 * 类描述：
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/8
 */

public class HHSoftDialog extends Dialog implements View.OnClickListener{
    protected final Builder builder;
    protected View view;
    protected ImageView iconImageView;
    protected TextView titleTextView;
    protected TextView contentTextView;
    protected TextView actionNeutralTextView;
    protected TextView actionNegativeTextView;
    protected TextView actionPositiveTextView;

    protected HHSoftDialog(Builder builder) {
        super(builder.context, HHSoftDialogInit.getTheme(builder));
        this.builder = builder;
        view=View.inflate(builder.context, R.layout.hhsoft_dialog_view,null);
        setContentView(view);
        HHSoftDialogInit.init(this);
        //在这一点之后，不要在生成器中保留上下文引用
        builder.context = null;
    }

    @Override
    public void onClick(View v) {
        HHSoftDialogActionEnum tag = (HHSoftDialogActionEnum) v.getTag();
        switch (tag) {
            case POSITIVE:
                if (builder.onPositiveCallback != null) {
                    builder.onPositiveCallback.onClick(this, tag);
                }
                if (builder.autoDismiss) {
                    dismiss();
                }
                break;
            case NEGATIVE:
                if (builder.onNegativeCallback != null) {
                    builder.onNegativeCallback.onClick(this, tag);
                }
                if (builder.autoDismiss) {
                    cancel();
                }
                break;
            case NEUTRAL:
                if (builder.onNeutralCallback != null) {
                    builder.onNeutralCallback.onClick(this, tag);
                }
                if (builder.autoDismiss) {
                    dismiss();
                }
                break;
        }
        if (builder.onAnyCallback != null) {
            builder.onAnyCallback.onClick(this, tag);
        }
    }

    public static class Builder{
        protected Context context;
        /*title属性*/
        protected CharSequence title;
        protected int titleColor=-1;
        protected Drawable icon;
        protected CharSequence content;
        protected int contentColor=-1;
        protected CharSequence neutralText;
        protected CharSequence negativeText;
        protected CharSequence positiveText;
        protected int neutralColor=-1;
        protected int negativeColor=-1;
        protected int positiveColor=-1;
        protected SingleButtonCallback onPositiveCallback;
        protected SingleButtonCallback onNegativeCallback;
        protected SingleButtonCallback onNeutralCallback;
        protected SingleButtonCallback onAnyCallback;

        protected boolean autoDismiss = true;
        //dialog弹出后会点击屏幕或物理返回键，dialog不消失
        protected boolean cancelable = true;
        //dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
        protected boolean canceledOnTouchOutside = true;
        /*Dialog取消，如果只是取消dialog，那么用以下哪个都一样的*/
        //回调setOnCancelListener的监听事件—–>执行dismiss。
        protected OnCancelListener cancelListener;
        //取消dialog—–>回调setOnDismissListener的监听事件。
        protected OnDismissListener dismissListener;

        public Builder(Context context){
            this.context=context;
            this.neutralColor= ContextCompat.getColor(context,R.color.defaultDialogActionTextColorNeutral);
            this.negativeColor= ContextCompat.getColor(context,R.color.defaultDialogActionTextColorNegative);
            this.positiveColor= ContextCompat.getColor(context,R.color.defaultDialogActionTextColorPositive);
        }
        public HHSoftDialog build() {
            return new HHSoftDialog(this);
        }
        public HHSoftDialog show() {
            HHSoftDialog dialog = build();
            dialog.show();
            return dialog;
        }
        public Builder icon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder iconRes(int iconRes) {
            this.icon=ResourcesCompat.getDrawable(context.getResources(),iconRes,null);
            return this;
        }
        public Builder title(CharSequence title) {
            this.title = title;
            return this;
        }
        public Builder title(int titleRes) {
            title(this.context.getText(titleRes));
            return this;
        }
        public Builder content(CharSequence content) {
            this.content = content;
            return this;
        }
        public Builder content(int contentRes) {
            content(this.context.getText(contentRes));
            return this;
        }
        public Builder neutralText(CharSequence neutralText) {
            this.neutralText = neutralText;
            return this;
        }
        public Builder neutralText(int neutralTextRes) {
            neutralText(this.context.getText(neutralTextRes));
            return this;
        }
        public Builder negativeText(CharSequence negativeText) {
            this.negativeText = negativeText;
            return this;
        }
        public Builder negativeText(int negativeTextRes) {
            negativeText(this.context.getText(negativeTextRes));
            return this;
        }
        public Builder positiveText(CharSequence positiveText) {
            this.positiveText = positiveText;
            return this;
        }
        public Builder positiveText(int positiveTextRes) {
            positiveText(this.context.getText(positiveTextRes));
            return this;
        }
        public Builder titleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }
        public Builder titleColorRes(int titleColorRes) {
            titleColor(ContextCompat.getColor(context,titleColorRes));
            return this;
        }
        public Builder contentColor(int contentColor) {
            this.contentColor = contentColor;
            return this;
        }
        public Builder contentColorRes(int contentColorRes) {
            contentColor(ContextCompat.getColor(context,contentColorRes));
            return this;
        }
        public Builder neutralColor(int neutralColor) {
            this.neutralColor = neutralColor;
            return this;
        }
        public Builder neutralColorRes(int neutralColorRes) {
            neutralColor(ContextCompat.getColor(context,neutralColorRes));
            return this;
        }
        public Builder negativeColor(int negativeColor) {
            this.negativeColor = negativeColor;
            return this;
        }
        public Builder negativeColorRes(int negativeColorRes) {
            negativeColor(ContextCompat.getColor(context,negativeColorRes));
            return this;
        }
        public Builder positiveColor(int positiveColor) {
            this.positiveColor = positiveColor;
            return this;
        }
        public Builder positiveColorRes(int positiveColorRes) {
            positiveColor(ContextCompat.getColor(context,positiveColorRes));
            return this;
        }
        public Builder onPositive(SingleButtonCallback callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegative(SingleButtonCallback callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onNeutral(SingleButtonCallback callback) {
            this.onNeutralCallback = callback;
            return this;
        }

        public Builder onAny(SingleButtonCallback callback) {
            this.onAnyCallback = callback;
            return this;
        }
        public Builder autoDismiss(boolean dismiss) {
            this.autoDismiss = dismiss;
            return this;
        }
        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            this.canceledOnTouchOutside=cancelable;
            return this;
        }
        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }
        public Builder dismissListener(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }
        public Builder cancelListener(OnCancelListener listener) {
            this.cancelListener = listener;
            return this;
        }
    }
    public interface SingleButtonCallback {
        void onClick(HHSoftDialog dialog, HHSoftDialogActionEnum which);
    }
}
