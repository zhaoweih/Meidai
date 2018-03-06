package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_buy.*
import me.zhaoweihao.shopping.gson.Trade
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import okhttp3.Call
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class BuyActivity : AppCompatActivity() {

    val TAG = "BuyActivity"

    private var token: String? = null
    private var userId: Int = 0
    private var address: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        title = "确认订单"

        val intent = intent

        val goodId = intent.getIntExtra("id", 0)
        val goodSellerId = intent.getIntExtra("sellerId", 0)
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        val buyNum = intent.getIntExtra("buyNum", 0)
        val leaveMessage = intent.getStringExtra("leaveMessage")
        val goodName = intent.getStringExtra("goodName")
        val singlePrice = intent.getIntExtra("singlePrice", 0)

        tv_buy_price.text = singlePrice.toString()
        tv_buy_name.text = goodName
        tv_buy_num.text = buyNum.toString()
        tv_buy_total_price.text = totalPrice.toString()
        tv_buy_leave_message.text = leaveMessage

        val find = DataSupport.find(UserInfo::class.java, 1)

        if (find != null) {
            token = find.userToken
            userId = find.userId!!
            address = find.userAddress

            tv_buy_address.text = address

            btn_buy_sure.setOnClickListener {


                Log.d(TAG, find.userToken)
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

                val trade = Trade()
                trade.token = token
                trade.id = goodId
                trade.sellerId = goodSellerId
                trade.purchaserId = userId
                trade.price = singlePrice
                trade.buyNum = buyNum
                trade.address = address
                trade.leaveMessage = leaveMessage


                val jsonObject = Gson().toJson(trade)

                val url = "http://meidai.maocanhua.cn/trade"

                Log.d(TAG, jsonObject)

                HttpUtil.sendOkHttpPostRequest(url, jsonObject, object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {

                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()!!.string()
                        Log.d(TAG, responseData)
//                        val user = Utility.handleUserResponse(responseData)
//                        if (user!!.code == 200) {
//
//                        } else {
//                            Log.d(TAG, "failed")
//                        }
                    }
                })

            }
        } else {
            Log.d(TAG, "find is null")
        }

        btn_buy_cancel.setOnClickListener { finish() }


    }
}
