package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_my_goods.*
import me.zhaoweihao.shopping.adapter.GoodsAdapter
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class MyGoodsActivity : AppCompatActivity() {

    val TAG = "MyGoodsActivity"

    val MYGOODSACTIVITY_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_goods)

        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null ) {
            val userId = find.userId

            val url = Constant.baseUrl + "get_goods_by_seller?id=$userId&begin=0&num=6"

            HttpUtil.sendOkHttpGetRequest(url, object: Callback{
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {

                    val responseData = response!!.body()!!.string()

                    val goods = Utility.handleGoodsResponse(responseData)

                    if ( goods!!.code == 200) {
                        val data = goods.data
                        runOnUiThread {
                            rv_my_goods.layoutManager = GridLayoutManager(this@MyGoodsActivity, 3)
                            val adapter = GoodsAdapter(data!!,MYGOODSACTIVITY_CODE)
                            rv_my_goods.adapter = adapter
                        }

                    } else {
                        Log.d(TAG,"code is not 200")
                    }

                    Log.d(TAG,responseData)

                }

            })
        } else {
            Log.d(TAG,"find is null")
        }
    }
}
