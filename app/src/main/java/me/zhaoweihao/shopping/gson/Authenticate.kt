package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/13.
 */
class Authenticate {
    /**
     * 设置用户实名
     * @param {string} token
     * @param {number} id 用户id
     * @param {string} real_name
     * @param {string} real_identity_card
     */
    var token: String? = null
    var id: Int = 0
    @SerializedName("real_name")
    var realName: String? = null
    @SerializedName("real_identity_card")
    var realIdentityCard: String? = null
}