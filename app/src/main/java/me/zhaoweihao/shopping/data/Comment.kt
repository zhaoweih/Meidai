package me.zhaoweihao.shopping.data

import com.google.gson.annotations.SerializedName

/**
 * Created by por on 2018/3/12.
 */
class Comment {
    /**
     * 写入评论（暂时没有写删除评论，这些以后看看需不需要）
     * @param {*} token
     * @param {*} tid 订单id
     * @param {*} text 评论
     * @param {*} star 评价
     * @param {*} gid 商品id
     * @param {*} isSecret 是否匿名，默认匿名
     */

    var token: String? = null
    @SerializedName("tid")
    var tradeId: Int = 0
    var text: String? = null
    var star: Int = 0
    @SerializedName("gid")
    var goodId: Int = 0
    var isSecret: Int = 0
}