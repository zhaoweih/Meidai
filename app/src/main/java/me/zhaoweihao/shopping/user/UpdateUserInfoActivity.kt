package me.zhaoweihao.shopping.user

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import kotlinx.android.synthetic.main.activity_update_user_info.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.data.Update
import me.zhaoweihao.shopping.database.UserInfo
import org.litepal.crud.DataSupport
import java.io.*
import android.provider.MediaStore
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class UpdateUserInfoActivity : AppCompatActivity() {

    val TAG = "UpdateUserInfoActivity"

    var returnUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_info)


        btn_test.setOnClickListener {

            val jsonData = loadJSONFromAsset()
            val jsonObject = JSONObject(jsonData)
            val keys = jsonObject.keys()
            while (keys.hasNext())
            {
                val key = keys.next() as String
                Log.d(TAG,key)
            }

        }

        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null ) {
            val userName = find.userName
            val userPhone = find.userPhone
            val userAddress = find.userAddress
            val userAddressMore = find.userAddressMore
            val userSex = find.userSex
            val userAvator = find.userAvator
            val userCoin = find.userCoin
            val userAuthenticated = find.userAuthenticated

            et_username.setText(userName)
            et_userphone.setText(userPhone)
            et_useraddress.setText(userAddress)
            et_useraddress_more.setText(userAddressMore)

            when(userSex) {
                0 -> {
                    rb_male.isChecked = true
                }
                1 -> {
                    rb_male.isChecked = false
                }
            }

            Picasso.with(this).load(Constant.baseUrl +userAvator)
                    .resize(70, 70)
                    .centerCrop()
                    .into(iv_avator)

            tv_coin.text = "美代币："+userCoin.toString()

            when(userAuthenticated) {
                0 -> tv_authenticated.text = "实名状态：未实名"
                1 -> tv_authenticated.text = "实名状态：已实名"
            }

            /**
             *
             * @param {*} token
             * @param {*} id
             * @param {*} user_name
             * @param {*} user_sex
             * @param {*} user_phone
             * @param {*} user_avator 头像
             * @param { Array } user_address 所在区域 数组的方式 ["广东","惠州","博罗"]
             * @param { string } user_address_more 地址补充
             */

            btn_sure.setOnClickListener {
                val update = Update()
                update.token = find.userToken
                update.id = find.userId
                update.userName = et_username.text.toString()

                if(rb_male.isChecked) {
                    update.userSex = 0
                } else {
                    update.userSex = 1
                }

                update.userPhone = et_userphone.text.toString()
                update.userAvator = returnUrl
                val strs = Gson().fromJson(userAddress, Array<String>::class.java)
                update.userAddress = strs
                update.userAddressMore = et_useraddress_more.text.toString()

                val jsonObject = Gson().toJson(update)
                Log.d(TAG,jsonObject)

                //将数据通过POST方法上传到服务器

                val url = Constant.baseUrl+"update_user_info"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject,object : Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        Log.d(TAG,responseData)
                    }

                })

            }

            iv_avator.setOnClickListener {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                } else {
                    showImageSelector()
                }
            }




        } else {
            Log.d(TAG, "find is null")
        }
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val `is` = this.assets.open("citys.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return json
    }

    private fun showImageSelector(){
        Matisse.from(this)
                .choose(setOf(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(PicassoEngine())
                .forResult(2)
    }


    private fun uploadImage(file: File) {

        val url = "http://meidai.maocanhua.cn/upload/image"

        Log.d(TAG,file.absolutePath)
        HttpUtil.sendOkHttpPostFileRequest(url,file, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d(TAG,"failed")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val responseData = response!!.body()!!.string()
                val upload = Utility.handleUploadResponse(responseData)
                returnUrl = upload!!.data
                Log.d(TAG,"头像上传成功")
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
                val uri = Matisse.obtainResult(data)[0]

                //uri to path
                var path: String? = null
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    path = cursor.getString(columnIndex)//输出的path
                    Log.d(TAG,path)
                } else {
                    //boooo, cursor doesn't have rows ...
                }
                cursor.close()

                uploadImage(File(path))



            }
        }
    }
}
