package com.huahansoft.hhsoftlibrarykit.retrofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @类说明 Retrofit生成接口
 * 一、FormUrlEncoded  不需要上传文件
 * 二、Multipart  图文上传：每个键值对都需要用@Part注解键名字
 * @作者 hhsoft
 * @创建日期 2019/8/2 17:38
 */
public interface HHSoftRetrofitService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("Range") String range, @Url String url);


    @GET
    Call<String> callGetRequest(@Url String url, @QueryMap Map<String,String> paramMap);

    @FormUrlEncoded
    @POST
    Call<String> defaultCallPostRequestFormUrl(@Url String url, @Field("para") String json);

    @FormUrlEncoded
    @POST
    Call<String> defaultCallPostRequestFormUrlWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @Field("para") String json);





    @FormUrlEncoded
    @POST
    Call<String> callPostRequestFormUrl(@Url String url, @FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST
    Call<String> callPostRequestFormUrlWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> paramMap);




    @Multipart
    @POST
    Call<String> callPostRequestMultipartURL(@Url String url, @PartMap Map<String, RequestBody> paramMap, @Part List<MultipartBody.Part> fileMap);

    @Multipart
    @POST
    Call<String> callPostRequestMultipartURLWithHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @PartMap Map<String, RequestBody> paramMap, @Part List<MultipartBody.Part> fileMap);
}
