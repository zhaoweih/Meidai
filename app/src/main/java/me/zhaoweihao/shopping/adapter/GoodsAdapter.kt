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
import me.zhaoweihao.shopping.constant.Constant.baseUrl
import me.zhaoweihao.shopping.gson.Goods.Data


/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class GoodsAdapter(private val mGoodsList: List<Data>) : RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? =null

    val TAG = "GoodsAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var goodsName = view.findViewById<TextView>(R.id.tv_goods)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "OCV")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.goods_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val good = mGoodsList[position]
        val imageUrl = gson!!.fromJson(good.pictures, Array<String>::class.java)[0]
        Log.d(TAG,baseUrl+imageUrl)
        Picasso.with(mContext).load(baseUrl+imageUrl)
                .resize(500, 500)
                .centerCrop()
                .into(holder.goodsImage)
        holder.goodsName.text = good.name

    }

    override fun getItemCount(): Int {
        return mGoodsList.size
    }


}
