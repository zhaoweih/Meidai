package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/11.
 */
class Notifications {
    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {
        @SerializedName("user_id")
        var userId: Int = 0
        @SerializedName("user_name")
        var userName: String? = null
        @SerializedName("user_rank")
        var userRank: Int = 0
        @SerializedName("user_sex")
        var userSex: Int = 0
        @SerializedName("user_avator")
        var userAvator: String? = null
        @SerializedName("id")
        var notificationId: Int = 0
        @SerializedName("log_from")
        var logFrom:Int = 0
        @SerializedName("log_to")
        var logTo: Int = 0
        @SerializedName("log_html")
        var logHtml: String? = null
        @SerializedName("log_text")
        var logText: String? = null
        @SerializedName("log_date")
        var logDate: String? = null
        @SerializedName("log_owner")
        var logOwner: Int = 0
        @SerializedName("log_other")
        var logOther: Int = 0
        @SerializedName("log_read")
        var logRead: Int = 0

    }
}