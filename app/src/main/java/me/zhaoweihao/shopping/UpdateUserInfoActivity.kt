package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update_user_info.*
import kotlinx.android.synthetic.main.fragment_user.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Update
import me.zhaoweihao.shopping.litepal.UserInfo
import org.litepal.crud.DataSupport

class UpdateUserInfoActivity : AppCompatActivity() {

    val TAG = "UpdateUserInfoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_info)

        val find = DataSupport.find(UserInfo::class.java, 1)

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
                update.userAvator = find.userAvator
                update.userAddress = et_useraddress.text.toString()
                update.userAddressMore = et_useraddress_more.text.toString()

                val jsonObject = Gson().toJson(update)
                Log.d(TAG,jsonObject)

                //将数据通过POST方法上传到服务器
                
            }


        } else {
            Log.d(TAG, "find is null")
        }
    }
}
