package com.lyb.purchasesystem.bean.departdevice;

import java.io.Serializable;
import java.util.List;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-26 18:00
 */
public class DepartDeviceBean implements Serializable {
    private String deviceName;//名称
    private String deviceBrand;//品牌
    private String devicePrice;//价格
    private String deviceNum;//数量
    private String deviceId;//id
    private List<DepartDeviceBean> departDeviceBeans;//id

    public DepartDeviceBean(String deviceName, String deviceBrand, String devicePrice, String deviceNum, String deviceId, List<DepartDeviceBean> departDeviceBeans) {
        this.deviceName = deviceName;
        this.deviceBrand = deviceBrand;
        this.devicePrice = devicePrice;
        this.deviceNum = deviceNum;
        this.deviceId = deviceId;
        this.departDeviceBeans = departDeviceBeans;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<DepartDeviceBean> getDepartDeviceBeans() {
        return departDeviceBeans;
    }

    public void setDepartDeviceBeans(List<DepartDeviceBean> departDeviceBeans) {
        this.departDeviceBeans = departDeviceBeans;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(String devicePrice) {
        this.devicePrice = devicePrice;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }
}
