package com.lyb.purchasesystem.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lyb.purchasesystem.bean.DaoMaster;
import com.lyb.purchasesystem.bean.DaoSession;
import com.lyb.purchasesystem.consta.Constants;


/**
 * BiometricsPlugin
 * 类描述：
 * 类传参：
 * Creat by Lyb on 2019/9/20 11:40
 */
public class DaoSessionUtils {
    private static volatile DaoSession Instance = null;

    public DaoSessionUtils() {

    }

    public static DaoSession getInstance(Context context) {
        if (Instance == null) {
            synchronized (DaoSessionUtils.class) {
                if (Instance == null) {
                    Instance = initGreenDao(context);
                }
            }
        }
        return Instance;
    }

    /**
     * 初始化数据库
     */
    private static DaoSession initGreenDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}
