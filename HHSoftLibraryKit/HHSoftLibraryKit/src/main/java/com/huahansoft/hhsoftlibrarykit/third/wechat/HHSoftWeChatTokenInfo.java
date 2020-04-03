package com.huahansoft.hhsoftlibrarykit.third.wechat;

public class HHSoftWeChatTokenInfo {
    private String accessToken;//	接口调用凭证
    private String expiresIn;//	access_token接口调用凭证超时时间，单位（秒）
    private String refreshToken;//	用户刷新access_token
    private String openid;//	授权用户唯一标识
    private String scope;//	用户授权的作用域，使用逗号（,）分隔
    private String unionid;//	当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
