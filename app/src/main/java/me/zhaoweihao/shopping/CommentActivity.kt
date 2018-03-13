package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.RatingBar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_comment.*
import me.zhaoweihao.shopping.gson.Comment
import me.zhaoweihao.shopping.litepal.UserInfo
import org.litepal.crud.DataSupport
import android.widget.RatingBar.OnRatingBarChangeListener
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class CommentActivity : AppCompatActivity() {

    val TAG = "CommentActivity"

    private var star: Int = 5

    private var secret: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val tradeId = intent.getIntExtra("tradeId",0)
        val goodId = intent.getIntExtra("goodId",0)

        rb_comment.onRatingBarChangeListener = OnRatingBarChangeListener { _, rating, _ ->
            star = rating.toInt()
            Log.d(TAG,star.toString())
        }

        sw_comment.setOnCheckedChangeListener(
                CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    secret = if (isChecked) {
                        1
                    } else {
                        0
                    }
                    Log.d(TAG,secret.toString())
                })

        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null ){

            btn_comment.setOnClickListener {
                /**
                 * 写入评论（暂时没有写删除评论，这些以后看看需不需要）
                 * @param {*} token
                 * @param {*} tid 订单id
                 * @param {*} text 评论
                 * @param {*} star 评价
                 * @param {*} gid 商品id
                 * @param {*} isSecret 是否匿名，默认匿名
                 */

                val comment = Comment()
                comment.token = find.userToken
                comment.tradeId = tradeId
                comment.text = et_comment.text.toString()
                comment.star = star
                comment.goodId = goodId
                comment.isSecret = secret

                val jsonObject = Gson().toJson(comment)

                Log.d(TAG,jsonObject)

                val url = Constant.baseUrl + "writer_comment"

                HttpUtil.sendOkHttpPostRequest(url,jsonObject,object:Callback{
                    override fun onFailure(call: Call?, e: IOException?) {

                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val responseData = response!!.body()!!.string()
                        val returnData = Utility.handleIsCommentResponse(responseData)
                        if (returnData!!.code == 200){
                            finish()
                        }
                        Log.d(TAG,responseData)
                    }

                })
            }



        } else {
            Log.d(TAG,"find is null")
        }

        Log.d(TAG,tradeId.toString()+" "+goodId.toString())
    }
}
