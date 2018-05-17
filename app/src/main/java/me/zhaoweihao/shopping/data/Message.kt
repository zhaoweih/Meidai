package me.zhaoweihao.shopping.data

/**
 * Created by por on 2018/3/14.
 */
class Message {
    /**
     * 发送信息
     * @param {*} token
     * @param {*} fromid 发送者的id
     * @param {*} toid 接受者的id
     * @param {*} text 信息（过滤html）
     * @param {*} html  信息（带有html）
     * @param {*} date 时间 格式 （YYYY-MM-DD HH:mm:ss）
     */

    var token: String? = null
    var fromid: Int = 0
    var toid: Int = 0
    var text: String? = null
    var html: String? = null
    var date: String? = null

}