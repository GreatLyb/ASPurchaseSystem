package com.huahansoft.hhsoftlibrarykit.ui;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftCallBack;
import com.huahansoft.hhsoftlibrarykit.proxy.HHSoftLoadStatus;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftTipUtils;
import com.huahansoft.hhsoftlibrarykit.view.pulltorefresh.HHSoftRefreshListView;

import java.util.ArrayList;
import java.util.List;

public abstract class HHSoftUIBaseListFragment<T> extends HHSoftUIBaseLoadFragment {
    private HHSoftRefreshListView mListView;
    private List<T> mList;
    private List<T> mTempList;
    private BaseAdapter mAdapter;
    private boolean mIsLoading = false;
    private boolean mIsLoadMore = true;
    private boolean mIsRefresh = true;
    //当前获取的是第几页的数据，当前可见的数据的数量，当前页获取的数据的条数
    private int mPageIndex = 1, mVisibleCount = 0, mPageCount = 0;

    @Override
    protected void onCreate() {
        mListView = new HHSoftRefreshListView(getPageContext());
        mListView.setDividerHeight(0);
        mListView.setFadingEdgeLength(0);
        mListView.setVerticalFadingEdgeEnabled(false);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setSelector(R.color.defaultTransparent);
        mListView.setBackgroundColor(ContextCompat.getColor(getPageContext(), R.color.defaultWhite));
        containerView().addView(mListView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mIsLoadMore = isLoadMore();
        mIsRefresh = isRefresh();
        if (mIsRefresh) {
            mListView.setOnRefreshListener(() -> {
                mPageIndex = 1;
                onPageLoad();
            });
        }
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!mIsLoading && mIsLoadMore && mPageCount == getPageSize() && mVisibleCount == mAdapter.getCount() && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    ++mPageIndex;
                    onPageLoad();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mListView.setFirstVisibleItem(firstVisibleItem);
                mVisibleCount = firstVisibleItem + visibleItemCount - mListView.getFooterViewsCount() - mListView.getHeaderViewsCount();
            }
        });
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < mListView.getHeaderViewsCount()) {
                mListView.onRefreshComplete();
                return;
            }
            if (position > (mListView.getHeaderViewsCount() + mList.size() - 1)) {
                return;
            }
            itemClickListener(position - mListView.getHeaderViewsCount());
        });
    }

    @Override
    protected void onPageLoad() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        getListData(tempList -> {
            mTempList = (List<T>) tempList;
            mPageCount = mTempList == null ? 0 : mTempList.size();
            mIsLoading = false;
            if (mListView != null) {
                mListView.onRefreshComplete();
            }
            if (mListView != null && mListView.getFooterViewsCount() > 0 && getPageSize() != mPageCount) {
                mListView.removeFooterView();
            }
            if (mTempList == null) {
                if (1 == mPageIndex) {
                    loadViewManager().changeLoadState(HHSoftLoadStatus.FAILED);
                } else {
                    HHSoftTipUtils.getInstance().showToast(getPageContext(), R.string.huahansoft_net_error);
                }
            } else if (mTempList.size() == 0) {
                if (mPageIndex == 1) {
                    if (mList == null) {
                        mList = new ArrayList<T>();
                    } else {
                        mList.clear();
                    }
                    loadViewManager().changeLoadState(HHSoftLoadStatus.NODATA);
                } else {
                    HHSoftTipUtils.getInstance().showToast(getPageContext(), R.string.huahansoft_load_state_no_more_data);
                }
            } else {
                loadViewManager().changeLoadState(HHSoftLoadStatus.SUCCESS);
                if (mPageIndex == 1) {
                    if (mList == null) {
                        mList = new ArrayList<T>();
                    } else {
                        mList.clear();
                    }
                    mList.addAll(mTempList);
                    mAdapter = instanceAdapter(mList);
                    if (mIsLoadMore && mPageCount == getPageSize() && mListView.getFooterViewsCount() == 0) {
                        mListView.addFooterView();
                    }
                    mListView.setAdapter(mAdapter);
                } else {
                    mList.addAll(mTempList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 获取页面集合
     *
     * @param callBack
     */
    protected abstract void getListData(HHSoftCallBack callBack);

    /**
     * 设置适配器
     *
     * @param list
     * @return
     */
    protected abstract BaseAdapter instanceAdapter(List<T> list);

    /**
     * Item点击事件，已排除HeaderView和FooterView对position的影响
     *
     * @param position
     */
    protected abstract void itemClickListener(int position);

    /**
     * 获取页面加载数量
     *
     * @return
     */
    protected abstract int getPageSize();

    /**
     * 设置页面是否可以加载更多
     *
     * @return
     */
    protected boolean isLoadMore() {
        return true;
    }

    /**
     * 设置页面是否允许下拉刷新
     *
     * @return
     */
    protected boolean isRefresh() {
        return true;
    }

    /**
     * 获取页面列表数据
     *
     * @return
     */
    protected List<T> getPageListData() {
        return mList;
    }

    /**
     * 获取ListView对象
     *
     * @return
     */
    protected HHSoftRefreshListView getPageListView() {
        return mListView;
    }

    /**
     * 获取页面当前页码
     *
     * @return
     */
    protected int getPageIndex() {
        return mPageIndex;
    }

    /**
     * 设置页面页码
     *
     * @param pageIndex
     */
    protected void setPageIndex(int pageIndex) {
        this.mPageIndex = pageIndex;
    }
}
