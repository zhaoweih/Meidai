package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_trade_purchaser.*
import me.zhaoweihao.shopping.adapter.PurchaserTradeAdapter
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class PurchaserTradeActivity : AppCompatActivity() {

    val TAG = "PurchaserTradeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trade_purchaser)

        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null) {
            val userId = find.userId
            val url = Constant.baseUrl+"get_trade_by_purchaser?id=$userId&begin=0&num=30"

            HttpUtil.sendOkHttpGetRequest(url, object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseData = response!!.body()!!.string()

                    val trades = Utility.handlePurchaserTradeResponse(responseData)
                    if (trades!!.code == 200) {
                        val data = trades.data
                        runOnUiThread {
                            rv_purchaser_trade.layoutManager = GridLayoutManager(this@PurchaserTradeActivity,1)
                            val adapter = PurchaserTradeAdapter(data!!)
                            rv_purchaser_trade.adapter = adapter
                        }
                    }
                    Log.d(TAG, responseData)
                }

            })

        } else {
            Log.d(TAG,"find is null")
        }
    }
}
