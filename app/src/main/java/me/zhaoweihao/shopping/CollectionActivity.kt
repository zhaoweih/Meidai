package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_good.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class CollectionActivity : AppCompatActivity() {

    val TAG = "CollectActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val find = DataSupport.find(UserInfo::class.java, 1)

        if ( find != null) {
            val userId = find.userId
            val url = Constant.baseUrl+"get_collection?id=$userId&begin=0&num=6"

            HttpUtil.sendOkHttpGetRequest(url, object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseData = response!!.body()!!.string()
                    val collections = Utility.handleCollectionsResponse(responseData)

                    Log.d(TAG, responseData)
                }

            })

        } else {
            Log.d(TAG,"find is null")
        }
    }
}
