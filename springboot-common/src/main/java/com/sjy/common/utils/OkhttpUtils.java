package com.sjy.common.utils;

import io.micrometer.core.instrument.util.StringUtils;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class OkhttpUtils {

    public static String get(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("cookie", "SESSION=M2E4ZTdlNTUtOGNkNi00NGQ2LWE2MTUtMjNiZTk0MDE5MWE4")
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * from
     * @param url
     * @param param
     * @return
     */
    public static String post(String url, HashMap<String,String> param){
        OkHttpClient okHttpClient = new OkHttpClient();

      /*  RequestBody body = new FormBody.Builder()
                .add("键", "值")
                .add("键", "值")
    .build();*/
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> strings = param.keySet();
        for (String string : strings) {
            builder.add(string,param.get(string));

        }
        RequestBody body= builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json
     * @param bodyJson
     * @param url
     * @return
     */
    public static String createBatch(String bodyJson, String url) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), bodyJson);
        //请求header的添加
        Headers.Builder headlerBuilder = new Headers.Builder();
        headlerBuilder.add("Content-Type","application/json");
        Headers headers = headlerBuilder.build();
        Request requestPost = new Request.Builder()
                .url(url)
                .post(body)
                .headers(headers)
                .build();
        Response response = null;
        String result = null;
        try {
            response = client.newCall(requestPost).execute();
            if (response.body() != null) {
                result = response.body().string();
            }
        } catch (IOException e) {
        }
        return result;
    }
}
