package com.lyb.purchasesystem;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.Gravity;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.lyb.purchasesystem.ui.SplashActivity;
import com.lysoft.baseproject.BuildConfig;
import com.lysoft.baseproject.HHSoftApplication;
import com.lysoft.baseproject.InterCept;
import com.lysoft.baseproject.crash.CaocConfig;
import com.lysoft.baseproject.manager.LoadViewManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import androidx.multidex.MultiDex;
import okhttp3.OkHttpClient;

/**
 * Describe：基础Application所有需要模块化开发的module都需要继承自BaseApplication
 */
public class BaseApplication extends HHSoftApplication {

    //全局唯一的context
//    private static BaseApplication application;

    private static SoftReference<Application> reference;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        application = this;
        reference = new SoftReference<>(this);
        //  MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // 打印日志
        if (isDebug()){
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险
        }
        ARouter.init(getApplication()); // 尽可能早，推荐在Application中初始化
        //全局异常捕捉
        initCrash();
        initLogger();
        initOKGo();
        initDialog();
        ToastUtils.init(this, new ToastQQStyle(this));
        ToastUtils.getToast().setDuration(Toast.LENGTH_SHORT);
        // 设置吐司重心
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 150);


    }

    private void initDialog() {
        DialogSettings.isUseBlur = (true);               //是否开启模糊效果，默认关闭
        DialogSettings.style = (DialogSettings.STYLE.STYLE_IOS);      //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS
        DialogSettings.theme = (DialogSettings.THEME.DARK);      //全局明暗风格，提供两种可选主题，LIGHT, DARK
        //        DialogSettings.titleTextInfo = (TextInfo);          //全局标题文字样式
        //        DialogSettings.contentTextInfo = (TextInfo);        //全局正文文字样式
        //        DialogSettings.buttonTextInfo = (TextInfo);         //全局默认按钮文字样式
        //        DialogSettings.buttonPositiveTextInfo = (TextInfo); //全局焦点按钮文字样式（一般指确定按钮）
        //        DialogSettings.inputInfo = (InputInfo);             //全局输入框文本样式
        //        DialogSettings.backgroundColor = (ColorInt);        //全局对话框背景颜色，值0时不生效
        DialogSettings.cancelable = (true);              //全局对话框默认是否可以点击外围遮罩区域或返回键关闭，此开关不影响提示框（TipDialog）以及等待框（TipDialog）
        //        DialogSettings.cancelableTipDialog = (boolean);     //全局提示框及等待框（WaitDialog、TipDialog）默认是否可以关闭
        //        DialogSettings.DEBUGMODE = (boolean);               //是否允许打印日志
        //        DialogSettings.blurAlpha = (int);                   //开启模糊后的透明度（0~255）
    }

    private boolean isDebug() {
        try {
            ApplicationInfo  info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void initAppTopViewInfo() {

    }

    @Override
    protected LoadViewManager.LoadMode initAppLoadMode() {
        return LoadViewManager.LoadMode.DRAWABLE;
    }


    /**
     * 初始化全局异常捕捉
     */
    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(1000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(SplashActivity.class) //重新启动后的activity
                //                                .errorActivity(DefaultErrorActivity.class) //崩溃后的错误activity
                //                                .eventListener(new CustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    /**
     * 初始化日志打印框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)                   //（可选）是否显示线程信息。 默认值为true
                .methodCount(1)                          //（可选）要显示的方法行数。 默认2
                .methodOffset(7)                         //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .logStrategy(new LogcatLogStrategy())    //（可选）更改要打印的日志策略。 默认LogCat
                .tag("LargeScreenShop")                              //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //DEBUG模式下不打印LOG
                return BuildConfig.DEBUG;
            }
        });

    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        BaseDialog.unload();
    }

    /**
     * 获取全局唯一上下文
     *
     * @return BaseApplication
     */
    public static Application getApplication() {
        return reference.get();
    }


    /**
     * 退出应用
     */
    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 初始化okgo
     */
    private void initOKGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        InterCept loggingInterceptor = new InterCept("LargeScreenOkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.OFF);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置，默认60秒
        builder.readTimeout(12000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(12000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(12000 * 5, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        OkGo.getInstance().init(this)                          //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(2);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

}