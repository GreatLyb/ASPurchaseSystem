package com.lysoft.baseproject.view.pulltorefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lysoft.baseproject.R;
import com.lysoft.baseproject.imp.HHSoftRefreshFooterInterface;
import com.lysoft.baseproject.imp.HHSoftRefreshHeaderInterface;


/**
 * 类描述：下拉刷新列表控件
 * 类传参：
 * 创建人：xiao
 * 创建时间：2018/3/13
 */
@SuppressLint({"SimpleDateFormat", "ClickableViewAccessibility"})
public class HHSoftRefreshListView extends HandyListView {
    private final static int RATIO = 3;//下拉难易系数，数值越大，难度越大

    private View mHeaderView;//listview的HeaderView
    private View mFooterView;//listview的FooterView

    //下拉刷新的集中状态
    private enum RefreshStatus {
        PULL,
        RELEASE,
        REFRESHING,
        DONE
    }

    private RefreshStatus mState;//当前刷新状态
    private int mHeaderHeight;//初始状态mHeader的高度
    private int mHeaderRefreshingHeight;//mHeader的刷新时的高度

    /*标记标签*/
    private boolean mIsRecored;//是否开始记录下拉刷新操作
    private boolean mIsRefreshable;//是否允许刷新
    private int mFlagYDown, mFlagYMove,
            mFlagY;//手指按下、移动、及总体的Y轴偏移量
    /*接口实现*/
    private OnRefreshListener mOnRefreshListener;//下拉刷新调用

    public HHSoftRefreshListView(Context context) {
        super(context);
        init();
    }

