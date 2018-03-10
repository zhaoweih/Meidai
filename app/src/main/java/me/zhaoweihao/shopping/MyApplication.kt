package me.zhaoweihao.shopping

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.lzy.ninegrid.NineGridView
import com.squareup.picasso.Picasso
import org.litepal.LitePalApplication

/**
 * Created by por on 2018/3/10.
 */
class MyApplication: LitePalApplication() {
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