package com.lysoft.baseproject.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zw on 19/12/1.
 */
public class FileUtils {


    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    //File转换为byte[]
    public static byte[] getBytesByFile(String filePath) {
        try {
            File file = new File(filePath);
            //获取输入流
            FileInputStream fis = new FileInputStream(file);
            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //byte[]转换为File
    public static File getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            // 判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            // 判断文件目录是否存在
            if (!file.exists()) {
                file.mkdirs();
            }
            //输出流
            fos = new FileOutputStream(file);
            //缓冲流
            bos = new BufferedOutputStream(fos);
            //将字节数组写出
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    //在这里抽取了一个方法   可以封装到自己的工具类中...
    public static File getFile(Bitmap bitmap, String fileName) {
        File file = new File(Environment.getExternalStorageDirectory() + fileName);
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

            file.delete();
            file.createNewFile();
            fos = new FileOutputStream(file);
            is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) baos.close();
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return file;
    }

    //string转换为File
    public static File getFileFromBytes(Context context, String name, String pathName) {
        byte[] b = name.getBytes();
        BufferedOutputStream stream = null;
        File file = null;
        try {
            File dirsFile = new File(context.getFilesDir().getAbsolutePath());
            if (!dirsFile.exists()) {
                dirsFile.mkdirs();
            }
            //Log.i("创建文件","创建文件");
            file = new File(dirsFile.toString(), pathName);// MYLOG_PATH_SDCARD_DIR
            if (!file.exists()) {
                //在指定的文件夹中创建文件
                file.createNewFile();
            }
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return file;
    }
}
