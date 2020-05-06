package com.lyb.purchasesystem.bean.clat

import com.contrarywind.interfaces.IPickerViewData

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-28 11:02
 */
class ClatAtTypeBean : IPickerViewData {
    lateinit var typeName: String;
    lateinit var typeID: String;

    constructor(typeName: String, typeID: String) {
        this.typeName = typeName
        this.typeID = typeID
    }

    override fun getPickerViewText(): String {
        return typeName
    }

}