package com.huahansoft.hhsoftlibrarykit.third;

/**
 * @类说明 分享item信息
 * @作者 hhsoft
 * @创建日期 2019/8/31 12:42
 */
public class HHSoftShareItemInfo implements Comparable<HHSoftShareItemInfo> {
    /**
     * 平台id
     */
    private int platformID;
    /**
     * 分享排序
     */
    private int order;
    /**
     * 分享平台名字的资源id
     */
    private int nameResID;
    /**
     * 分享平台logo的资源id
     */
    private int drawableResID;

    public HHSoftShareItemInfo(int order, int platformID, int nameResID, int drawableResID) {
        this.order = order;
        this.platformID = platformID;
        this.nameResID = nameResID;
        this.drawableResID = drawableResID;
    }

    public int getPlatformID() {
        return platformID;
    }

    public void setPlatformID(int platformID) {
        this.platformID = platformID;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getNameResID() {
        return nameResID;
    }

    public void setNameResID(int nameResID) {
        this.nameResID = nameResID;
    }

    public int getDrawableResID() {
        return drawableResID;
    }

    public void setDrawableResID(int drawableResID) {
        this.drawableResID = drawableResID;
    }

    @Override
    public int compareTo(HHSoftShareItemInfo another) {
        return (order - another.getOrder());
    }
}
