package me.zhaoweihao.shopping.data

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/14.
 */
class GetLog {
    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {
        var id: Int = 0
        @SerializedName("log_from")
        var logFrom: Int = 0
        @SerializedName("log_to")
        var logTo: Int = 0
        @SerializedName("log_html")
        var logHtml:String? = null
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