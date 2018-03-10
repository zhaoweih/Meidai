package me.zhaoweihao.shopping

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.gson.Gson
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import kotlinx.android.synthetic.main.activity_publish_goods.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.PublishGoods
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.File
import java.io.IOException

class PublishGoodsActivity : AppCompatActivity() {

    val TAG = "PublishGoodsActivity"

    var imageUrls: Array<String?> = arrayOfNulls(9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_goods)

        val find = DataSupport.find(UserInfo::class.java, 1)

        if ( find != null ) {
            /**
             * 发布商品
             * @param {string} token
             * @param {string} tag 标签
             * @param {string} description 描述
             * @param {string} keyword 关键词
             * @param {string} price 价格
             * @param {string} name 商品名字
             * @param {number} seller_id 卖家id
             * @param {string} address 卖家地址
             * @param {string} pictures 展示图片数组 ["/assets/images/upload/upload37347.jpeg","/assets/images/upload/upload37347.jpeg"]
             * @param {number} num 商品数量
             */

            iv_goods_image.setOnClickListener {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                } else {
                    showImageSelector()
                }
            }

            btn_publish.setOnClickListener {

                val token = find.userToken
                val tag = "化妆品"
                val description = et_goods_description.text.toString()
                val keyword = et_goods_keyword.text.toString()
                val price = et_goods_price.text.toString()
                val name = et_goods_name.text.toString()
                val sellerId = find.userId
                val address = find.userAddress
                val num = et_goods_num.text.toString().toInt()

                val publishGoods = PublishGoods()
                publishGoods.token = token
                publishGoods.tag = tag
                publishGoods.description = description
                publishGoods.keyword = keyword
                publishGoods.price = price
                publishGoods.name = name
                publishGoods.sellerId = sellerId!!
                publishGoods.address = address
                publishGoods.pictures = imageUrls
                publishGoods.num = num

                val jsonObject = Gson().toJson(publishGoods)
                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl+"publish_goods"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject, object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        Log.d(TAG,responseData)
                    }

                })

            }

        } else {
            Log.d(TAG,"find is null")
        }
    }

    private fun showImageSelector(){
        Matisse.from(this)
                .choose(setOf(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(PicassoEngine())
                .forResult(2)
    }

    private fun uploadImage(file: File,num: Int) {

        val url = "http://meidai.maocanhua.cn/upload/image"

        Log.d(TAG,file.absolutePath)
        HttpUtil.sendOkHttpPostFileRequest(url,file, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d(TAG,"failed")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val responseData = response!!.body()!!.string()
                val upload = Utility.handleUploadResponse(responseData)

                val i = num - 1
                Log.d(TAG,"i = "+i)
                imageUrls[i] = upload!!.data
                Log.d(TAG,"第${num}张图片上传成功")
                Log.d(TAG, responseData)
            }

        })

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageSelector()
            } else {
                Log.d(TAG, "Permission denied")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            2 -> if (resultCode == RESULT_OK) {

                //uri to path
                val uris = Matisse.obtainResult(data)
                val uriLength = uris.size
                val paths = arrayOfNulls<String>(uriLength)
                imageUrls = arrayOfNulls<String>(paths.size)
                var i = 0
                for (uri in uris) {
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
                    if (cursor!!.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                        paths[i] = cursor.getString(columnIndex)
                        uploadImage(File(paths[i]),i+1)
                        Log.d(TAG,paths[i]+" "+i.toString()+" "+paths.size.toString())
                        i++

                    } else {
                        //boooo, cursor doesn't have rows ...
                    }
                    cursor.close()
                }
                
            }
        }
    }
}
