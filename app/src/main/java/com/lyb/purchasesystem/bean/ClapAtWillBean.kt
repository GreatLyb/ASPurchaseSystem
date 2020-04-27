package com.lyb.purchasesystem.bean

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-27 10:26
 */
class ClapAtWillBean {
    lateinit var clapAtTitle: String    //标题
    lateinit var clapAtContent: String //内容
    lateinit var clapAtTime: String  //时间
    lateinit var clapAtUserName: String  //发布人
    lateinit var mutableList: MutableList<ImageBean> //三张图片
    lateinit var dealState: String  //处理状态
    lateinit var dealPersonName: String //处理人
    lateinit var dealNeedTime: String   //处理预计需要时间

    constructor(clapAtTitle: String, clapAtContent: String, clapAtTime: String, clapAtUserName: String, mutableList: MutableList<ImageBean>, dealState: String, dealPersonName: String, dealNeedTime: String) {
        this.clapAtTitle = clapAtTitle
        this.clapAtContent = clapAtContent
        this.clapAtTime = clapAtTime
        this.clapAtUserName = clapAtUserName
        this.mutableList = mutableList
        this.dealState = dealState
        this.dealPersonName = dealPersonName
        this.dealNeedTime = dealNeedTime
    }
}