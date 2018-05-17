package me.zhaoweihao.shopping.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.data.Login
import me.zhaoweihao.shopping.database.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"



    companion object {
        var loginActivity: LoginActivity? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginActivity = this

        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        login_btn.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()

            val url = "http://meidai.maocanhua.cn/login"

            val login = Login()

            login.phone = username
            login.password = password

            val jsonObject = Gson().toJson(login)

            Log.d(TAG, jsonObject)

            HttpUtil.sendOkHttpPostRequest(url, jsonObject, object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body()!!.string()
                    Log.d(TAG, responseData)
                    val user = Utility.handleUserResponse(responseData)
                    if (user!!.code == 200) {
                        Log.d(TAG, user.userData!!.userInfo!!.userPhone)
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
                        finish()
                    } else {
                        Log.d(TAG, "failed")
                    }
                }
            })
        }
    }



}
