package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/9.
 */
class Update {

    /**
     *
     * @param {*} token
     * @param {*} id
     * @param {*} user_name
     * @param {*} user_sex
     * @param {*} user_phone
     * @param {*} user_avator 头像
     * @param { Array } user_address 所在区域 数组的方式 ["广东","惠州","博罗"]
     * @param { string } user_address_more 地址补充
     */

    var token: String? = null
    var id: Int? = 0
    @SerializedName("user_name")
    var userName: String? = null
    @SerializedName("user_sex")
    var userSex: Int? = 0
    @SerializedName("user_phone")
    var userPhone: String? = null
    @SerializedName("user_avator")
    var userAvator: String? = null
    @SerializedName("user_address")
    var userAddress: Array<String>? = null
    @SerializedName("user_address_more")
    var userAddressMore: String? = null
}