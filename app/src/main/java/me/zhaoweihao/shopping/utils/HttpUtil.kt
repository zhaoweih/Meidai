package me.zhaoweihao.shopping.utils

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Created by Zhaoweihao on 2018/1/6.
 */

object HttpUtil {

    private val JSON = MediaType.parse("application/json; charset=utf-8")

    fun sendOkHttpGetRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(address)
                .build()
        client.newCall(request).enqueue(callback)
    }

    fun sendOkHttpPostRequest(address: String, json: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
                .url(address)
                .post(body)
                .build()
        client.newCall(request).enqueue(callback)
    }
}
