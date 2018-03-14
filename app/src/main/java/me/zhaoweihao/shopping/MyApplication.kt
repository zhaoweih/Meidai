package me.zhaoweihao.shopping

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.lzy.ninegrid.NineGridView
import com.squareup.picasso.Picasso
import io.socket.client.IO
import io.socket.client.Socket


import me.zhaoweihao.shopping.constant.Constant
import org.litepal.LitePalApplication
import java.lang.RuntimeException
import java.net.URISyntaxException


/**
 * Created by por on 2018/3/10.
 */
class MyApplication: LitePalApplication() {

    val TAG = "MyApplication"

    var socket: Socket? = null
        private set

    init {
        try {
            socket = IO.socket("http://193.112.79.90:3320")
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

    }

    override fun onCreate() {
        super.onCreate()

        NineGridView.setImageLoader(PicassoImageLoader())

    }


    /** Picasso 加载  */
    private inner class PicassoImageLoader : NineGridView.ImageLoader {

        override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
            Picasso.with(context).load(url)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .into(imageView)
        }

        override fun getCacheImage(url: String): Bitmap? {
            return null
        }
    }

}