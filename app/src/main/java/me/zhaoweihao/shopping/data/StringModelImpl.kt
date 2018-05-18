package me.zhaoweihao.shopping.data

import android.content.Context

import java.io.IOException

import me.zhaoweihao.shopping.utils.HttpUtil
import okhttp3.Call

/**
 * Created by zhao weihao 2018/4/5.
 */

class StringModelImpl(private val context: Context) {

    fun load(url: String, listener: OnStringListener) {
        HttpUtil.sendOkHttpGetRequest(url, object : okhttp3.Callback {

            override fun onFailure(call: Call, e: IOException) {
                listener.onError(e.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: okhttp3.Response) {
                //网络请求成功
                val s = response.body()!!.string()
                listener.onSuccess(s)
            }
        })
    }
}
