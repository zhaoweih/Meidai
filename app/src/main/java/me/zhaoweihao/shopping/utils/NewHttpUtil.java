package me.zhaoweihao.shopping.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NewHttpUtil {
    public static void sendGetRequest(String address,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
