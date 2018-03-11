package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_notification.*
import me.zhaoweihao.shopping.adapter.NotificationsAdapter
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class NotificationActivity : AppCompatActivity() {

    val TAG = "NotificationActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val find = DataSupport.find(UserInfo::class.java, 1)

        if ( find != null) {
            val userId = find.userId

            val url = Constant.baseUrl + "get_notification?id=$userId&begin=0&num=6"

            HttpUtil.sendOkHttpGetRequest(url,object:Callback{
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseData = response!!.body()!!.string()
                    val notification = Utility.handleNotificationResponse(responseData)
                    if (notification!!.code == 200) {
                        val data = notification.data
                        runOnUiThread {
                            rv_notifications.layoutManager = GridLayoutManager(this@NotificationActivity, 1) as RecyclerView.LayoutManager?
                            val adapter = NotificationsAdapter(data!!)
                            rv_notifications.adapter = adapter
                        }
                    }
                    Log.d(TAG,responseData)
                }

            })
        } else{
            Log.d(TAG,"find is null")
        }
    }
}
