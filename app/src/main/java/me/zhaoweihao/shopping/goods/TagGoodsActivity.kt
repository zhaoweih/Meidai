package me.zhaoweihao.shopping.goods

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_tag_goods.*
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.adapter.GoodsAdapter
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.database.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class TagGoodsActivity : AppCompatActivity() {

    private val TAG_CODE = 4

    private val TAG = "TagGoodsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_goods)

        val intent = intent
        var tagName = intent.getStringExtra("tagName")

        setSupportActionBar(toolbar)

        supportActionBar!!.title = tagName

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val url = Constant.baseUrl + "get_goods_by_tag?tagName=$tagName&begin=0&num=30"

        HttpUtil.sendOkHttpGetRequest(url, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {

                val responseData = response!!.body()!!.string()

                val goods = Utility.handleGoodsResponse(responseData)

                if (goods!!.code == 200) {
                    val data = goods.data
                    runOnUiThread {
                        rv_my_goods.layoutManager = LinearLayoutManager(this@TagGoodsActivity)
                        val adapter = GoodsAdapter(data!!, TAG_CODE)
                        rv_my_goods.adapter = adapter
                    }

                } else {
                    Log.d(TAG, "code is not 200")
                }

                Log.d(TAG, responseData)

            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
