package com.lyb.purchasesystem.utils;

import android.content.Context;
import android.text.TextUtils;

import com.lyb.purchasesystem.bean.UserBean;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-08 17:09
 */
public class UserInfoUtils {

    public static boolean isLogin(Context context) {
        UserBean unique = DaoSessionUtils.getInstance(context).getUserBeanDao().queryBuilder().unique();
        if (unique == null) {
            return false;
        }
        return !TextUtils.isEmpty(unique.getToken());
    }

    public static UserBean getUserInfo(Context context) {
        return DaoSessionUtils.getInstance(context).getUserBeanDao().queryBuilder().unique();
    }

    public static boolean saveUserInfo(Context context, UserBean userBean) {
        DaoSessionUtils.getInstance(context).getUserBeanDao().deleteAll();
        return DaoSessionUtils.getInstance(context).getUserBeanDao().insertOrReplace(userBean) > 0;

    }

    public static void loginOut(Context context) {
        DaoSessionUtils.getInstance(context).getUserBeanDao().deleteAll();
    }

}
