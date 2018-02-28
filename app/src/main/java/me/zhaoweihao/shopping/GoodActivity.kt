package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_good.*
import com.daimajia.slider.library.SliderTypes.TextSliderView
import me.zhaoweihao.shopping.constant.Constant


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

    }

}
