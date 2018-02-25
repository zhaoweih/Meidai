package me.zhaoweihao.hnuplus
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class HomeFragment : Fragment() {

    val TAG = "HomeFragment"

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home,
                container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestWeather()

    }

    private fun requestWeather() {

        val url = "http://meidai.maocanhua.cn/get_goods_by_tag?tagName=%E8%A1%A3%E6%9C%8D&begin=0&num=10"
        HttpUtil.sendOkHttpRequest(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()!!.string()
                Log.d(TAG,responseData)
                val goods = Utility.handleGoodsResponse(responseData)

                if (goods != null && goods.code == 200) {
                    val data = goods.data
                    if (data != null) {
                        for ( item in data) {
                            Log.d(TAG,item.name+" "+ item.pictures)
                        }
                    }
                } else {
                    Log.d(TAG,"failed")
                }
            }
        })
    }

}