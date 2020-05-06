package com.lyb.purchasesystem.bean.suggertion

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-24 15:50
 */
class SuggertionBean {
    constructor(deaprtment: String, insertTime: String, suggestContent: String, suggestId: String, suggestName: String, updateTime: String, userId: String, status: String) {
        this.deaprtment = deaprtment
        this.insertTime = insertTime
        this.suggestContent = suggestContent
        this.suggestId = suggestId
        this.suggestName = suggestName
        this.updateTime = updateTime
        this.userId = userId
        this.status = status
    }

    var deaprtment = ""
    var insertTime = ""
    var suggestContent = ""
    var suggestId = ""
    var suggestName = ""
    var updateTime = ""
    var userId = ""
    var status = ""
}