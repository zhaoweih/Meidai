package me.zhaoweihao.shopping.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_authenticate.*
import me.zhaoweihao.shopping.R
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.data.Authenticate
import me.zhaoweihao.shopping.database.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException

class AuthenticateActivity : AppCompatActivity() {

    val TAG = "AuthenticateActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate)

        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null ) {

            btn_sure.setOnClickListener {
                val realName = et_realname.text.toString()
                val realId = et_idcard.text.toString()

                val authenticate = Authenticate()
                /**
                 * 设置用户实名
                 * @param {string} token
                 * @param {number} id 用户id
                 * @param {string} real_name
                 * @param {string} real_identity_card
                 */

                authenticate.token = find.userToken
                authenticate.id = find.userId!!
                authenticate.realName = realName
                authenticate.realIdentityCard = realId

                val jsonObject = Gson().toJson(authenticate)

                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl + "set_user_authenticated"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject,object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {

                        val responseData = response!!.body()!!.string()
                        val isAuthenticate = Utility.handleIsAuthenicateResponse(responseData)
                        if (isAuthenticate!!.code==200&&isAuthenticate.data==true){
                            Log.d(TAG,"authenticate success")
                            finish()
                        } else {
                            Log.d(TAG,"fail")
                        }
                        Log.d(TAG,responseData)
                    }

                })
            }





        } else {
            Log.d(TAG,"find is null")
        }
    }
}
