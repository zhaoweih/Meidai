package me.zhaoweihao.shopping.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
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
import me.zhaoweihao.shopping.goods.TagGoodsActivity
import me.zhaoweihao.shopping.ui.MainActivity
import me.zhaoweihao.shopping.ui.SearchActivity
import org.w3c.dom.Text


/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class GoodsAdapter(private val mGoodsList: List<Data>,private val code: Int) : RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
    
    companion object {
        private const val TAG = "GoodsAdapter"

        const val MYGOODSACTIVITY_CODE = 2

        const val HOMEFRAGMENT_CODE = 1

        const val SEARCHACTIVITY_CODE = 3

        const val TAG_CODE = 4
    }

    private var mContext: Context? = null

    private var gson: Gson? =null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goodsImage = view.findViewById<ImageView>(R.id.iv_goods)
        var goodsName = view.findViewById<TextView>(R.id.tv_goods)
        var goodsPrice = view.findViewById<TextView>(R.id.tv_goods_price)
        var goodsLocation = view.findViewById<TextView>(R.id.tv_goods_location)
        var goodsSell = view.findViewById<TextView>(R.id.tv_goods_sell)
        var goodsSeller = view.findViewById<TextView>(R.id.tv_goods_seller)
        var sellerAva = view.findViewById<ImageView>(R.id.iv_goods_seller)
        var goodsView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.new_good_item, parent, false)
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

            if (code == HOMEFRAGMENT_CODE) {
                (mContext as MainActivity).startActivity(intent)
            } else if (code == MYGOODSACTIVITY_CODE) {
                (mContext as MyGoodsActivity).startActivity(intent)
            } else if (code == SEARCHACTIVITY_CODE) {
                (mContext as SearchActivity).startActivity(intent)
            } else if (code == TAG_CODE) {
                (mContext as TagGoodsActivity).startActivity(intent)
            }


        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val good = mGoodsList[position]
        val imageUrl = gson!!.fromJson(good.pictures, Array<String>::class.java)[0]
        Picasso.with(mContext).load(baseUrl+imageUrl)
                .resize(500, 500)
                .centerCrop()
                .into(holder.goodsImage)
        Picasso.with(mContext).load(baseUrl+good.userAvator)
                .resize(50, 50)
                .centerCrop()
                .into(holder.sellerAva)

        val index = good.address!!.indexOf('-')
        val address: String? = if (index == -1) null else good.address!!.substring(index + 1, index + 3)
        holder.goodsName.text = good.name
        holder.goodsPrice.text = good.price.toString()
        holder.goodsLocation.text = address
        holder.goodsSell.text = "销量：" + good.sellCount.toString()
        holder.goodsSeller.text = "卖家：" + good.userName

    }

    override fun getItemCount(): Int {
        return mGoodsList.size
    }


}
