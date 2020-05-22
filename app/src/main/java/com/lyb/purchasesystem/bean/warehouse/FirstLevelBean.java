package com.lyb.purchasesystem.bean.warehouse;

import java.util.ArrayList;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-21 18:13
 */
public class FirstLevelBean {
    private int parentId;
    private String firstName;
    private ArrayList<SecondLevelBean> secondLevelMenus;
    private boolean isSelect = false;

    public FirstLevelBean(int parentId, String firstName, ArrayList<SecondLevelBean> secondLevelMenus) {
        this.parentId = parentId;
        this.firstName = firstName;
        this.secondLevelMenus = secondLevelMenus;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ArrayList<SecondLevelBean> getSecondLevelMenus() {
        return secondLevelMenus;
    }

    public void setSecondLevelMenus(ArrayList<SecondLevelBean> secondLevelMenus) {
        this.secondLevelMenus = secondLevelMenus;
    }
}
