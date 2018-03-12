package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_my_goods.*
import kotlinx.android.synthetic.main.activity_search.*
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

class SearchActivity : AppCompatActivity() {

    val TAG = "SearchActivity"

    val SEARCHACTIVITY_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btn_search.setOnClickListener {
            val keyword = et_keyword.text.toString()

            val url = Constant.baseUrl+"get_search?keyword=$keyword&begin=0&num=6"

            HttpUtil.sendOkHttpGetRequest(url,object:Callback{
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseData = response!!.body()!!.string()

                    val goods = Utility.handleGoodsResponse(responseData)

                    if (goods!!.code == 200) {

                        val data = goods.data

                        runOnUiThread {
                            rv_search.layoutManager = GridLayoutManager(this@SearchActivity, 3)
                            val adapter = GoodsAdapter(data!!,SEARCHACTIVITY_CODE)
                            rv_search.adapter = adapter
                        }

                    } else {
                        Log.d(TAG,"code is not 200")
                    }

                    Log.d(TAG,responseData)
                }

            })
        }

    }
}
