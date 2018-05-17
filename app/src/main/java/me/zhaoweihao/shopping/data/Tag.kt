package me.zhaoweihao.shopping.data

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/10.
 */
class Tag {
    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {
        var id: Int = 0
        @SerializedName("t_name")
        var tagName: String? = null
        @SerializedName("t_description")
        var tagDescription: String? = null

    }


}