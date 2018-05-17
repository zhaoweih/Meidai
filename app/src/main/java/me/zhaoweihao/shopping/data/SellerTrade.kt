package me.zhaoweihao.shopping.data

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/8.
 */
class SellerTrade {
    var code: Int = 0
    var message: String? = null
    var data: List<Data>? = null

    class Data {

        @SerializedName("id")
        var sellerTradeId: Int = 0
        @SerializedName("seller_id")
        var sellerId: Int = 0
        @SerializedName("purchaser_id")
        var purchaserId: Int = 0
        @SerializedName("g_price")
        var goodsPrice: Int = 0
        @SerializedName("t_buy_num")
        var buyNum: Int = 0
        @SerializedName("t_date")
        var date: String? = null
        @SerializedName("g_id")
        var goodsId: Int = 0
        @SerializedName("t_status")
        var status: Int = 0
        @SerializedName("t_address")
        var address: String? = null
        @SerializedName("t_update")
        var update: String? = null
        @SerializedName("t_commented")
        var commented: Int = 0
        @SerializedName("t_leave_message")
        var leaveMessage: String? = null
        @SerializedName("user_name")
        var userName: String? = null
        @SerializedName("user_address")
        var userAddress: String? = null
        @SerializedName("user_address_more")
        var userAddressMore: String? = null
        @SerializedName("g_name")
        var goodsName: String? = null
        @SerializedName("g_picture")
        var goodsPicture: String? = null
        @SerializedName("c_content")
        var commentsContent: String? = null
        @SerializedName("c_date")
        var commentsDate: String? = null
        @SerializedName("c_secret")
        var commentsSecret: Int? = null

    }
}