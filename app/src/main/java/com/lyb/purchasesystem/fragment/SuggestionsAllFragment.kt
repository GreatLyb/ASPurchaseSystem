package com.lyb.purchasesystem.fragment

import android.view.View
import android.widget.BaseAdapter
import com.lyb.purchasesystem.adapter.SuggestionsListAdapter
import com.lyb.purchasesystem.bean.SuggertionBean
import com.lyb.purchasesystem.consta.Constants
import com.lysoft.baseproject.activity.BaseUIListFragment
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.lysoft.baseproject.imp.BaseCallBack
import com.lysoft.baseproject.imp.LoadStatus

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 15:45
 */
class SuggestionsAllFragment : AdapterViewClickListener, BaseUIListFragment<SuggertionBean>() {

    override fun onCreate() {
        super.onCreate()
        topViewManager().topView().visibility = View.GONE
        loadViewManager().changeLoadState(LoadStatus.LOADING)
        onPageLoad()
    }

    override fun getListData(callBack: BaseCallBack) {
        var suggertionList = mutableListOf<SuggertionBean>()
        for (a in 0..30) {
            var state = "";
            if (a < 3) {
                state = a.toString()
            } else {
                state = "2"
            }
            suggertionList.add(SuggertionBean("采购部", "2020-04-24", "我们每天都要用水，在用水的过程中我们掉的头发丝，洗菜的菜叶，洗碗的食物残渣，油脂等等，都会随着水流进入下水道，其中大部分都被水冲走了，但是还有小一部分就附着在了下水管管壁上。随着我们每天的用水，附着在管壁上的污垢就会越来越多，会直接影响到下水的速度，而我们不去管它那下水道一旦堵塞，不但无法排泄污水，还会影响我们的日常生活。管道里的污垢长期堵塞不疏通的话，还会滋生出大量的细菌散发霉变的味道，还对我们的身体健康产生巨大危害", "1", "下水道总是堵，希望赶紧处理下", "2020-04-05", "1", state))
        }
        callBack.callBack(suggertionList)
    }

    override fun instanceAdapter(list: MutableList<SuggertionBean>): BaseAdapter {
        return SuggestionsListAdapter(pageContext, list, this)
    }

    override fun itemClickListener(position: Int) {
    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }

}