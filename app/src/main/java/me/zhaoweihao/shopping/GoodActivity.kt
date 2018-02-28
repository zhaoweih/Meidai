package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

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
        
    }
}
