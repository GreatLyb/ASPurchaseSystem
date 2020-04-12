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
#图片裁剪
-keep class androidx.appcompat.widget.** { *; }

#弹出框----------------------
-keep class com.kongzue.dialog.** { *; }
-dontwarn com.kongzue.dialog.**
# 额外的，建议将 android.view 也列入 keep 范围：
-keep class android.view.** { *; }
# 如果有开启模糊效果，建议将 Renderscript 也列入 keep 范围：
-dontwarn android.support.v8.renderscript.**
-keep public class android.support.v8.renderscript.** { *; }
# AndroidX版本请使用如下配置：
-dontwarn androidx.renderscript.RenderScript.**
-keep public class androidx.renderscript.RenderScript.** { *; }

#轮播图----------------------

  -keep class androidx.recyclerview.widget.**{*;}
    -keep class androidx.viewpager2.widget.**{*;}