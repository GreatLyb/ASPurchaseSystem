package com.lyb.purchasesystem.bean

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-27 10:35
 */
class ImageBean {
    /**
     * 缩略图
     */
    lateinit var ThumImage: String

    /**
     * 大图
     */
    lateinit var BigImage: String

    /**
     * 原图
     */
    lateinit var Originalimage: String

    constructor(ThumImage: String, BigImage: String, Originalimage: String) {
        this.ThumImage = ThumImage
        this.BigImage = BigImage
        this.Originalimage = Originalimage
    }
}