package me.zhaoweihao.shopping.gson

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/10.
 */
class PublishGoods {
    /**
     * 发布商品
     * @param {string} token
     * @param {string} tag 标签
     * @param {string} description 描述
     * @param {string} keyword 关键词
     * @param {string} price 价格
     * @param {string} name 商品名字
     * @param {number} seller_id 卖家id
     * @param {string} address 卖家地址
     * @param {string} pictures 展示图片数组 ["/assets/images/upload/upload37347.jpeg","/assets/images/upload/upload37347.jpeg"]
     * @param {number} num 商品数量
     */

    var token: String? = null
    var tag: String? = null
    var description:String? = null
    var keyword:String? = null
    var price:String? = null
    var name:String? = null
    @SerializedName("seller_id")
    var sellerId:Int = 0
    var address:String? = null
    var pictures:Array<String?> ? = null
    var num:Int= 0
}