/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huahansoft.hhsoftlibrarykit.retrofit.gson;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftLogUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final String TAG = "xiao";
    //    private static final String TAG="GsonRequestBodyConverter";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        HHSoftLogUtils.i(TAG, "RequestBody==convert==" + value);
//        if (value instanceof Map) {
//            String jsonString = null;
//            Map<String, String> map = (Map<String, String>) value;
//            map.put("v","1.0.1");
//            jsonString=getPostRequestParamString(map);
//            HHSoftLogUtils.i(TAG, "RequestBody==param:" + jsonString);
//            return RequestBody.create(MEDIA_TYPE, jsonString);
//        }else {
//            Buffer buffer = new Buffer();
//            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
//            JsonWriter jsonWriter = gson.newJsonWriter(writer);
//            adapter.write(jsonWriter, value);
//            jsonWriter.close();
//            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
//        }
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
