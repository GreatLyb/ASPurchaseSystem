package com.lyb.purchasesystem.bean

import com.lysoft.baseproject.imp.CommonGalleryImageImp
import java.io.Serializable

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-27 10:35
 */
class ImageBean : CommonGalleryImageImp, Serializable {
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

    override fun getThumb_img(): String {
        return this.ThumImage
    }

    override fun getId(): String {
        return this.ThumImage
    }

    override fun getBig_img(): String {
        return this.ThumImage

    }

    override fun getSource_img(): String {
        return this.ThumImage

    }
}