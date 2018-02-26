package me.zhaoweihao.shopping.litepal

import org.litepal.crud.DataSupport

/**
 * Created by Administrator on 2018/2/26.
 */
class UserInfo : DataSupport() {
    //            "id":42,
//            "user_name":"赵威豪",
//            "user_phone":"18320665077",
//            "user_sex":0,
//            "user_avator":"/assets/images/upload/151913486572917.jpeg",
//            "user_rank":10,
//            "user_status":1,
//            "user_coin":8816,
//            "user_register_date":"2018-02-15",
//            "user_authenticated":1,
//            "user_real_name":"赵威豪",
//            "user_real_identity_card":"44132219960522601x",
//            "user_address":"[\"广东\",\"惠州\",\"博罗县\"]",
//            "user_address_more":"龙溪镇88号",
//            "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjo0MiwidXNlcl9uYW1lIjoi6LW15aiB6LGqIiwidXNlcl9waG9uZSI6IjE4MzIwNjY1MDc3IiwidXNlcl9zZXgiOjAsInVzZXJfYXZhdG9yIjoiL2Fzc2V0cy9pbWFnZXMvdXBsb2FkL2F2YXRvci5wbmciLCJ1c2VyX2FkZHJlc3MiOiLmnKrloavlhpkiLCJ1c2VyX3"

    var userId: Int? = 0
    var userName: String? = null
    var userPhone: String? = null
    var userSex: Int? = null
    var userAvator: String? = null
    var userRank: Int? = 0
    var userStatus: Int? = 0
    var userCoin: Int? = 0
    var userRegisterDate: String? = null
    var userAuthenticated: Int? = 0
    var userRealName: String? = null
    var userRealIdentityCard: String? = null
    var userAddress: String? = null
    var userAddressMore: String? = null
    var userToken: String? = null

}