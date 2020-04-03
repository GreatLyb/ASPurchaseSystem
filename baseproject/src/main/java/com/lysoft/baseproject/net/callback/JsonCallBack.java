package com.lysoft.baseproject.net.callback;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lysoft.baseproject.net.NetConfig;
import com.lzy.okgo.callback.AbsCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @作者 lidanning
 * 版本：1.0
 * 创建日期：2019/9/20
 * 描述：请求的回调封装
 */

public abstract class JsonCallBack<T> extends AbsCallback<T> {
    /**
     * 数据
     */
    private T dataResponse = null;
    /**
     * 错误码
     */
    protected int code = -1;

    protected String msg = "";
    private String dataStr = "";
    /**
     * 是否成功
     */
    protected Object tag;


    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public T convertResponse(Response response) throws Exception {

        return transform(new String(response.body().bytes()));

    }

    private T transform(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            code = jsonObject.optInt(NetConfig.Code.CODE);
            msg = jsonObject.optString(NetConfig.Code.MSG);
//            success = jsonObject.optBoolean(NetConfig.Code.SUCCESS);
            dataStr = jsonObject.opt(NetConfig.Code.MODEL).toString();
            Log.i("lyb", "==" + response);
            if (dataStr.charAt(0) == 123) {
                //获取泛型类型
                Class<T> classOfT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                if (classOfT == String.class) {
                    dataResponse = (T) dataStr;
                } else {
                    //对象
//                    dataResponse = (new Gson()).fromJson(dataStr, classOfT);
                    dataResponse = JSON.parseObject(dataStr, classOfT);
                }
            } else if (dataStr.charAt(0) == 91) {
                //数组
                Type collectionType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//                dataResponse = new Gson().fromJson(dataStr, collectionType);
                dataResponse = JSON.parseObject(dataStr, collectionType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;

    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        T body = response.body();
        onSuccess(code, msg, body);
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Exception exception = (Exception) response.getException();
        onFailure(tag, exception);
    }

    /**
     * 请求成功后处理数据
     *
     * @param code
     * @param msg
     * @param response
     */
    public abstract void onSuccess(int code, String msg, T response);

    /**
     * 请求失败后处理
     *
     * @param tag
     * @param e
     */
    public abstract void onFailure(Object tag, Exception e);
}