package me.zhaoweihao.shopping.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.youth.banner.loader.ImageLoader
import me.zhaoweihao.shopping.constant.Constant

/**
 * Created by Administrator on 2018/2/28.
 */
class PicassoImageLoader : ImageLoader() {
    val TAG = "PicassoImageLoader"
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val url : String = path as String
        //Picasso 加载图片简单用法
        Picasso.with(context)
                .load(Constant.baseUrl+url)
                .resize(500, 500)
                .centerCrop()
                .into(imageView)
    }

}