package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2018/2/25.
 */

class Goods {
    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {
        var id: Int = 0
        @SerializedName("g_tag")
        var tag: String? = null
        @SerializedName("g_description")
        var description: String? = null
        @SerializedName("g_price")
        var price: Int = 0
        @SerializedName("g_name")
        var name: String? = null
        @SerializedName("seller_id")
        var sellerId: Int = 0
        @SerializedName("g_address")
        var address: String? = null
        @SerializedName("g_picture")
        var pictures: String? = null
        @SerializedName("g_date")
        var date: String? = null
        @SerializedName("g_num")
        var num: Int = 0
        @SerializedName("g_sell_count")
        var sellCount: Int = 0
        @SerializedName("g_status")
        var status: Int = 0
        @SerializedName("g_keyword")
        var keyword: String? = null
        @SerializedName("g_level")
        var level: String? = null
        @SerializedName("user_name")
        var userName: String? = null
        @SerializedName("user_avator")
        var userAvator: String? = null
    }
}
