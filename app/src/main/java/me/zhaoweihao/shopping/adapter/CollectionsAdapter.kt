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
import me.zhaoweihao.shopping.MainActivity
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Collections
import me.zhaoweihao.shopping.gson.Goods

/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class CollectionsAdapter(private val mCollectionsList: List<Collections.Data>) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? =null


    val TAG = "CollectionsAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var goodsName = view.findViewById<TextView>(R.id.tv_goods_name)
        var goodsPrice = view.findViewById<TextView>(R.id.tv_goods_price)
        var goodsDescription = view.findViewById<TextView>(R.id.tv_goods_description)
        var goodsNum = view.findViewById<TextView>(R.id.tv_goods_num)
        var goodsSeller = view.findViewById<TextView>(R.id.tv_goods_seller)
        var collectionsView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "onCreateViewHolder")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.collections_item, parent, false)
        val holder = ViewHolder(view)

        holder.collectionsView.setOnClickListener {
//            val position = holder.adapterPosition
//            val good = mGoodsList[position]
//            Log.d(TAG, good.name)
//            val intent = Intent(mContext, GoodActivity::class.java)
//            intent.putExtra("id",good.goodsId)
//            intent.putExtra("tag",good.tag)
//            intent.putExtra("description",good.description)
//            intent.putExtra("price", good.price)
//            intent.putExtra("name", good.name)
//            intent.putExtra("sellerId",good.sellerId)
//            intent.putExtra("address", good.address)
//            intent.putExtra("pictures", good.pictures)
//            intent.putExtra("date", good.date)
//            intent.putExtra("num", good.num)
//            intent.putExtra("sellCount", good.sellCount)
//            intent.putExtra("status", good.status)
//            intent.putExtra("keyword", good.keyword)
//            intent.putExtra("level", good.level)
//            intent.putExtra("userName", good.userName)
//            intent.putExtra("userAvator", good.userAvator)
//
//            (mContext as MainActivity).startActivity(intent)



        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = mCollectionsList[position]
        val imageUrl = gson!!.fromJson(collection.pictures, Array<String>::class.java)[0]
        Log.d(TAG, Constant.baseUrl + imageUrl)
        Picasso.with(mContext).load(Constant.baseUrl +imageUrl)
                .resize(70, 70)
                .centerCrop()
                .into(holder.goodsImage)
        holder.goodsName.text = collection.name
        holder.goodsPrice.text = collection.price.toString()
        holder.goodsDescription.text = collection.description
        holder.goodsNum.text = collection.num.toString()
        holder.goodsSeller.text = collection.userName

    }

    override fun getItemCount(): Int {
        return mCollectionsList.size
    }


}