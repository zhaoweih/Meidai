package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/7.
 */
class Concerns {

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
        var concernsId: Int = 0
        @SerializedName("c_leader")
        var leader: Int = 0
        @SerializedName("c_follow")
        var follow: Int = 0
        @SerializedName("c_date")
        var date: String? = null
    }
}