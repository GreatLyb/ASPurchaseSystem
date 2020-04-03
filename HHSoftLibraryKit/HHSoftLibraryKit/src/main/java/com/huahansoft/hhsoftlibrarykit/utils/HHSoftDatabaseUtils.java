package com.huahansoft.hhsoftlibrarykit.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @类说明 HHSoftDatabaseUtils
 * @作者 hhsoft
 * @创建日期 2019/8/28 12:24
 */
public class HHSoftDatabaseUtils {

    /**
     * 数据库的创建和版本管理的助手
     */
    private SQLiteOpenHelper mDatabaseHelper;
    /**
     * 是Android提供的用来管理Sqlite database的一种数据持久化解决方案
     */
    private SQLiteDatabase mDatabase;
    /**
     * Integer类型的线程安全原子类，可以在应用程序中以原子的方式更新int值
     */
    private static AtomicInteger mOpenCounter = new AtomicInteger();
    /**
     * 主线程
     */
    private static Handler mHandler;
    /**
     * 子线程
     */
    private Handler mWorkHandler;

    public HHSoftDatabaseUtils() {
        this.mHandler = new Handler(Looper.getMainLooper());
        HandlerThread workThread = new HandlerThread("DATABASE_WORK");
        workThread.start();
        this.mWorkHandler = new Handler(workThread.getLooper());
    }

    public void execute(String sql, Object[] bindArgs, ResultCallback callback) {
        this.mWorkHandler.post(() -> {
            SQLiteDatabase database = getSQLiteDatabase();
            try {
                database.execSQL(sql, bindArgs);
                if (callback != null) {
                    callback.onCallback(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onFail(e);
                }
            }
        });
    }
    public void execute(Runnable runnable) {
        this.mWorkHandler.post(runnable);
    }

    /**
     * 数据库工具类初始化
     *
     * @param helper
     */
    public static void initDatabase(SQLiteOpenHelper helper) {
        SingletonHolder.mInstance.mDatabaseHelper = helper;
    }

    /**
     * 获取SQLiteDatabase
     *
     * @return
     */
    public SQLiteDatabase getSQLiteDatabase() {
        int count = mOpenCounter.incrementAndGet();
        if (count == 1 || mDatabase == null) {
            if (mDatabaseHelper != null) {
                mDatabase = mDatabaseHelper.getWritableDatabase();
            }
        }
        return mDatabase;
    }

    /**
     * 关闭SQLiteDatabase
     */
    public void closeSQLiteDataBase() {
        int count = mOpenCounter.decrementAndGet();
        if (count == 0) {
            if (mDatabase != null) {
                mDatabase.close();
            }
        }
    }

    /**
     * 关闭数据库工具类，释放数据库对象
     */
    public void close() {
        if (mDatabaseHelper != null) {
            mDatabaseHelper.close();
            mDatabaseHelper = null;
        }
    }

    public static HHSoftDatabaseUtils getInstance() {
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        static HHSoftDatabaseUtils mInstance = new HHSoftDatabaseUtils();

        private SingletonHolder() {
        }
    }

    public abstract static class ResultCallback<T> {
        public ResultCallback() {
        }

        public abstract void successCallBack(T var1);

        public abstract void failureCallBack(Throwable t);

        void onFail(final Throwable t) {
            HHSoftDatabaseUtils.mHandler.post(() -> ResultCallback.this.failureCallBack(t));
        }

        void onCallback(final T t) {
            HHSoftDatabaseUtils.mHandler.post(() -> ResultCallback.this.successCallBack(t));
        }
    }
}
