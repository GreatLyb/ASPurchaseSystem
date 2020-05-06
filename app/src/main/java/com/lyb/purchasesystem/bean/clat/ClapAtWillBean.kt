package com.lyb.purchasesystem.bean.clat

import com.lyb.purchasesystem.bean.ImageBean
import java.io.Serializable

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-27 10:26
 */
class ClapAtWillBean : Serializable {
    lateinit var clapAtTitle: String    //标题
    lateinit var clapAtContent: String //内容
    lateinit var clapAtTime: String  //时间
    lateinit var clapAtUserName: String  //发布人
    lateinit var mutableList: MutableList<ImageBean> //三张图片
    lateinit var dealState: String  //处理状态 0.等待处理  1.正在处理中 2.完成处理  3.处理时间超时 4超时未处理
    lateinit var dealPersonName: String //处理人
    lateinit var dealNeedTime: String   //处理预计需要时间
    lateinit var dealRealTime: String   //实际处理时间
    lateinit var dealContent: String   //处理意见


    constructor(clapAtTitle: String, clapAtContent: String, clapAtTime: String, clapAtUserName: String, mutableList: MutableList<ImageBean>, dealState: String, dealPersonName: String, dealNeedTime: String, dealRealTime: String, dealContent: String) {
        this.clapAtTitle = clapAtTitle
        this.clapAtContent = clapAtContent
        this.clapAtTime = clapAtTime
        this.clapAtUserName = clapAtUserName
        this.mutableList = mutableList
        this.dealState = dealState
        this.dealPersonName = dealPersonName
        this.dealNeedTime = dealNeedTime
        this.dealRealTime = dealRealTime
        this.dealContent = dealContent
    }
}