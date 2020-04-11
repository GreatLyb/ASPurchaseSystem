package com.lysoft.baseproject.view.pulltorefresh;


import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 实现listview基本滑动逻辑
 */
public abstract class HandyListView extends ListView implements
        OnScrollListener {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int mFirstVisibleItem;//第一条可见item的位置
    protected boolean mIsTop;//是否滑动到顶部
    protected boolean mIsBottom;//是否滑动到底部

    protected Point mDownPoint;//手势按下位置
    protected Point mMovePoint;//手势移动位置
    protected Point mUpPoint;//手势抬起位置

    public HandyListView(Context context) {
        super(context);
        init(context);
    }

    public HandyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HandyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        if (view.getFirstVisiblePosition() == 1) {
            mIsTop = true;
        } else if (view.getLastVisiblePosition() == view.getCount() - 1) {
            mIsBottom = true;
        } else {
            mIsTop = false;
            mIsBottom = false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = 0;
        int y = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = (int) ev.getX();
                y = (int) ev.getY();
                mDownPoint = new Point(x, y);
                onDown(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                x = (int) ev.getX();
                y = (int) ev.getY();
                mMovePoint = new Point(x, y);
                onMove(ev);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                x = (int) ev.getX();
                y = (int) ev.getY();
                mUpPoint = new Point(x, y);
                onUp(ev);
                break;
        }
        return super.onTouchEvent(ev);
    }

    public abstract void onDown(MotionEvent ev);

    public abstract void onMove(MotionEvent ev);

    public abstract void onUp(MotionEvent ev);

    public void setFirstVisibleItem(int firstVisibleItem) {
        this.mFirstVisibleItem = firstVisibleItem;
    }
}
