package me.zhaoweihao.shopping.goods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_good.*
import com.daimajia.slider.library.SliderTypes.TextSliderView
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.adapter.CommentsAdapter
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.database.UserInfo
import me.zhaoweihao.shopping.trade.BuyActivity
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.progressDialog
import org.litepal.crud.DataSupport
import java.io.IOException


class GoodActivity : AppCompatActivity() {

    val TAG = "GoodActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_good)

        val intent = intent

        val goodId = intent.getIntExtra("id", 0)
        val url = Constant.baseUrl + "get_goods_by_id?id=$goodId"


        iv_back.setOnClickListener {
            onBackPressed()
        }

        HttpUtil.sendOkHttpGetRequest(url, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val responseData = response!!.body()!!.string()

                val good = Utility.handleGoodResponse(responseData)
                if (good!!.code == 200) {
                    val data = good.data
                    val goodTag = data!!.tag
                    val goodDescription = data.description
                    val goodPrice = data.price
                    val goodName = data.name
                    val goodSellerId = data.sellerId
                    val goodAddress = data.address
                    val goodPictures = data.pictures
                    val goodDate = data.date
                    val goodNum = data.num
                    val goodSellCount = data.sellCount
                    val goodStatus = data.status
                    val goodKeyword = data.keyword
                    val goodLevel = data.level
                    val userName = data.userName
                    val userAvator = data.userAvator

                    runOnUiThread {


//                        val leaveMessage = et_leave_message.text.toString()

                        tv_good_name.text = goodName
//                        tv_good_price.text = goodPrice.toString()
                        val index = goodAddress!!.indexOf('-')
                        val address: String? = if (index == -1) null else goodAddress.substring(index + 1, index + 3)
                        tv_good_address.text = address
                        tv_good_sell.text = goodSellCount.toString()
                        tv_good_left.text = goodNum.toString()
                        tv_good_seller_name.text = userName
                        tv_good_description.text = goodDescription

                        val imageUrls = Gson().fromJson(goodPictures, Array<String>::class.java)

                        for (url in imageUrls) {
                            val textSliderView = TextSliderView(this@GoodActivity)
                            textSliderView.image(Constant.baseUrl + url)

                            iv_good.addSlider(textSliderView)
                        }

                        iv_good.stopAutoCycle()

                        btn_buy.setOnClickListener {
                            val intent = Intent(this@GoodActivity, BuyActivity::class.java)
//                            val buyNum = et_buy_num.text.toString().toInt()
//                            val totalPrice = goodPrice * buyNum
                            intent.putExtra("id", goodId)
                            intent.putExtra("sellerId", goodSellerId)
                            intent.putExtra("singlePrice", goodPrice)
//                            intent.putExtra("totalPrice", totalPrice)
//                            intent.putExtra("buyNum", buyNum)
//                            intent.putExtra("leaveMessage", leaveMessage)
                            intent.putExtra("goodName", goodName)
                            startActivity(intent)

                        }

                        val find = DataSupport.findFirst(UserInfo::class.java)

                        if (find != null) {
                            val userId = find.userId

                            if (userId == goodSellerId) {
                                btn_buy.visibility = View.GONE
                                tv_isseller.visibility = View.VISIBLE
                                btn_update.visibility = View.VISIBLE

                                btn_update.setOnClickListener {

                                    /**
                                     *更新商品信息，参数类型如上
                                     * @param {*} token
                                     * @param {*} tag
                                     * @param {*} description
                                     * @param {*} keyword
                                     * @param {*} price
                                     * @param {*} name
                                     * @param {*} id 商品id
                                     * @param {*} address
                                     * @param {*} pictures
                                     * @param {*} num
                                     */

                                    val intent = Intent(this@GoodActivity, UpdateGoodsActivity::class.java)
                                    intent.putExtra("goodId",goodId)
                                    intent.putExtra("tag",goodTag)
                                    intent.putExtra("description",goodDescription)
                                    intent.putExtra("keyword",goodKeyword)
                                    intent.putExtra("price",goodPrice)
                                    intent.putExtra("name",goodName)
                                    intent.putExtra("address",goodAddress)
                                    intent.putExtra("pictures",goodPictures)
                                    intent.putExtra("num",goodNum)
                                    startActivity(intent)

                                }
                            } else {
                                btn_buy.visibility = View.VISIBLE
                                tv_isseller.visibility = View.GONE
                                btn_update.visibility = View.GONE
                            }


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
                                                Toast.makeText(this@GoodActivity, "关注成功", Toast.LENGTH_SHORT).show()

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
                                        if (returnData!!.code == 200) {
                                            runOnUiThread {
                                                btn_isfollowed.visibility = View.GONE
                                                btn_follow.visibility = View.VISIBLE
                                                Toast.makeText(this@GoodActivity, "取消关注成功", Toast.LENGTH_SHORT).show()
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
                                                Toast.makeText(this@GoodActivity, "收藏成功", Toast.LENGTH_SHORT).show()

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
                                                Toast.makeText(this@GoodActivity, "取消收藏成功", Toast.LENGTH_SHORT).show()

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

//                Message.d(TAG, responseData)
            }

        })

        val commentUrl = Constant.baseUrl + "get_goods_comment?id=$goodId&begin=0&num=6"

        HttpUtil.sendOkHttpGetRequest(commentUrl,object:Callback{
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val responseData = response!!.body()!!.string()
                val comments = Utility.handleGetCommnentResponse(responseData) ?: return
                if ( comments!!.code == 200 ) {
                    val data = comments.data
                    runOnUiThread {
                        rv_comments.layoutManager = GridLayoutManager(this@GoodActivity,1)
                        val adapter = CommentsAdapter(data!!)
                        rv_comments.adapter = adapter
                    }
                } else {
                    Log.d(TAG,"fail")
                }
                Log.d(TAG,responseData)
            }

        })
    }

}
