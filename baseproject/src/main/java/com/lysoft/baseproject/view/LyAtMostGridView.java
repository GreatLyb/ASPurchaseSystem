package com.lysoft.baseproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 重写GridView，实现GridView的高度为GridView的行数高度*GridView的行数
 *
 * @author yuan
 */
public class LyAtMostGridView extends GridView {

    public LyAtMostGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LyAtMostGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LyAtMostGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
