package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/7.
 */
class Collections {
    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {
        @SerializedName("user_name")
        var userName: String? = null
        @SerializedName("user_rank")
        var userRank: Int = 0
        @SerializedName("user_sex")
        var userSex: Int = 0
        @SerializedName("user_avator")
        var userAvator: String? = null
        @SerializedName("id")
        var collectionsId: Int = 0
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
        @SerializedName("goods_id")
        var goodsId: Int = 0
        @SerializedName("c_date")
        var collectionsDate: String? = null

    }
}