package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2018/2/26.
 */
class User {
    var code: Int = 0
    var message: String? = null
    @SerializedName("data")
    var userData: UserData? = null

    class UserData {
        @SerializedName("user")
        var userInfo: UserInfo? = null
        @SerializedName("token")
        var userToken: String? = null

        class UserInfo {
            @SerializedName("id")
            var userId: Int = 0
            @SerializedName("user_name")
            var userName: String? = null
            @SerializedName("user_phone")
            var userPhone: String? = null
            @SerializedName("user_sex")
            var userSex: Int = 0
            @SerializedName("user_avator")
            var userAvator: String? = null
            @SerializedName("user_rank")
            var userRank: Int = 0
            @SerializedName("user_status")
            var userStatus: Int = 0
            @SerializedName("user_coin")
            var userCoin: Int = 0
            @SerializedName("user_register_date")
            var userRegisterDate: String? = null
            @SerializedName("user_authenticated")
            var userAuthenticated: Int = 0
            @SerializedName("user_real_name")
            var userRealName: String? = null
            @SerializedName("user_real_identity_card")
            var userRealIdentityCard: String? = null
            @SerializedName("user_address")
            var userAddress: String? = null
            @SerializedName("user_address_more")
            var userAddressMore: String? = null
        }

    }
}