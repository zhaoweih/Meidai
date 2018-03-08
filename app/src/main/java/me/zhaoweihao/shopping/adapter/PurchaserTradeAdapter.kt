package me.zhaoweihao.shopping.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.PurchaserTrade

/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class PurchaserTradeAdapter(private val mPurchaserTradeList: List<PurchaserTrade.Data>) : RecyclerView.Adapter<PurchaserTradeAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? = null


    val TAG = "PurchaserTradeAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var sellerName = view.findViewById<TextView>(R.id.tv_seller)
        var goodsName = view.findViewById<TextView>(R.id.tv_goods_name)
        var goodsPrice = view.findViewById<TextView>(R.id.tv_goods_price)
        var buyNum = view.findViewById<TextView>(R.id.tv_buy_num)
        var buyDate = view.findViewById<TextView>(R.id.tv_buy_date)
        var leaveMessage = view.findViewById<TextView>(R.id.tv_leave_message)
        var tradeStatus = view.findViewById<TextView>(R.id.tv_trade_status)
        var purchaserTradeView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "onCreateViewHolder")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.purchaser_trade_item, parent, false)
        val holder = ViewHolder(view)

        holder.purchaserTradeView.setOnClickListener {

            //跳转至商品详情页面

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trade = mPurchaserTradeList[position]
        val imageUrl = gson!!.fromJson(trade.goodsPicture, Array<String>::class.java)[0]
        Log.d(TAG, Constant.baseUrl + imageUrl)
        Picasso.with(mContext).load(Constant.baseUrl + imageUrl)
                .resize(70, 70)
                .centerCrop()
                .into(holder.goodsImage)

        holder.sellerName.text = "商家名称："+trade.userName
        holder.goodsName.text = "商品名字："+trade.goodsName
        holder.goodsPrice.text = "商品价格："+trade.goodsPrice.toString()
        holder.buyNum.text = "购买数量："+trade.buyNum.toString()
        holder.buyDate.text = "购买日期："+trade.date
        holder.leaveMessage.text = "留言："+trade.leaveMessage

        var tradeStatus: String ? = null

        when (trade.status) {
            0 -> {
                tradeStatus = "等待卖家发货"
            }
            1 -> {
                tradeStatus = "等待买家收货"
            }
            2 -> {
                tradeStatus = "买家已签收"
            }
        }
        holder.tradeStatus.text = "交易状态："+tradeStatus


    }

    override fun getItemCount(): Int {
        return mPurchaserTradeList.size
    }


}