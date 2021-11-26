package com.sjy.common.utils;

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
}
