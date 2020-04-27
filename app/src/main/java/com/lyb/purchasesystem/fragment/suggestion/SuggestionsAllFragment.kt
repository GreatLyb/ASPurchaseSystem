package com.lyb.purchasesystem.fragment.suggestion

import android.content.Intent
import android.view.View
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import com.kongzue.dialog.interfaces.OnMenuItemClickListener
import com.kongzue.dialog.util.BaseDialog
import com.kongzue.dialog.v3.BottomMenu
import com.kongzue.dialog.v3.InputDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.adapter.SuggestionsListAdapter
import com.lyb.purchasesystem.bean.SuggertionBean
import com.lyb.purchasesystem.consta.Constants
import com.lyb.purchasesystem.ui.suggestions.SuggestionsInfoActivity
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
class SuggestionsAllFragment(var appCompatActivity: AppCompatActivity) : AdapterViewClickListener, BaseUIListFragment<SuggertionBean>() {
    val menulist = listOf("驳回该意见", "采纳该意见")
    override fun onCreate() {
        super.onCreate()
        containerView().setBackgroundColor(ContextCompat.getColor(pageContext, R.color.background_color))
        topViewManager().topView().visibility = View.GONE
        pageListView.setBackgroundColor(ContextCompat.getColor(pageContext, R.color.background_color))
        pageListView.dividerHeight = 0
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
        return SuggestionsListAdapter(pageContext, list, this, 1)
    }

    override fun adapterViewClick(position: Int, view: View?) {

        BottomMenu.show(appCompatActivity, menulist, OnMenuItemClickListener({ text, index ->
            InputDialog.show(appCompatActivity, "提示", "请输入处理意见").setOnOkButtonClickListener { baseDialog: BaseDialog, v: View?, inputStr: String? ->
                baseDialog.doDismiss()
                ToastUtils.showShort(inputStr)
                true
            }
        }))
    }

    override fun itemClickListener(position: Int) {
        startActivity(Intent(pageContext, SuggestionsInfoActivity::class.java))
    }

    override fun getPageSize(): Int {
        return Constants.PAGE_SIZE
    }

}