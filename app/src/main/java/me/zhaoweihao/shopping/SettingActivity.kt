package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_setting.*
import me.zhaoweihao.shopping.litepal.UserInfo
import org.litepal.crud.DataSupport

class SettingActivity : AppCompatActivity() {

    val TAG = "SettingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val find = DataSupport.findFirst(UserInfo::class.java)

        if ( find != null ) {
            btn_log_out.setOnClickListener {
                find.delete()
                finish()
            }

        } else {
            Log.d(TAG,"find is null")
        }

    }
}
