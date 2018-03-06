package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/6.
 */
class Follow {
    var code: Int? = 0
    var message: String? = null
    @SerializedName("data")
    var returnData: ReturnData? = null

    class ReturnData {
        var id: Int = 0
        @SerializedName("goods_id")
        var goodsId: Int = 0
        @SerializedName("user_id")
        var userId: Int = 0
        @SerializedName("c_date")
        var date: String? = null
    }
}