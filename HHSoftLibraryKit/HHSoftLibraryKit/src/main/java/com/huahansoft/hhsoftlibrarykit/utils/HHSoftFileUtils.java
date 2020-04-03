package com.huahansoft.hhsoftlibrarykit.utils;


import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * @类说明 文件操作类
 * @作者 hhsoft
 * @创建日期 2019/8/19 16:47
 */
public class HHSoftFileUtils {
    private static final String TAG = "HHSoftFileUtils";

    /**
     * 判断SD卡是否装载
     *
     * @return true:sd卡已装载；false:sd卡未装载
     */
    public static boolean isSDExist() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个文件是否存在
     *
     * @param filePath 文件或者文件夹的路径
     * @return true：文件存在  false:文件不存在
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 创建一个目录
     *
     * @param dirName 目录名（路径）
     * @return File：当前目录文件
     */
    public static File createDirectory(String dirName) {
        File file = new File(dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 删除单个文件
     *
     * @param filePath 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean isSuccess = true;
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (dirFile.exists() && dirFile.isDirectory()) {
            // 删除文件夹中的所有文件包括子目录
            File[] files = dirFile.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    //删除子文件
                    isSuccess = deleteFile(file.getAbsolutePath()) && isSuccess;
                } else {
                    //删除子目录
                    isSuccess = deleteDirectory(file.getAbsolutePath()) && isSuccess;
                }
            }
            //删除当前目录
            isSuccess = dirFile.delete() && isSuccess;
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 根据路径删除指定的目录或文件
     *
     * @param filePath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false
     */
    public static boolean deleteFolder(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) {
                //为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                //为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
        return false;
    }


    /**
     * 复制单个文件
     *
     * @param sourceFilePath 原文件路径
     * @param targetFilePath 目标文件路径
     * @return 复制成功返回true，出现异常返回false
     */
    public static boolean copyFile(String sourceFilePath, String targetFilePath) {
        File file = new File(sourceFilePath);
        if (file.exists() && file.isFile()) {
            try {
                File targetFile = new File(targetFilePath);
                FileInputStream fis = new FileInputStream(file);
                FileChannel inChannel = fis.getChannel();
                FileOutputStream fos = new FileOutputStream(targetFile);
                FileChannel outChannel = fos.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inChannel.close();
                outChannel.close();
                fis.close();
                fos.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 目录及目录下的文件
     *
     * @param sourceFilePath 原文件路径
     * @param targetFilePath 目标文件路径
     * @return 复制成功返回true，出现异常返回false
     */
    public static boolean copyDirectory(String sourceFilePath, String targetFilePath) {
        boolean isSuccess = true;
        File dirFile = new File(sourceFilePath);
        if (dirFile.exists() && dirFile.isDirectory()) {
            // 如果dir不以文件分隔符结尾，自动添加文件分隔符
            if (!targetFilePath.endsWith(File.separator)) {
                targetFilePath = targetFilePath + File.separator;
            }
            File targetFile = new File(targetFilePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            File[] files = dirFile.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    isSuccess = copyFile(file.getAbsolutePath(), targetFilePath + file.getName()) && isSuccess;
                } else {
                    isSuccess = copyFolder(file.getAbsolutePath(), targetFilePath + file.getName()) && isSuccess;
                }
            }
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 复制指定的目录或文件
     *
     * @param sourceFilePath 原目录或文件路径
     * @param targetFilePath 目标目录或文件路径
     * @return 复制成功返回true，出现异常返回false
     */
    public static boolean copyFolder(String sourceFilePath, String targetFilePath) {
        File file = new File(sourceFilePath);
        if (file.exists()) {
            if (file.isFile()) {
                //为文件时调用复制文件方法
                return copyFile(sourceFilePath, targetFilePath);
            } else {
                //为目录时调用复制目录方法
                return copyDirectory(sourceFilePath, targetFilePath);
            }
        }
        return false;
    }

    /**
     * 重命名指定的目录或文件
     *
     * @param sourceFilePath 原目录或文件路径
     * @param targetFilePath 重命名目录或文件路径
     */
    public static void renameFolder(String sourceFilePath, String targetFilePath) {
        File oldFile = new File(sourceFilePath);
        if (oldFile.exists()) {
            File newFile = new File(targetFilePath);
            oldFile.renameTo(newFile);
        }
    }

    /**
     * 保存Bitmap到本地文件
     *
     * @param bitmap   图片的bitmap对象
     * @param filePath 图片保存的文件路径
     * @param quality  图片压缩率，eg:不压缩是100，即压缩率为0
     * @return
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, String filePath, int quality) {
        boolean isSuccess = false;
        try {
            File file = new File(filePath);
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                isSuccess = true;
                out.flush();
                out.close();
                out = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            HHSoftLogUtils.e("HHSoftFileUtils","saveBitmapToFile=="+ Log.getStackTraceString(e));
        }
        return isSuccess;
    }

    /**
     * 内部存储：向指定的文件中写入指定的数据：文件默认存储位置：/data/data/包名/files/文件名（fileName）
     *
     * @param fileName 文件名 eg:test
     * @param content  文件内容
     * @return 写入成功返回true，写入失败返回false
     */
    public static boolean writeFileData(String fileName, String content) {
        try {
            //获得FileOutputStream
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            //将要写入的字符串转换为byte数组
            byte[] bytes = content.getBytes();
            fout.write(bytes);//将byte数组写入文件
            fout.close();//关闭文件输出流
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 内部存储：读取一个内部存储的私有文件
     *
     * @param fileName 文件名 eg:test
     * @return 读取内容为空，返回空字符串
     */
    public static String readFileData(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 向指定的文件中写入指定的数据
     *
     * @param filePath 文件路径
     * @param content  文件内容
     * @return 写入成功返回true，写入失败返回false
     */
    public static boolean writeFileDataToSDCard(String filePath, String content) {
        try {
            if (isSDExist()) {
                File file = new File(filePath);
                if (!file.exists()) {
                    //先创建文件夹/目录
                    file.getParentFile().mkdirs();
                    //再创建新文件
                    file.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(file);
                //将要写入的字符串转换为byte数组
                byte[] bytes = content.getBytes();
                fout.write(bytes);//将byte数组写入文件
                fout.close();//关闭文件输出流
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取SDCard文件内容
     *
     * @param filePath 文件名路径
     * @return 读取内容为空，返回空字符串
     */
    public static String readFileDataFromSDCard(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                //先创建文件夹/目录
                file.getParentFile().mkdirs();
                //再创建新文件
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定目录/文件的大小
     *
     * @param filePath 目录/文件的路径
     * @return 指定目录/文件的大小
     */
    public static long fileSize(String filePath) {
        long size = 0;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files!=null&&files.length>0){
                    for (File f : files) {
                        size += fileSize(f.getAbsolutePath());
                    }
                }
            } else {
                size = file.length();
            }
        }
        return size;
    }

    /**
     * 自定义文件类型
     */
    public enum FileType {
        IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF, IMAGE_TIFF, IMAGE_WEBP, IMAGE_BMP
    }

    /**
     * 根据图片的文件头判断图片类别
     *
     * @param filePath 文件路径
     * @return 返回自定义文件枚举类型FileType
     */
    public static FileType fileTypeForImageData(String filePath) {
        byte[] b = new byte[4];
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            is.read(b, 0, b.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String type = bytesToHexString(b).toUpperCase();
        if (type.contains("FFD8FF")) {
            return FileType.IMAGE_JPEG;
        } else if (type.contains("89504E47")) {
            return FileType.IMAGE_PNG;
        } else if (type.contains("47494638")) {
            return FileType.IMAGE_GIF;
        } else if (type.contains("49492A00")) {
            return FileType.IMAGE_TIFF;
        } else if (type.contains("424D")) {
            return FileType.IMAGE_BMP;
        } else if (type.contains("52494646")) {
            return FileType.IMAGE_WEBP;
        }
        return FileType.IMAGE_JPEG;
    }


    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 获取文件夹名字
     *
     * @param filePath
     * @return
     */
    public static String dirNameByFilePath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        } else {
            int lastSep = filePath.lastIndexOf(File.separator);
            return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
        }
    }

    /**
     * 保存Bitmap对象到文件
     *
     * @param bitmap         bitmap对象
     * @param targetFilePath 保存文件的路径
     * @return 返回保存文件的路径，保存失败返回空字符串
     */
    public static String writeBitmapToFile(Bitmap bitmap, String targetFilePath) {
        if (bitmap == null) {
            return null;
        }
        try {
            File file = new File(targetFilePath);
            if (!file.exists()) {
                //先创建文件夹/目录
                file.getParentFile().mkdirs();
                //再创建新文件
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return targetFilePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据Uri获取文件路径
     *
     * @param context
     * @param uri     待查找路径的Uri
     * @return 查询失败返回null
     */
    public static String filePathByUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return filePathByUriForApi19(context, uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            return uri.getPath();
        }
        return null;
    }

    /**
     * 获取uri的文件路径，目标版本19
     *
     * @param context
     * @param uri
     * @return
     */
    private static String filePathByUriForApi19(Context context, Uri uri) {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 判断文件路径是否为网络路径
     *
     * @param filePath
     * @return
     */
    public static boolean isHttpUrl(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return filePath.startsWith("http") || filePath.startsWith("https");
    }
}
