package com.huahansoft.hhsoftlibrarykit.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @类说明 流对象操作
 * @作者 hhsoft
 * @创建日期 2019/8/18 18:56
 */
public class HHSoftStreamUtils {
    private static final String TAG="HHSoftStreamUtils";
    /**
     * 把流对象转换成字符串
     *
     * @param stream 流对象
     * @return 字符串
     */
    public static String convertStreamToString(InputStream stream) {
        if (stream != null) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = stream.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                bos.flush();
                stream.close();
                byte[] stringInfo = bos.toByteArray();
                return new String(stringInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    /**
     * Bitmap转换成字节
     *
     * @param bitmap      bitmap对象
     * @param needRecycle bitmap 是否需要回收
     * @return
     */
    public static byte[] convertBitmapToByteArray(Bitmap bitmap, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取Uri对应的InputStream
     *
     * @param context 上下文对象
     * @param uri     uri路径
     * @return 获取输入流失败返回null
     */
    public static InputStream inputStreamFromUri(Context context, Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        try {
            InputStream is = resolver.openInputStream(uri);
            return is;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从Assets文件夹中获取一个文件的输入流
     *
     * @param context  上下文对象
     * @param fileName 文件的名称
     * @return 如果文件不存在或者其他原因获取失败返回null
     */
    public static InputStream inputStreamFromAssetsFile(Context context, String fileName) {
        AssetManager manager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = manager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static byte[] toByteArray(String hexString) {
        if (TextUtils.isEmpty(hexString))
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        Log.i(TAG,"bitmap2Bytes=="+output.toByteArray());
        return output.toByteArray();
    }
    /**
     * 获取bitmap字节大小
     *
     * @param bitmap
     * @return
     */
    public static long getBitmapByteSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        Log.i(TAG, "getBitmapByteSize==" + bitmap.getRowBytes());
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
