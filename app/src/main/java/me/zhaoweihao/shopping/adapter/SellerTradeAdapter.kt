package me.zhaoweihao.shopping.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import me.zhaoweihao.shopping.GoodActivity
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.SellerTradeActivity
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Deliver
import me.zhaoweihao.shopping.gson.SellerTrade
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class SellerTradeAdapter(private val mSellerTradeList: List<SellerTrade.Data>) : RecyclerView.Adapter<SellerTradeAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? = null


    val TAG = "SellerTradeAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var purchaserName = view.findViewById<TextView>(R.id.tv_purchaser)
        var goodsName = view.findViewById<TextView>(R.id.tv_goods_name)
        var goodsPrice = view.findViewById<TextView>(R.id.tv_goods_price)
        var buyNum = view.findViewById<TextView>(R.id.tv_buy_num)
        var address = view.findViewById<TextView>(R.id.tv_address)
        var tradeStatus = view.findViewById<TextView>(R.id.tv_trade_status)
        var comment = view.findViewById<TextView>(R.id.tv_content)
        var deliver = view.findViewById<Button>(R.id.btn_deliver)
        var sellerTradeView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "onCreateViewHolder")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.seller_trade_item, parent, false)
        val holder = ViewHolder(view)

        holder.sellerTradeView.setOnClickListener {
            val position = holder.adapterPosition
            val trade = mSellerTradeList[position]
            val intent = Intent(mContext, GoodActivity::class.java)
            intent.putExtra("id", trade.goodsId)
            (mContext as SellerTradeActivity).startActivity(intent)

        }

        holder.deliver.setOnClickListener {
            val position = holder.adapterPosition
            val trade = mSellerTradeList[position]

            val find = DataSupport.findFirst(UserInfo::class.java)

            if ( find != null ) {

                val deliver = Deliver()
                /** 0=> 已下单 1=>已发货 2=>已签收
                 * 将商品状态设置为发货 1
                 * @param {*} token
                 * @param {*} id
                 * @param {*} status
                 */

                deliver.token = find.userToken
                deliver.id = trade.sellerTradeId
                deliver.status = 1

                val jsonObject = Gson().toJson(deliver)

                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl + "deliver"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject,object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleIsDeliverResponse(responseData)
                        if ( returnData!!.code == 200 ) {
                            Log.d(TAG,"response")
                            (mContext as SellerTradeActivity).runOnUiThread {
                                holder.deliver.visibility = View.GONE
                            }
                        }
                        Log.d(TAG,responseData)
                    }

                })


            } else {
                Log.d(TAG,"find is null")
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trade = mSellerTradeList[position]
        val imageUrl = gson!!.fromJson(trade.goodsPicture, Array<String>::class.java)[0]
        Log.d(TAG, Constant.baseUrl + imageUrl)
        Picasso.with(mContext).load(Constant.baseUrl + imageUrl)
                .resize(70, 70)
                .centerCrop()
                .into(holder.goodsImage)

        holder.purchaserName.text = "买家名称："+trade.userName
        holder.goodsName.text = "商品名字："+trade.goodsName
        holder.goodsPrice.text = "商品价格："+trade.goodsPrice.toString()
        holder.buyNum.text = "购买数量："+trade.buyNum.toString()
        holder.address.text = "寄送地址："+trade.userAddress+trade.userAddressMore
        if (trade.commentsContent != null) {
            holder.comment.text = "留言："+trade.commentsContent
        } else {
            holder.comment.text = "暂无评论"
        }


        var tradeStatus: String ? = null

        when (trade.status) {
            0 -> {
                tradeStatus = "等待卖家发货"
                holder.deliver.visibility = View.VISIBLE
            }
            1 -> {
                tradeStatus = "等待买家收货"
                holder.deliver.visibility = View.GONE
            }
            2 -> {
                tradeStatus = "买家已签收"
                holder.deliver.visibility = View.GONE
            }
        }
        holder.tradeStatus.text = "交易状态："+tradeStatus


    }

    override fun getItemCount(): Int {
        return mSellerTradeList.size
    }


}