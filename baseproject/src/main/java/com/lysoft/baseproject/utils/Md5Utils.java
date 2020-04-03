package com.lysoft.baseproject.utils;

import android.util.Log;

import java.security.MessageDigest;

/**
 * LargeScreenShop
 * 类描述：
 * 类传参：
 * Creat by Lyb on 2019/10/22 8:40
 */
public class Md5Utils {

    private static byte[] md5(String s)
    {
        MessageDigest algorithm;
        try
        {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        }
        catch (Exception e)
        {
            Log.e("MD5 Error...", ""+e);
        }
        return null;
    }

    private static  String toHex(byte hash[])
    {
        if (hash == null)
        {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++)
        {
            if ((hash[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s)
    {
        try
        {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        }
        catch (Exception e)
        {
            Log.e("Lyb", "not supported charset...{}"+e);
            return s;
        }
    }
}