    public HHSoftRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HHSoftRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setRefreshHeaderView(new HHSoftRefreshHeaderView(getContext()));
        mState = RefreshStatus.PULL;
        mIsRefreshable = false;
    }

    @Override
    public void onDown(MotionEvent ev) {
        if (mIsRefreshable) {
            if (mFirstVisibleItem == 0 && !mIsRecored) {
                mIsRecored = true;
                mFlagYDown = mDownPoint.y;
            }
        }
    }

    @Override
    public void onMove(MotionEvent ev) {
        if (mIsRefreshable) {
            if (!mIsRecored && mFirstVisibleItem == 0) {
                mIsRecored = true;
                mFlagYDown = mMovePoint.y;
            }
            if (RefreshStatus.REFRESHING != mState && mIsRecored) {
                //可以刷新，但刷新状态不为REFRESHING
                mFlagYMove = mMovePoint.y;
                mFlagY = (mFlagYMove - mFlagYDown) / RATIO;
                if (RefreshStatus.RELEASE == mState) {
                    //当前状态是释放刷新，满足条件，状态改变为下拉刷新和刷新完成
                    if (mFlagY < mHeaderHeight && (mFlagYMove - mFlagYDown) > 0) {
                        mState = RefreshStatus.PULL;
                        changeHeaderViewByState();
                    } else if ((mFlagYMove - mFlagYDown) <= 0) {
                        mState = RefreshStatus.DONE;
                        changeHeaderViewByState();
                    }
                }
                if (RefreshStatus.PULL == mState) {
                    if (mFlagY > mHeaderHeight) {
                        mState = RefreshStatus.RELEASE;
                        changeHeaderViewByState();
                    } else if ((mFlagYMove - mFlagYDown) <= 0) {
                        mState = RefreshStatus.DONE;
                        changeHeaderViewByState();
                    }
                }
                if (RefreshStatus.DONE == mState) {
                    if (mFlagYMove - mFlagYDown > 0) {
                        mState = RefreshStatus.PULL;
                        changeHeaderViewByState();
                    }
                }
                if (mFlagY > 0) {
                    if (mHeaderView instanceof HHSoftRefreshHeaderInterface) {
                        ((HHSoftRefreshHeaderInterface) mHeaderView).changeViewHeight(mFlagY);
                    }
                }
            }
        }
    }

    @Override
    public void onUp(MotionEvent ev) {
        if (mState != RefreshStatus.REFRESHING) {
            if (mState == RefreshStatus.PULL) {
                mState = RefreshStatus.DONE;
                changeHeaderViewByState();
            }
            if (mState == RefreshStatus.RELEASE) {
                mState = RefreshStatus.REFRESHING;
                changeHeaderViewByState();
                onRefresh();
            }
        }
        mIsRecored = false;
    }

    /*通过刷新状态改变布局*/
    private void changeHeaderViewByState() {
        HHSoftRefreshHeaderInterface headerInterface = (mHeaderView instanceof HHSoftRefreshHeaderInterface) ? (HHSoftRefreshHeaderInterface) mHeaderView : null;
        if (headerInterface == null) {
            return;
        }
        switch (mState) {
            case RELEASE://释放刷新
                headerInterface.releaseToRefresh();
                break;
            case PULL://下拉刷新
                headerInterface.pullToRefresh();
                break;
            case REFRESHING://正在刷新
                headerInterface.changeViewHeight(mHeaderRefreshingHeight);
                headerInterface.refreshing();
                break;
            case DONE://刷新成功
                headerInterface.changeViewHeight(0);
                headerInterface.complete();
                break;
        }
    }

    /*刷新完成*/
    public void onRefreshComplete() {
        mState = RefreshStatus.DONE;
        changeHeaderViewByState();
    }

    /*刷新数据*/
    private void onRefresh() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }

    /*手动刷新*/
    public void onManualRefresh() {
        if (mIsRefreshable) {
            mState = RefreshStatus.REFRESHING;
            changeHeaderViewByState();
            onRefresh();
        }
    }

    /*下拉刷新监听*/
    public void setOnRefreshListener(OnRefreshListener l) {
        mOnRefreshListener = l;
        if (mOnRefreshListener == null) {
            mIsRefreshable = false;
        } else {
            mIsRefreshable = true;
        }
    }

    /*下拉刷新接口*/
    public interface OnRefreshListener {
        public void onRefresh();
    }

    /*获取头部布局*/
    public View getDefaultRefreshHeaderView() {
        return mHeaderView;
    }

    /**
     * 动态设置listview的头部布局，需要继承HuaHanSoftRefreshHeaderInterface
     *
     * @param headerView
     */
    public void setRefreshHeaderView(View headerView) {
        if (headerView != null && headerView != mHeaderView) {
            removeHeaderView(mHeaderView);
        }
        mHeaderView = headerView;
        measureView(mHeaderView);
        addHeaderView(mHeaderView);
        mHeaderHeight = mHeaderView.getMeasuredHeight();
        mHeaderRefreshingHeight = (int) getContext().getResources().getDimension(R.dimen.top_height);
        if (mHeaderView instanceof HHSoftRefreshHeaderInterface) {
            ((HHSoftRefreshHeaderInterface) mHeaderView).changeViewHeight(0);
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /*移除FooterView*/
    public void removeFooterView() {
        if (mFooterView != null) {
            if (mFooterView instanceof HHSoftRefreshFooterInterface) {
                ((HHSoftRefreshFooterInterface) mFooterView).loadComplete();
            }
            this.removeFooterView(mFooterView);
        }
    }

    /*设置默认FooterView，只初始化view，不添加到listview*/
    public void setRefreshFooterView(View footerView) {
        if (footerView != null && footerView != mFooterView) {
            this.removeFooterView(mFooterView);
        }
        mFooterView = footerView;
    }

    /*添加FooterView，外部不调用setRefreshFooterView()时，加载默认view*/
    public void addFooterView() {
        if (mFooterView == null) {
            setRefreshFooterView(new HHSoftRefreshFooterView(getContext()));
        }
        this.addFooterView(mFooterView);
        if (mFooterView instanceof HHSoftRefreshFooterInterface) {
            ((HHSoftRefreshFooterInterface) mFooterView).loading();
        }
    }
}
