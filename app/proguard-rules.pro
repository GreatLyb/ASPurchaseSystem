# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#图片裁剪============================================
-keep class androidx.appcompat.widget.** { *; }

#弹出框Dialog3========================================
-keep class com.kongzue.dialog.** { *; }
-dontwarn com.kongzue.dialog.**

# 额外的，建议将 android.view 也列入 keep 范围：
-keep class android.view.** { *; }

# 如果有开启模糊效果，建议将 Renderscript 也列入 keep 范围：
-dontwarn android.support.v8.renderscript.**
-keep public class android.support.v8.renderscript.** { *; }

# AndroidX版本请使用如下配置：
-dontwarn androidx.renderscript.**
-keep public class androidx.renderscript.** { *; }

#轮播图-===================================================

 -keep class androidx.recyclerview.widget.**{*;}
  -keep class androidx.viewpager2.widget.**{*;}

#Arouter====================================================
  -keep public class com.alibaba.android.arouter.routes.**{*;}
  -keep public class com.alibaba.android.arouter.facade.**{*;}
  -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
  # If you use the byType method to obtain Service, add the following rules to protect the interface:
  -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
  # If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
  # -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#GreenDao====================================================
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties { *; }
# If you DO use SQLCipher:
-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }
# If you do NOT use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do NOT use RxJava:
-dontwarn rx.**

#eventBus====================================================
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#okhttp===============================================
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}