package me.zhaoweihao.shopping.data

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/12.
 */
class IsComment {
    var code : Int? = 0
    var message : String? = null
    var data : Data? = null

    class Data {
        var id: Int = 0
        @SerializedName("t_id")
        var tradeId: Int = 0
        @SerializedName("c_date")
        var commentDate: String? = null
        @SerializedName("c_content")
        var commentContent: String? = null
        @SerializedName("c_star")
        var commentStar: Int = 0
        @SerializedName("g_id")
        var goodsId: Int = 0
        @SerializedName("c_secret")
        var commentSecret: Int = 0
    }
}