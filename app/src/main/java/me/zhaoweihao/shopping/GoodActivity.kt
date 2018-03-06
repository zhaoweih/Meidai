package me.zhaoweihao.shopping

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_good.*
import com.daimajia.slider.library.SliderTypes.TextSliderView
import me.zhaoweihao.shopping.constant.Constant
import me.zhaoweihao.shopping.gson.Trade
import me.zhaoweihao.shopping.litepal.UserInfo
import me.zhaoweihao.shopping.utils.HttpUtil
import me.zhaoweihao.shopping.utils.Utility
import okhttp3.Call
import okhttp3.Response
import org.litepal.crud.DataSupport
import java.io.IOException
import javax.security.auth.callback.Callback


class GoodActivity : AppCompatActivity() {

    val TAG = "GoodActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_good)

        val intent = intent

        val goodId =intent.getIntExtra("id",0)
        val goodTag = intent.getStringExtra("tag")
        val goodDescription = intent.getStringExtra("description")
        val goodPrice = intent.getIntExtra("price",0)
        val goodName = intent.getStringExtra("name")
        val goodSellerId = intent.getIntExtra("sellerId",0)
        val goodAddress = intent.getStringExtra("address")
        val goodPictures = intent.getStringExtra("pictures")
        val goodDate = intent.getStringExtra("date")
        val goodNum = intent.getIntExtra("num", 0)
        val goodSellCount = intent.getIntExtra("sellCount", 0)
        val goodStatus = intent.getIntExtra("status", 0)
        val goodKeyword = intent.getStringExtra("keyword")
        val goodLevel = intent.getStringExtra("level")
        val userName = intent.getStringExtra("userName")
        val userAvator = intent.getStringExtra("userAvator")


        val leaveMessage = et_leave_message.text.toString()

        tv_good_name.text = goodName
        tv_good_price.text = goodPrice.toString()
        tv_good_address.text = goodAddress
        tv_good_sell.text = goodSellCount.toString()
        tv_good_left.text = goodNum.toString()
        tv_good_seller_name.text = userName
        tv_good_description.text = goodDescription

        val imageUrls =Gson().fromJson(goodPictures, Array<String>::class.java)

        for ( url in imageUrls ) {
            val textSliderView = TextSliderView(this)
            textSliderView.image(Constant.baseUrl+url)

            iv_good.addSlider(textSliderView)
        }

        iv_good.stopAutoCycle()

        btn_buy.setOnClickListener {
            val intent = Intent(this,BuyActivity::class.java)
            val buyNum = et_buy_num.text.toString().toInt()
            val totalPrice = goodPrice*buyNum
            intent.putExtra("id",goodId)
            intent.putExtra("sellerId", goodSellerId)
            intent.putExtra("singlePrice",goodPrice)
            intent.putExtra("totalPrice",totalPrice)
            intent.putExtra("buyNum",buyNum)
            intent.putExtra("leaveMessage",leaveMessage)
            intent.putExtra("goodName",goodName)
            startActivity(intent)

        }

    }

}
