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
import me.zhaoweihao.shopping.gson.Concerns
import me.zhaoweihao.shopping.gson.Goods

/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class ConcernsAdapter(private val mConcernsList: List<Concerns.Data>) : RecyclerView.Adapter<ConcernsAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? =null


    val TAG = "ConcernsAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var concernsImage = view.findViewById<ImageView>(R.id.iv_avator)
        var concernsName = view.findViewById<TextView>(R.id.tv_username)
        var concernsView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "onCreateViewHolder")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.concerns_item, parent, false)
        val holder = ViewHolder(view)

        holder.concernsView.setOnClickListener {
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val concern = mConcernsList[position]
        Picasso.with(mContext).load(Constant.baseUrl +concern.userAvator)
                .resize(70, 70)
                .centerCrop()
                .into(holder.concernsImage)
        holder.concernsName.text = concern.userName

    }

    override fun getItemCount(): Int {
        return mConcernsList.size
    }


}