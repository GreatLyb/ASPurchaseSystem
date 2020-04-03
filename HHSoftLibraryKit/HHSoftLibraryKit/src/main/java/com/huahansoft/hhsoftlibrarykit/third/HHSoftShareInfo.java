package com.huahansoft.hhsoftlibrarykit.third;

import android.app.Activity;
import android.graphics.Bitmap;

public class HHSoftShareInfo {
    private Activity activity;//qq分享必传;微博分享必传
    private String localImagePath;//qq分享保存Bitmap的文件路径，必传

    private String shareTitle;//必传
    private String shareDesc;//必传
    private String linkUrl;//必传
    private String imageUrl;
    private Bitmap thumpBitmap;//必传
    private int qqShareType = 0;
    private int wechatShareType = 0;//0网页分享；1图片分享
    private int wechatShareScene = 0;//微信分享场景；0微信；1朋友圈
    private int weiboShareType = 0;//0网页分享

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getWechatShareScene() {
        return wechatShareScene;
    }

    public void setWechatShareScene(int wechatShareScene) {
        this.wechatShareScene = wechatShareScene;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getThumpBitmap() {
        return thumpBitmap;
    }

    public void setThumpBitmap(Bitmap thumpBitmap) {
        this.thumpBitmap = thumpBitmap;
    }

    public int getQqShareType() {
        return qqShareType;
    }

    public void setQqShareType(int qqShareType) {
        this.qqShareType = qqShareType;
    }

    public int getWechatShareType() {
        return wechatShareType;
    }

    public void setWechatShareType(int wechatShareType) {
        this.wechatShareType = wechatShareType;
    }

    public int getWeiboShareType() {
        return weiboShareType;
    }

    public void setWeiboShareType(int weiboShareType) {
        this.weiboShareType = weiboShareType;
    }
}
