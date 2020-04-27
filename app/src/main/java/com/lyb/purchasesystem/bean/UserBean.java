package com.lyb.purchasesystem.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-07 14:47
 */
//@ent
@Entity
public class UserBean {
    @Unique
    private String token;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String icon;
    private String departments;
    private String power;
    private String userId;

    @Generated(hash = 797402205)
    public UserBean(String token, String email, String phone, String username,
                    String password, String icon, String departments, String power,
                    String userId) {
        this.token = token;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.departments = departments;
        this.power = power;
        this.userId = userId;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDepartments() {
        return this.departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getPower() {
        return this.power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
