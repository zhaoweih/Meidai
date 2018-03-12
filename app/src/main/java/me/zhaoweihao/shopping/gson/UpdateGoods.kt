package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/12.
 */
class UpdateGoods {

    /**
     *更新商品信息，参数类型如上
     * @param {*} token
     * @param {*} tag
     * @param {*} description
     * @param {*} keyword
     * @param {*} price
     * @param {*} name
     * @param {*} id 商品id
     * @param {*} address
     * @param {*} pictures
     * @param {*} num
     */

    var token: String? = null
    var tag: String? = null
    var description:String? = null
    var keyword:String? = null
    var price:String? = null
    var name:String? = null
    @SerializedName("id")
    var goodId:Int = 0
    var address:String? = null
    var pictures:Array<String?> ? = null
    var num:Int= 0
}