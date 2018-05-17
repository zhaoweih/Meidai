package me.zhaoweihao.shopping.data

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/13.
 */
class GetComment {

    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {
        var id: Int = 0
        @SerializedName("c_content")
        var content: String? = null
        @SerializedName("c_date")
        var date: String? = null
        @SerializedName("c_secret")
        var secret: Int = 0
        @SerializedName("c_star")
        var star: Int = 0
        @SerializedName("t_id")
        var tradeId: Int = 0
        @SerializedName("user_name")
        var userName: String? = null
    }


}