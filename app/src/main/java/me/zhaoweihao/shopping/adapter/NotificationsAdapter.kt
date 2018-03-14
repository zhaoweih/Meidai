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
import me.zhaoweihao.shopping.CollectionActivity
import me.zhaoweihao.shopping.GoodActivity
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Collections
import me.zhaoweihao.shopping.gson.Notifications

/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class NotificationsAdapter(private val mNotificationsList: List<Notifications.Data>) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? = null


    val TAG = "NotificationsAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userAvator = view.findViewById<ImageView>(R.id.iv_avator)
        var userName = view.findViewById<TextView>(R.id.tv_name)
        var message = view.findViewById<TextView>(R.id.tv_message)
        var date = view.findViewById<TextView>(R.id.tv_date)
        var notificationsView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "onCreateViewHolder")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.notifications_item, parent, false)
        val holder = ViewHolder(view)

        holder.notificationsView.setOnClickListener {
//            val position = holder.adapterPosition
//            val collection = mCollectionsList[position]
//            Message.d(TAG, collection.name)
//            val intent = Intent(mContext, GoodActivity::class.java)
//            intent.putExtra("id", collection.goodsId)
//            intent.putExtra("tag", collection.tag)
//            intent.putExtra("description", collection.description)
//            intent.putExtra("price", collection.price)
//            intent.putExtra("name", collection.name)
//            intent.putExtra("sellerId", collection.sellerId)
//            intent.putExtra("address", collection.address)
//            intent.putExtra("pictures", collection.pictures)
//            intent.putExtra("date", collection.date)
//            intent.putExtra("num", collection.num)
//            intent.putExtra("sellCount", collection.sellCount)
//            intent.putExtra("status", collection.status)
//            intent.putExtra("keyword", collection.keyword)
//            intent.putExtra("level", collection.level)
//            intent.putExtra("userName", collection.userName)
//            intent.putExtra("userAvator", collection.userAvator)
//
//            (mContext as CollectionActivity).startActivity(intent)


        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = mNotificationsList[position]

        val avator = notification.userAvator
        val userName = notification.userName
        val message = notification.logText
        val date = notification.logDate

        Picasso.with(mContext).load(Constant.baseUrl + avator)
                .resize(70, 70)
                .centerCrop()
                .into(holder.userAvator)

        holder.userName.text = userName
        holder.message.text = message
        holder.date.text = date


    }

    override fun getItemCount(): Int {
        return mNotificationsList.size
    }


}