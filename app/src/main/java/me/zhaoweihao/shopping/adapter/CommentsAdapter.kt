package me.zhaoweihao.shopping.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.data.GetComment

/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class CommentsAdapter(private val mCommentsList: List<GetComment.Data>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private var mContext: Context? = null

    private var gson: Gson? = null

    val TAG = "CommentsAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userName = view.findViewById<TextView>(R.id.tv_name)
        var date = view.findViewById<TextView>(R.id.tv_date)
        var content = view.findViewById<TextView>(R.id.tv_content)
        var commentsView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        Log.d(TAG, "onCreateViewHolder")
        gson = Gson()
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.comments_item, parent, false)
        val holder = ViewHolder(view)

        holder.commentsView.setOnClickListener {

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = mCommentsList[position]

        val userName = comment.userName
        val date = comment.date
        val content = comment.content
        val secret = comment.secret

        if ( secret == 1) {
            holder.userName.text = "匿名"
        } else{
            holder.userName.text = userName
        }


        holder.content.text = content
        holder.date.text = date


    }

    override fun getItemCount(): Int {
        return mCommentsList.size
    }


}