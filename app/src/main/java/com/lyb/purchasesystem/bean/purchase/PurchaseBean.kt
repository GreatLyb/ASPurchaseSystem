package com.lyb.purchasesystem.bean.purchase

import java.io.Serializable

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020/5/3 15:31
 */
class PurchaseBean : Serializable {

    lateinit var purchaseName: String//采购名称
    lateinit var purchasespecifications: String//采购类型
    lateinit var purchasePrice: String//采购价格
    lateinit var purchaseNum: String//采购数量
    lateinit var purchaseUseTime: String//采购使用时间
    lateinit var purchasePublishTime: String//采购发布时间
    lateinit var purchaseRemarks: String//采购备注
    lateinit var purchasePerson: String//采购申请人
    lateinit var purchaseDealState: String//采购处理状态
    lateinit var purchaseDealPerson: String//处理人
    lateinit var purchaseDealTime: String//处理时间
    lateinit var purchaseDealContent: String//处理意见
    lateinit var purchaseDepartment: String//采购部门


    constructor(purchaseName: String, purchasespecifications: String, purchasePrice: String, purchaseNum: String, purchaseUseTime: String, purchasePublishTime: String, purchaseRemarks: String, purchasePerson: String, purchaseDealState: String, purchaseDealPerson: String, purchaseDealTime: String, purchaseDealContent: String, purchaseDepartment: String) {
        this.purchaseName = purchaseName
        this.purchasespecifications = purchasespecifications
        this.purchasePrice = purchasePrice
        this.purchaseNum = purchaseNum
        this.purchaseUseTime = purchaseUseTime
        this.purchasePublishTime = purchasePublishTime
        this.purchaseRemarks = purchaseRemarks
        this.purchasePerson = purchasePerson
        this.purchaseDealState = purchaseDealState
        this.purchaseDealPerson = purchaseDealPerson
        this.purchaseDealTime = purchaseDealTime
        this.purchaseDealContent = purchaseDealContent
        this.purchaseDepartment = purchaseDepartment
    }

}
