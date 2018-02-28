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
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import me.zhaoweihao.shopping.adapter.GoodsAdapter


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
        requestGoodsByTag("衣服", rv_cloths)
        requestGoodsByTag("化妆品", rv_cosmetic)
        requestGoodsByTag("日用品", rv_daily)

    }

    private fun requestGoodsByTag(tagName: String?, recyclerView: RecyclerView?) {

        val url = "http://meidai.maocanhua.cn/get_goods_by_tag?tagName=$tagName&begin=0&num=6"
        HttpUtil.sendOkHttpGetRequest(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()!!.string()
                Log.d(TAG, responseData)
                val goods = Utility.handleGoodsResponse(responseData)
                if (goods!!.code == 200) {
                    val data = goods.data
                    activity.runOnUiThread({
                        recyclerView!!.isNestedScrollingEnabled = false
                        recyclerView.layoutManager = GridLayoutManager(activity, 3)
                        val adapter = GoodsAdapter(data!!)
                        recyclerView.adapter = adapter
                    })
                } else {
                    Log.d(TAG, "failed")
                }
            }
        })
    }

}