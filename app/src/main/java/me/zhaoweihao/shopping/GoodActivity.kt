package me.zhaoweihao.shopping

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_good.*
import com.daimajia.slider.library.SliderTypes.TextSliderView
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Trade
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException


class GoodActivity : AppCompatActivity() {

    val TAG = "GoodActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_good)

        val intent = intent

        val goodId = intent.getIntExtra("id", 0)
        val goodTag = intent.getStringExtra("tag")
        val goodDescription = intent.getStringExtra("description")
        val goodPrice = intent.getIntExtra("price", 0)
        val goodName = intent.getStringExtra("name")
        val goodSellerId = intent.getIntExtra("sellerId", 0)
        val goodAddress = intent.getStringExtra("address")
        val goodPictures = intent.getStringExtra("pictures")
        val goodDate = intent.getStringExtra("date")
        val goodNum = intent.getIntExtra("num", 0)
        val goodSellCount = intent.getIntExtra("sellCount", 0)
        val goodStatus = intent.getIntExtra("status", 0)
        val goodKeyword = intent.getStringExtra("keyword")
        val goodLevel = intent.getStringExtra("level")
        val userName = intent.getStringExtra("userName")
        val userAvator = intent.getStringExtra("userAvator")


        val leaveMessage = et_leave_message.text.toString()

        tv_good_name.text = goodName
        tv_good_price.text = goodPrice.toString()
        tv_good_address.text = goodAddress
        tv_good_sell.text = goodSellCount.toString()
        tv_good_left.text = goodNum.toString()
        tv_good_seller_name.text = userName
        tv_good_description.text = goodDescription

        val imageUrls = Gson().fromJson(goodPictures, Array<String>::class.java)

        for (url in imageUrls) {
            val textSliderView = TextSliderView(this)
            textSliderView.image(Constant.baseUrl + url)

            iv_good.addSlider(textSliderView)
        }

        iv_good.stopAutoCycle()

        btn_buy.setOnClickListener {
            val intent = Intent(this, BuyActivity::class.java)
            val buyNum = et_buy_num.text.toString().toInt()
            val totalPrice = goodPrice * buyNum
            intent.putExtra("id", goodId)
            intent.putExtra("sellerId", goodSellerId)
            intent.putExtra("singlePrice", goodPrice)
            intent.putExtra("totalPrice", totalPrice)
            intent.putExtra("buyNum", buyNum)
            intent.putExtra("leaveMessage", leaveMessage)
            intent.putExtra("goodName", goodName)
            startActivity(intent)

        }

        val find = DataSupport.find(UserInfo::class.java, 1)

        if (find != null) {
            val userId = find.userId
            val isFollowUrl = Constant.baseUrl + "isfollow?follow=$userId&leader=$goodSellerId"

            HttpUtil.sendOkHttpGetRequest(isFollowUrl, object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseData = response!!.body()!!.string()
                    val isFollow = Utility.handleIsFollowResponse(responseData)
                    runOnUiThread {
                        if (isFollow!!.data!!) {
                            btn_follow.visibility = View.GONE
                            btn_isfollowed.visibility = View.VISIBLE
                        } else {
                            btn_follow.visibility = View.VISIBLE
                            btn_isfollowed.visibility = View.GONE
                        }
                    }
                    Log.d(TAG, responseData)
                }

            })

            val isCollectedUrl = Constant.baseUrl + "iscollected?id=$userId&gid=$goodId"

            HttpUtil.sendOkHttpGetRequest(isCollectedUrl, object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseData = response!!.body()!!.string()
                    val isCollected = Utility.handleIsFollowResponse(responseData)
                    runOnUiThread {
                        if (isCollected!!.data!!) {
                            btn_collect.visibility = View.GONE
                            btn_iscollected.visibility = View.VISIBLE
                        } else {
                            btn_collect.visibility = View.VISIBLE
                            btn_iscollected.visibility = View.GONE
                        }
                    }
                    Log.d(TAG, responseData)
                }

            })

            btn_follow.setOnClickListener {

                val setFollowUrl = Constant.baseUrl + "follow?follow=$userId&leader=$goodSellerId"

                HttpUtil.sendOkHttpGetRequest(setFollowUrl, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleFollowResponse(responseData)
                        if (returnData!!.code == 200) {
                            runOnUiThread {
                                btn_follow.visibility = View.GONE
                                btn_isfollowed.visibility = View.VISIBLE
                            }
                        }
                        Log.d(TAG, responseData)
                    }

                })

            }

            btn_isfollowed.setOnClickListener {
                val cancelFollowUrl = Constant.baseUrl + "cancel_follow?follow=$userId&leader=$goodSellerId"
                HttpUtil.sendOkHttpGetRequest(cancelFollowUrl, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleIsFollowResponse(responseData)
                        if ( returnData!!.code == 200) {
                            runOnUiThread {
                                btn_isfollowed.visibility = View.GONE
                                btn_follow.visibility = View.VISIBLE
                            }
                        }
                        Log.d(TAG, responseData)
                    }

                })
            }

            btn_collect.setOnClickListener {
                val setCollectUrl = Constant.baseUrl + "collect?id=$userId&gid=$goodId"
                HttpUtil.sendOkHttpGetRequest(setCollectUrl, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleFollowResponse(responseData)
                        if (returnData!!.code == 200) {
                            runOnUiThread {
                                btn_collect.visibility = View.GONE
                                btn_iscollected.visibility = View.VISIBLE
                            }
                        }
                        Log.d(TAG, responseData)
                    }

                })
            }

            btn_iscollected.setOnClickListener {
                val cancelCollectUrl = Constant.baseUrl + "cancel_collected?id=$userId&gid=$goodId"

                HttpUtil.sendOkHttpGetRequest(cancelCollectUrl, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleIsFollowResponse(responseData)
                        if (returnData!!.code == 200) {
                            runOnUiThread {
                                btn_iscollected.visibility = View.GONE
                                btn_collect.visibility = View.VISIBLE
                            }
                        }
                        Log.d(TAG, responseData)
                    }

                })
            }
        } else {
            Log.d(TAG, "find is null")
        }


    }

}
