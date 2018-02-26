package me.zhaoweihao.shopping.utils


import com.google.gson.Gson
import me.zhaoweihao.shopping.gson.Goods
import me.zhaoweihao.shopping.gson.User


/**
 * Created by Zhaoweihao on 2018/1/6.
 */

object Utility {
    fun handleGoodsResponse(response: String): Goods? {
        try {
            val gson = Gson()
            return gson.fromJson(response, Goods::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun handleUserResponse(response: String): User? {
        try {
            val gson = Gson()
            return gson.fromJson(response, User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}
