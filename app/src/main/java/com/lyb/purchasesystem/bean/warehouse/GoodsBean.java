package com.lyb.purchasesystem.bean.warehouse;

import java.io.Serializable;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-22 16:37
 */
public class GoodsBean implements Serializable {
    private String goodsImage;//
    private String goodsName;//
    private String goodsSpecifications;//
    private String goodsPrice;//
    private String goodsStockNum;//
    private String goodsUpdateTime;//

    public GoodsBean(String goodsImage, String goodsName, String goodsSpecifications, String goodsPrice, String goodsStockNum, String goodsUpdateTime) {
        this.goodsImage = goodsImage;
        this.goodsName = goodsName;
        this.goodsSpecifications = goodsSpecifications;
        this.goodsPrice = goodsPrice;
        this.goodsStockNum = goodsStockNum;
        this.goodsUpdateTime = goodsUpdateTime;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(String goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsStockNum() {
        return goodsStockNum;
    }

    public void setGoodsStockNum(String goodsStockNum) {
        this.goodsStockNum = goodsStockNum;
    }

    public String getGoodsUpdateTime() {
        return goodsUpdateTime;
    }

    public void setGoodsUpdateTime(String goodsUpdateTime) {
        this.goodsUpdateTime = goodsUpdateTime;
    }
}
