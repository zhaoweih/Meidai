package me.zhaoweihao.shopping.goods

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.preview.NineGridViewClickAdapter
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import kotlinx.android.synthetic.main.activity_update_goods.*
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.data.UpdateGoods
import me.zhaoweihao.shopping.database.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.File
import java.io.IOException

class UpdateGoodsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    val TAG = "UpdateGoodsActivity"

    var imageUrls: Array<String?> = arrayOfNulls(9)

    var array: Array<String?>? = null

    var tagName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_goods)

        val goodId = intent.getIntExtra("goodId", 0)
        val goodTag = intent.getStringExtra("tag")
        val goodDescription = intent.getStringExtra("description")
        val goodKeyword = intent.getStringExtra("keyword")
        val goodPrice = intent.getIntExtra("price", 0)
        val goodName = intent.getStringExtra("name")
        val goodAddress = intent.getStringExtra("address")
        val goodPictures = intent.getStringExtra("pictures")
        val goodNum = intent.getIntExtra("num", 0)

        et_goods_name.setText(goodName)
        et_goods_keyword.setText(goodKeyword)
        et_goods_price.setText(goodPrice.toString())
        et_goods_num.setText(goodNum.toString())
        et_goods_description.setText(goodDescription)
        tv_goods_address.text = goodAddress

        Log.d(TAG,goodPictures)

        val strArray = Gson().fromJson(goodPictures, Array<String?>::class.java)

        for (str in strArray) {
            Log.d(TAG,str)
        }

        imageUrls = strArray

        showImageWithUrl(strArray)

        iv_goods_image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                showImageSelector()
            }
        }

        val getTagUrl = Constant.baseUrl + "get_tags"

        HttpUtil.sendOkHttpGetRequest(getTagUrl, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val responseData = response!!.body()!!.string()
                val tags = Utility.handleTagResponse(responseData)
                if (tags!!.code == 200) {
                    val tagsSize = tags.data!!.size
                    array = arrayOfNulls<String>(tagsSize)
                    for (i in 0..(tagsSize - 1)) {
                        array!![i] = tags.data!![i].tagName
                    }
                    runOnUiThread {
                        for (i in array!!) {
                            Log.d(TAG, i)
                        }

                        val adapter = ArrayAdapter<String>(this@UpdateGoodsActivity, android.R.layout.simple_spinner_item, array)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        sp_tag.adapter = adapter
                        sp_tag.onItemSelectedListener = this@UpdateGoodsActivity

                        sp_tag.setSelection(adapter.getPosition(goodTag))

                    }
                }
                Log.d(TAG, responseData)
            }

        })
        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null ) {
            btn_update.setOnClickListener {
                /**
                 *更新商品信息，参数类型如上
                 * @param {*} token
                 * @param {*} tag
                 * @param {*} description
                 * @param {*} keyword
                 * @param {*} price
                 * @param {*} name
                 * @param {*} id 商品id
                 * @param {*} address
                 * @param {*} pictures
                 * @param {*} num
                 */

                val token = find.userToken
                val tag = tagName
                val description = et_goods_description.text.toString()
                val keyword = et_goods_keyword.text.toString()
                val price = et_goods_price.text.toString()
                val name = et_goods_name.text.toString()
                val goodId = goodId
                val address = find.userAddress
                val num = et_goods_num.text.toString().toInt()

                val updateGoods = UpdateGoods()
                updateGoods.token = token
                updateGoods.tag = tag
                updateGoods.description = description
                updateGoods.keyword = keyword
                updateGoods.price = price
                updateGoods.name = name
                updateGoods.goodId = goodId
                updateGoods.address = address
                updateGoods.pictures = imageUrls
                updateGoods.num = num

                val jsonObect = Gson().toJson(updateGoods)

                Log.d(TAG,jsonObect)

                val url = Constant.baseUrl + "update_goods"

                HttpUtil.sendOkHttpPostRequest(url,jsonObect,object:Callback{
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




        btn_cancel.setOnClickListener {
            finish()
        }

        Log.d(TAG, goodId.toString())
    }

    private fun showImageWithUrl(imageUrls: Array<String?>) {

//        iv_goods_image.visibility = View.GONE

        val imageInfo = ArrayList<ImageInfo>()

        val length = imageUrls.size

        val baseUrl = Constant.baseUrl

        for (i in 0..(length - 1)) {
            Log.d(TAG, imageUrls[i])
            val info = ImageInfo()
            info.setThumbnailUrl(baseUrl+imageUrls[i])
            info.setBigImageUrl(baseUrl+imageUrls[i])
            imageInfo.add(info)
        }

        ngv_image.setAdapter(NineGridViewClickAdapter(this, imageInfo))
//        ngv_image.setOnClickListener { showImageSelector() }
    }

    private fun showImageWithUri(imageUris: List<Uri>) {

//        iv_goods_image.visibility = View.GONE

        val imageInfo = ArrayList<ImageInfo>()

        val length = imageUris.size

        for (i in 0..(length - 1)) {
            Log.d(TAG, imageUris[i].toString())
            val info = ImageInfo()
            info.setThumbnailUrl(imageUris[i].toString())
            info.setBigImageUrl(imageUris[i].toString())
            imageInfo.add(info)
        }

        ngv_image.setAdapter(NineGridViewClickAdapter(this, imageInfo))
//        ngv_image.setOnClickListener { showImageSelector() }
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
                showImageWithUri(uris)
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


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        tagName = array!![position]
    }
}
