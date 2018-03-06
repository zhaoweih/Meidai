package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/6.
 */
class Trade {
    /**
     * 进行交易，即点击购买生成订单
     * @param {*} token
     * @param {*} id 商品id
     * @param {*} seller_id 卖家id
     * @param {*} purchaser_id 买家id
     * @param {*} g_price 价格
     * @param {*} buy_num 购买数量
     * @param {*} t_address 购买地址 （用户的地址）
     */
    var token: String? = null
    var id: Int = 0
    @SerializedName("seller_id")
    var sellerId: Int = 0
    @SerializedName("purchaser_id")
    var purchaserId: Int = 0
    @SerializedName("g_price")
    var price: Int = 0
    @SerializedName("buy_num")
    var buyNum: Int = 0
    @SerializedName("t_address")
    var address: String? = null
    @SerializedName("t_leave_message")
    var leaveMessage: String? = null
}