package me.zhaoweihao.shopping.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import me.zhaoweihao.shopping.GoodActivity
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.SellerTradeActivity
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.SellerTrade

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
        var comment = view.findViewById<TextView>(R.id.tv_comment)
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
        return mSellerTradeList.size
    }


}