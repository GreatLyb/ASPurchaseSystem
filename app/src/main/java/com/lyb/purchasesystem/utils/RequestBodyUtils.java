package com.lyb.purchasesystem.utils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-24 14:06
 */
public class RequestBodyUtils {
    public static MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

    public static RequestBody getRequestBody(Map<String, String> parmMap) {
        String s = com.alibaba.fastjson.JSON.toJSONString(parmMap);
        return RequestBody.create(mediaType, s);
    }
}
