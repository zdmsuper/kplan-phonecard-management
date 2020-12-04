package com.kplan.phonecard.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: silly
 * @Date: 2019/3/22 12:19
 * @Version 1.0
 * @Desc
 */
public class OkHttp3Utils {

    /**
     * post json数据
     *
     * @param url     请求地址
     * @param data    json数据
     * @param timeout 超时时间
     */
    public static String postJson(String url, String data, int timeout) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), data);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        //创建/Call
        Call call = client.newCall(request);
        //加入队列 异步操作
        Response response = call.execute();
        return response.body().string();
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent","Mozilla/5.0")
                .get()
                .build();
        //创建/Call
        Call call = client.newCall(request);
        //加入队列 异步操作
        Response response = call.execute();
        return response.body().string();
    }
}
