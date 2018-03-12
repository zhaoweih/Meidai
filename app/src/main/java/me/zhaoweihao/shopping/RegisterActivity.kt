package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Register
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        btn_register.setOnClickListener {
            /**
             *  注册
             * @param {string} name 1-8
             * @param {number} phone 11
             * @param {string} password 6-36
             * @param {string} sex 1
             */
            val userName = et_username.text.toString()
            val userPhone = et_userphone.text.toString()
            val password = et_password.text.toString()
            val passwordConfirm = et_password_confirm.text.toString()



            if (password == passwordConfirm) {

                val register = Register()
                register.name = userName
                register.phone = userPhone
                register.password = password

                if (rb_male.isChecked) {
                    register.sex = 0
                } else {
                    register.sex = 1
                }

                val jsonObject = Gson().toJson(register)

                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl + "register"


                HttpUtil.sendOkHttpPostRequest(url,jsonObject,object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val user = Utility.handleUserResponse(responseData)
                        if (user!!.code == 200) {
                            val userInfoFromDatabase = UserInfo()
                            val userInfoFromResponse = user.userData!!.userInfo
                            userInfoFromDatabase.userId = userInfoFromResponse!!.userId
                            userInfoFromDatabase.userName = userInfoFromResponse.userName
                            userInfoFromDatabase.userPhone = userInfoFromResponse.userPhone
                            userInfoFromDatabase.userSex = userInfoFromResponse.userSex
                            userInfoFromDatabase.userAvator = userInfoFromResponse.userAvator
                            userInfoFromDatabase.userRank = userInfoFromResponse.userRank
                            userInfoFromDatabase.userStatus = userInfoFromResponse.userStatus
                            userInfoFromDatabase.userCoin = userInfoFromResponse.userCoin
                            userInfoFromDatabase.userRegisterDate = userInfoFromResponse.userRegisterDate
                            userInfoFromDatabase.userAuthenticated = userInfoFromResponse.userAuthenticated
                            userInfoFromDatabase.userRealName = userInfoFromResponse.userRealName
                            userInfoFromDatabase.userRealIdentityCard = userInfoFromResponse.userRealIdentityCard
                            userInfoFromDatabase.userAddress = userInfoFromResponse.userAddress
                            userInfoFromDatabase.userAddressMore = userInfoFromResponse.userAddressMore
                            userInfoFromDatabase.userToken = user.userData!!.userToken
                            userInfoFromDatabase.save()
                            
                            LoginActivity.loginActivity!!.finish()
                            finish()
                        } else {
                            Log.d(TAG,"code is not 200")
                        }
                        Log.d(TAG,responseData)
                    }

                })

            } else {
                Log.d(TAG,"please confirm your password")
            }

        }
    }
}
