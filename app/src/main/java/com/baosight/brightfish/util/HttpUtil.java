package com.baosight.brightfish.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/1/12.
 */

public class HttpUtil {
    public static void sendOkHttpRequestGet(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();

        client.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequestPost(String address, RequestBody requestBody,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }


}
