package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_publish_goods.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.PublishGoods
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class PublishGoodsActivity : AppCompatActivity() {

    val TAG = "PublishGoodsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_goods)

        val find = DataSupport.find(UserInfo::class.java, 1)

        if ( find != null ) {
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

            btn_publish.setOnClickListener {

                val token = find.userToken
                val tag = "化妆品"
                val description = et_goods_description.text.toString()
                val keyword = et_goods_keyword.text.toString()
                val price = et_goods_price.text.toString()
                val name = et_goods_name.text.toString()
                val sellerId = find.userId
                val address = find.userAddress
                val otherStrings = arrayOf("/assets/images/upload/upload37347.jpeg", "/assets/images/upload/upload37347.jpeg", "/assets/images/upload/upload37347.jpeg")
                val pictures = null
                val num = et_goods_num.text.toString().toInt()

                val publishGoods = PublishGoods()
                publishGoods.token = token
                publishGoods.tag = tag
                publishGoods.description = description
                publishGoods.keyword = keyword
                publishGoods.price = price
                publishGoods.name = name
                publishGoods.sellerId = sellerId!!
                publishGoods.address = address
                publishGoods.pictures = otherStrings
                publishGoods.num = num

                val jsonObject = Gson().toJson(publishGoods)
                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl+"publish_goods"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject, object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        Log.d(TAG,responseData)
                    }

                })

            }

        } else {
            Log.d(TAG,"find is null")
        }
    }
}
