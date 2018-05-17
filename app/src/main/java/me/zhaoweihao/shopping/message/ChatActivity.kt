package me.zhaoweihao.shopping.message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_chat.*
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.data.Message
import me.zhaoweihao.shopping.database.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response

import org.litepal.crud.DataSupport
import java.io.IOException

import java.text.SimpleDateFormat
import java.util.*
import io.socket.client.Socket
import io.socket.emitter.Emitter
import me.zhaoweihao.shopping.app.MyApplication
import me.zhaoweihao.shopping.R
import org.json.JSONException
import org.json.JSONObject




class ChatActivity : AppCompatActivity() {
    val TAG = "ChatActivity"
    private var mSocket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val app = application as MyApplication
        mSocket = app.socket
        mSocket!!.on(Socket.EVENT_CONNECT, onConnect)
        mSocket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        mSocket!!.on("/user/42", onNewMessage)
        mSocket!!.connect()


        val find = DataSupport.findFirst(UserInfo::class.java)

        if (find != null) {

            btn_send.setOnClickListener {
                val log = et_message.text.toString()

                /**
                 * 发送信息
                 * @param {*} token
                 * @param {*} fromid 发送者的id
                 * @param {*} toid 接受者的id
                 * @param {*} text 信息（过滤html）
                 * @param {*} html  信息（带有html）
                 * @param {*} date 时间 格式 （YYYY-MM-DD HH:mm:ss）
                 */


                val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)

                val message = Message()
                message.token = find.userToken
                message.fromid = find.userId!!
                message.toid = 49
                message.text = log
                message.html = log
                message.date = currentTime

                val jsonObject = Gson().toJson(message)

                Log.d(TAG, jsonObject)

                val url = Constant.baseUrl + "send_log"

                HttpUtil.sendOkHttpPostRequest(url, jsonObject, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val getLog = Utility.handleGetLogResponse(responseData)
                        if (getLog!!.code == 200) {
                            val data = getLog.data!![1]

                            val json = JSONObject()
                            json.put("from", "/user/"+data.logFrom)
                            json.put("to", "/user/"+data.logTo)
                            json.put("data",JSONObject(Gson().toJson(data)))
                            json.put("token",find.userToken)
                            json.put("action","chat")

                            //perform the sending message attempt.
                            mSocket!!.emit("client", json)


                        } else {
                            Log.d(TAG, "code is not 200")
                        }
                    }

                })


            }

        } else {
            Log.d(TAG, "find is null")
        }


    }

    private val onConnect = Emitter.Listener {
        runOnUiThread {
            Log.d(TAG, "连接成功")
            Toast.makeText(applicationContext,
                    "连接成功", Toast.LENGTH_LONG).show()

        }
    }

    private val onConnectError = Emitter.Listener {
        runOnUiThread {
            Log.e(TAG, "Error connecting")
            Toast.makeText(applicationContext,
                    "连接失败", Toast.LENGTH_LONG).show()
        }
    }

    private val onDisconnect = Emitter.Listener {
        runOnUiThread {
            Log.i(TAG, "diconnected")
            Toast.makeText(applicationContext,
                    "连接失败", Toast.LENGTH_LONG).show()
        }
    }

    private val onNewMessage = Emitter.Listener { msg ->
        runOnUiThread(Runnable {
            Log.d(TAG, msg[0].toString())
            val data = msg[0] as JSONObject

            val str: String
            val message: String
            try {
                str = data.getString("action")
                message = data.getString("data")
                if (str == "chat") {
                    Log.d(TAG, "收到消息")
                    Log.d(TAG, message)
                }
            } catch (e: JSONException) {
                Log.e(TAG, e.message)
                return@Runnable
            }
        })
    }

    override fun onDestroy() {
        mSocket!!.disconnect()
        mSocket!!.off("/user/42")
        super.onDestroy()

    }

}
