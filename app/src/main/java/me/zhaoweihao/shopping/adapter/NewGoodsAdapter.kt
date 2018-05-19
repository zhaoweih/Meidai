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
import me.zhaoweihao.shopping.*
import me.zhaoweihao.shopping.constant.Constant.baseUrl
import me.zhaoweihao.shopping.goods.MyGoodsActivity
import me.zhaoweihao.shopping.data.Goods.Data
import me.zhaoweihao.shopping.goods.GoodActivity
import me.zhaoweihao.shopping.ui.MainActivity
import me.zhaoweihao.shopping.ui.SearchActivity


/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class NewGoodsAdapter(private val mGoodsList: List<Data>, private val code: Int) : RecyclerView.Adapter<NewGoodsAdapter.ViewHolder>() {
    
    companion object {
        private const val TAG = "NewGoodsAdapter"
    }

    private var mContext: Context? = null

    private var gson: Gson? =null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var goodsView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.goods_items_copy, parent, false)
        val holder = ViewHolder(view)

        holder.goodsView.setOnClickListener {
            val position = holder.adapterPosition
            val good = mGoodsList[position]
            Log.d(TAG,good.name)
            val intent = Intent(mContext, GoodActivity::class.java)
            intent.putExtra("id",good.goodsId)
            intent.putExtra("tag",good.tag)
            intent.putExtra("description",good.description)
            intent.putExtra("price", good.price)
            intent.putExtra("name", good.name)
            intent.putExtra("sellerId",good.sellerId)
            intent.putExtra("address", good.address)
            intent.putExtra("pictures", good.pictures)
            intent.putExtra("date", good.date)
            intent.putExtra("num", good.num)
            intent.putExtra("sellCount", good.sellCount)
            intent.putExtra("status", good.status)
            intent.putExtra("keyword", good.keyword)
            intent.putExtra("level", good.level)
            intent.putExtra("userName", good.userName)
            intent.putExtra("userAvator", good.userAvator)

                (mContext as MainActivity).startActivity(intent)



        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val good = mGoodsList[position]
        val imageUrl = gson!!.fromJson(good.pictures, Array<String>::class.java)[0]
        Log.d(TAG,baseUrl+imageUrl)
        Picasso.with(mContext).load(baseUrl+imageUrl)
                .resize(mContext!!.resources.getDimensionPixelSize(R.dimen.border_length), mContext!!.resources.getDimensionPixelSize(R.dimen.border_length))
                .centerInside()
                .into(holder.goodsImage)

    }

    override fun getItemCount(): Int {
        return mGoodsList.size
    }


}
