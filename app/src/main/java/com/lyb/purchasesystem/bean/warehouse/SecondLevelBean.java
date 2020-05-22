package com.lyb.purchasesystem.bean.warehouse;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-21 18:14
 */
public class SecondLevelBean {

    boolean isSelect = false;
    private int id;
    private String secondName;
    private int parentId;

    public SecondLevelBean(int id, String secondName, int parentId) {
        this.id = id;
        this.secondName = secondName;
        this.parentId = parentId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
