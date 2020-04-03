package com.huahansoft.hhsoftlibrarykit.utils;

import android.text.TextUtils;
import android.util.Log;

import com.huahansoft.hhsoftlibrarykit.retrofit.HHSoftRetrofitManager;
import com.huahansoft.hhsoftlibrarykit.retrofit.HHSoftRetrofitService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class HHSoftNetworkUtils {
    private final String TAG = "HHSoftNetworkUtils";
//    private final String TAG="xiao";

    /**
     * 网络请求：post异步请求,明文传参
     *
     * @param ip              IP地址
     * @param methodName      接口名字
     * @param parameterMap    参数map
     * @param headerMap       请求头map
     * @param successCallBack 请求成功回调
     * @param failureCallBack 请求失败回调
     * @return
     */
    public Call<String> postRequestFormURLWithHeadersAsync(String ip, String methodName, Map<String, String> parameterMap, Map<String, String> headerMap, final BiConsumer<Call<String>, String> successCallBack, final BiConsumer<Call<String>, Throwable> failureCallBack) {
        Call<String> call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).callPostRequestFormUrlWithHeader(methodName, headerMap, parameterMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                HHSoftLogUtils.i(TAG,"postRequestUrlAsync==onResponse=="+response);
                try {
                    String result = response.body();
                    HHSoftLogUtils.i(TAG, "postRequestUrlAsync==onResponse==" + result);
                    successCallBack.accept(call, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    failureCallBack.accept(call, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return call;
    }

    public Call<String> postRequestMultipartURLWithHeadersAsync(String ip, String methodName, Map<String, RequestBody> parameterMap, List<MultipartBody.Part> files, Map<String, String> headerMap, final BiConsumer<Call<String>, String> successCallBack, final BiConsumer<Call<String>, Throwable> failureCallBack) {
        Call<String> call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).callPostRequestMultipartURLWithHeader(methodName, headerMap, parameterMap, files);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                HHSoftLogUtils.i(TAG,"postRequestUrlAsync==onResponse=="+response);
                try {
                    String result = response.body();
                    HHSoftLogUtils.i(TAG, "postRequestMultipartURLWithHeadersAsync==onResponse==" + result);
                    successCallBack.accept(call, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    HHSoftLogUtils.i(TAG, "postRequestMultipartURLWithHeadersAsync==onResponse==error==" + Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HHSoftLogUtils.i(TAG, "postRequestMultipartURLWithHeadersAsync==onFailure==" + Log.getStackTraceString(t));
                try {
                    failureCallBack.accept(call, t);
                } catch (Exception e) {
                    e.printStackTrace();
                    HHSoftLogUtils.i(TAG, "postRequestMultipartURLWithHeadersAsync==onFailure==error==" + Log.getStackTraceString(e));
                }
            }
        });
        return call;
    }

    /**
     * 网络请求：post异步请求,该方法进行了默认的加密解密
     *
     * @param ip              IP地址
     * @param methodName      接口名字
     * @param parameterMap    参数Map
     * @param headerMap       请求头map
     * @param successCallBack 请求成功回调
     * @param failureCallBack 请求失败回调
     * @return
     */
    public Call<String> defaultPostRequestFormURLAsync(String ip, String methodName, Map<String, String> parameterMap, Map<String, String> headerMap, final BiConsumer<Call<String>, String> successCallBack, final BiConsumer<Call<String>, Throwable> failureCallBack) {
        String parameterStr = getDefaultParaParameterByMap(parameterMap, "");
        return defaultPostRequestFormURLAsync("", ip, methodName, parameterStr, headerMap, successCallBack, failureCallBack);
    }

    /**
     * 网络请求：post异步请求
     *
     * @param ip              IP地址
     * @param methodName      接口名字
     * @param parameterStr    参数(json字符串)
     * @param headerMap       请求头map
     * @param successCallBack 请求成功回调
     * @param failureCallBack 请求失败回调
     * @return
     */
    public Call<String> defaultPostRequestFormURLAsync(final String aesKey, final String ip, final String methodName, String parameterStr, Map<String, String> headerMap, final BiConsumer<Call<String>, String> successCallBack, final BiConsumer<Call<String>, Throwable> failureCallBack) {
        Call<String> call;
        if (headerMap != null && headerMap.size() > 0) {
            call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).defaultCallPostRequestFormUrlWithHeader(methodName, headerMap, parameterStr);
        } else {
            call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).defaultCallPostRequestFormUrl(methodName, parameterStr);
        }
        if (call == null) {
            try {
                failureCallBack.accept(null, new Throwable("retrofit create fail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "defaultPostRequestFormURLAsync==onResponse==" + ip + methodName + "==" + response.code());
                    try {
                        if (response.isSuccessful()) {
                            String result = TextUtils.isEmpty(aesKey) ? HHSoftEncryptUtils.decryptAES(response.body()) : HHSoftEncryptUtils.decryptAESWithKey(response.body(), aesKey);
                            HHSoftLogUtils.i(TAG, "defaultPostRequestFormURLAsync==success==" + ip + methodName + "==" + result);
                            successCallBack.accept(call, result);
                        } else {
                            failureCallBack.accept(call, new HttpException(response));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        HHSoftLogUtils.i(TAG, "defaultPostRequestFormURLAsync==error==" + Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    HHSoftLogUtils.i(TAG, "defaultPostRequestFormURLAsync==onFailure==" + Log.getStackTraceString(t));
                    try {
                        failureCallBack.accept(call, t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        HHSoftLogUtils.i(TAG, "defaultPostRequestFormURLAsync==error==" + Log.getStackTraceString(e));
                    }
                }
            });
        }
        return call;
    }


    /**
     * 网络请求：post异步请求 图文
     *
     * @param ip              IP地址
     * @param methodName      方法名
     * @param parameterMap    请求参数
     * @param fileList        文件集合
     * @param headerMap       请求头
     * @param successCallBack 成功回调
     * @param failureCallBack 失败回调，返回的Call会为空
     * @return
     */
    public Call<String> defaultPostRequestFileURLAsync(String ip, String methodName, Map<String, String> parameterMap, List<MultipartBody.Part> fileList, Map<String, String> headerMap, final BiConsumer<Call<String>, String> successCallBack, final BiConsumer<Call<String>, Throwable> failureCallBack) {
        String parameterStr = getDefaultParaParameterByMap(parameterMap, "");
        return defaultPostRequestFileURLAsync("", ip, methodName, parameterStr, fileList, headerMap, successCallBack, failureCallBack);
    }

    /**
     * 网络请求：post异步请求 图文，请求参数需要进行Json拼接时，使用该方法
     *
     * @param ip              IP地址
     * @param methodName      方法名
     * @param parameterStr    请求参数需要进行Json拼接时，拼接的json字符串
     * @param fileList        文件集合
     * @param headerMap       请求头
     * @param successCallBack 成功回调
     * @param failureCallBack 失败回调，返回的Call会为空
     * @return
     */
    public Call<String> defaultPostRequestFileURLAsync(final String aesKey, final String ip, final String methodName, String parameterStr, List<MultipartBody.Part> fileList, Map<String, String> headerMap, final BiConsumer<Call<String>, String> successCallBack, final BiConsumer<Call<String>, Throwable> failureCallBack) {
        Map<String, RequestBody> paramMap = new HashMap<>();
        paramMap.put("para", toRequestBody(parameterStr));
        Call<String> call;
        if (headerMap != null && headerMap.size() > 0) {
            call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).callPostRequestMultipartURLWithHeader(methodName, headerMap, paramMap, fileList);
        } else {
            call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).callPostRequestMultipartURL(methodName, paramMap, fileList);
        }
        if (call == null) {
            try {
                failureCallBack.accept(call, new Throwable("retrofit create fail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "defaultPostRequestFileURLAsync==onResponse==" + ip + methodName + "==" + response.code());
                    try {
                        if (response.isSuccessful()) {
                            String result = TextUtils.isEmpty(aesKey) ? HHSoftEncryptUtils.decryptAES(response.body()) : HHSoftEncryptUtils.decryptAESWithKey(response.body(), aesKey);
                            HHSoftLogUtils.i(TAG, "defaultPostRequestFileURLAsync==success==" + ip + methodName + "==" + result);
                            successCallBack.accept(call, result);
                        } else {
                            failureCallBack.accept(call, new HttpException(response));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        HHSoftLogUtils.i(TAG, "defaultPostRequestFileURLAsync==error==" + Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    HHSoftLogUtils.i(TAG, "defaultPostRequestFileURLAsync==onFailure==" + ip + methodName + "==" + Log.getStackTraceString(t));
                    try {
                        failureCallBack.accept(call, t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        HHSoftLogUtils.i(TAG, "defaultPostRequestFileURLAsync==error==" + Log.getStackTraceString(t));
                    }
                }
            });
        }
        return call;
    }

    public Call<String> getRequest(final String ip, final String methodName, Map<String, String> parameterMap, final Consumer<String> successCallBack, final Consumer<Throwable> failureCallBack) {
        Call call = HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).callGetRequest(methodName, parameterMap);
        if (call != null) {
            try {
                Response<String> resp = call.execute();
                if (resp.isSuccessful()) {
                    if (successCallBack != null) {
                        successCallBack.accept(resp.body());
                    }
                } else {
                    if (failureCallBack != null) {
                        failureCallBack.accept(new HttpException(resp));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return call;
    }


    public Call<String> obtainDefaultPostRequestFormURLCall(String ip, String methodName, Map<String, String> parameterMap, Map<String, String> headerMap) {
        String parameterStr = getDefaultParaParameterByMap(parameterMap, "");
        if (headerMap != null && headerMap.size() > 0) {
            return HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).defaultCallPostRequestFormUrlWithHeader(methodName, headerMap, parameterStr);
        } else {
            return HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).defaultCallPostRequestFormUrl(methodName, parameterStr);
        }
    }

    public Call<String> obtainDefaultPostRequestFormURLCall(String ip, String methodName, String parameterStr, Map<String, String> headerMap) {
        return HHSoftRetrofitManager.getInstance().create(ip, HHSoftRetrofitService.class).defaultCallPostRequestFormUrlWithHeader(methodName, headerMap, parameterStr);
    }

    /**
     * 获取请求接口参数para的值（json字符串）
     *
     * @param map
     * @return
     */
    public String getDefaultParaParameterByMap(Map<String, String> map, String aesKey) {
        if (map == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            HHSoftLogUtils.i(TAG, "HHSoftNetworkUtils==Parameter==" + entry.getKey() + "==" + entry.getValue());
            builder.append("\"" + entry.getKey() + "\":" + "\"" + HHSoftEncodeUtils.encodeBase64(entry.getValue()) + "\"" + ",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        String para = TextUtils.isEmpty(aesKey) ? HHSoftEncryptUtils.encryptAES(builder.toString()) : HHSoftEncryptUtils.encryptAESWithKey(builder.toString(), aesKey);
        HHSoftLogUtils.i(TAG, "HHSoftNetworkUtils==para==" + para);
        return para;
    }

    public static RequestBody toRequestBody(String paramStr) {
        return RequestBody.create(MediaType.parse("text/plain"), paramStr);
    }

    /**
     * 生成文件对象
     *
     * @param fileKey
     * @param filePath
     * @return
     */
    public static MultipartBody.Part toFileMultipartBodyPart(String fileKey, String filePath) {
        File file = new File(filePath);
        String contentType = "application/octet-stream";
        HHSoftFileUtils.FileType fileType = HHSoftFileUtils.fileTypeForImageData(filePath);
        if (HHSoftFileUtils.FileType.IMAGE_GIF == fileType) {
            contentType = "image/gif";
        } else if (HHSoftFileUtils.FileType.IMAGE_WEBP == fileType) {
            contentType = "image/webp";
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(fileKey, file.getName(), requestBody);
        return part;
    }

    private static class SingletonHolder {
        static HHSoftNetworkUtils mInstance = new HHSoftNetworkUtils();

        private SingletonHolder() {
        }
    }

    public static HHSoftNetworkUtils getInstance() {
        return SingletonHolder.mInstance;
    }
}
