package com.lyb.purchasesystem.bean

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-21 14:12
 */
class MsgBean {
    var msgTitle = "";
    var msgContent = "";
    var msgTime = "";
    var isRead = "";
    var msgType = "";
    var msgUrl = "";

    constructor(msgTitle: String, msgContent: String, msgTime: String, isRead: String, msgType: String, msgUrl: String) {
        this.msgTitle = msgTitle
        this.msgContent = msgContent
        this.msgTime = msgTime
        this.isRead = isRead
        this.msgType = msgType
        this.msgUrl = msgUrl
    }
}