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
import me.zhaoweihao.shopping.CommentActivity
import me.zhaoweihao.shopping.GoodActivity
import me.zhaoweihao.shopping.PurchaserTradeActivity
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.PurchaserTrade
import me.zhaoweihao.shopping.gson.Receive
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

class PurchaserTradeAdapter(private val mPurchaserTradeList: List<PurchaserTrade.Data>) : RecyclerView.Adapter<PurchaserTradeAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? = null


    val TAG = "PurchaserTradeAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var sellerName = view.findViewById<TextView>(R.id.tv_purchaser)
        var goodsName = view.findViewById<TextView>(R.id.tv_goods_name)
        var goodsPrice = view.findViewById<TextView>(R.id.tv_goods_price)
        var buyNum = view.findViewById<TextView>(R.id.tv_buy_num)
        var buyDate = view.findViewById<TextView>(R.id.tv_address)
        var leaveMessage = view.findViewById<TextView>(R.id.tv_leave_message)
        var tradeStatus = view.findViewById<TextView>(R.id.tv_trade_status)
        var receive = view.findViewById<Button>(R.id.btn_sure)
        var comment = view.findViewById<Button>(R.id.btn_comment)
        var commentContent = view.findViewById<TextView>(R.id.tv_content)
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
            val position = holder.adapterPosition
            val trade = mPurchaserTradeList[position]
            val intent = Intent(mContext, GoodActivity::class.java)
            intent.putExtra("id", trade.goodsId)
            (mContext as PurchaserTradeActivity).startActivity(intent)

        }

        holder.receive.setOnClickListener {
            val postion = holder.adapterPosition
            val trade = mPurchaserTradeList[postion]

            val find = DataSupport.find(UserInfo::class.java,1)

            if ( find != null ) {
                val receive = Receive()


                /**
                 * 将商品状态设置为签收
                 * @param {*} token
                 * @param {*} id 订单id
                 * @param {*} status 2
                 * @param {*} price 价格
                 * @param {*} buyNum 购买数
                 */

                receive.token = find.userToken

                receive.id = trade.purchaserTradeId
                receive.status = 2
                receive.price = trade.goodsPrice
                receive.buyNum = trade.buyNum

                val jsonObject = Gson().toJson(receive)

                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl+"receive"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject,object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleIsReceiveResponse(responseData)

                        if (returnData!!.code == 200) {
                            (mContext as PurchaserTradeActivity).runOnUiThread {
                                holder.comment.visibility = View.VISIBLE
                                holder.receive.visibility = View.GONE
                            }
                        }
                        Log.d(TAG,responseData)
                    }

                })




            } else {
                Log.d(TAG,"find is null")
            }


        }

        holder.comment.setOnClickListener {
            val position = holder.adapterPosition
            val trade = mPurchaserTradeList[position]

                /**
                 * 写入评论（暂时没有写删除评论，这些以后看看需不需要）
                 * @param {*} token
                 * @param {*} tid 订单id
                 * @param {*} text 评论
                 * @param {*} star 评价
                 * @param {*} gid 商品id
                 * @param {*} isSecret 是否匿名，默认匿名
                 */

                val intent = Intent(mContext,CommentActivity::class.java)
            intent.putExtra("tradeId",trade.purchaserTradeId)
            intent.putExtra("goodId",trade.goodsId)
            (mContext as PurchaserTradeActivity) .startActivity(intent)

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
                holder.receive.visibility = View.GONE
                holder.comment.visibility = View.GONE
            }
            1 -> {
                tradeStatus = "等待买家收货"
                holder.receive.visibility = View.VISIBLE
                holder.comment.visibility = View.GONE
            }
            2 -> {
                tradeStatus = "买家已签收"
                holder.receive.visibility = View.GONE
                holder.comment.visibility = View.VISIBLE
            }
        }

        when (trade.commented) {
            1 -> {
                holder.comment.visibility = View.GONE
                holder.commentContent.visibility = View.VISIBLE
                holder.commentContent.text = "评论："+trade.commentsContent
            }
        }
        holder.tradeStatus.text = "交易状态："+tradeStatus



    }

    override fun getItemCount(): Int {
        return mPurchaserTradeList.size
    }


}