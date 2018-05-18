package me.zhaoweihao.shopping.utils

import android.util.Log
import okhttp3.*
import java.io.File

/**
 * Created by Zhaoweihao on 2018/1/6.
 */

object HttpUtil {


    val TAG = "HttpUtil"

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

    fun sendOkHttpPostFileRequest(address: String, file : File, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        Log.d(TAG,address)
        val MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg")

        val req = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("userfile", "profile.jpeg", RequestBody.create(MEDIA_TYPE_JPEG, file)).build()

        val request = Request.Builder()
                .url(address)
                .post(req)
                .build()
        client.newCall(request).enqueue(callback)
    }
}
