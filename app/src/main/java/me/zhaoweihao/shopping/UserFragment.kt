package me.zhaoweihao.shopping

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.*
import me.zhaoweihao.shopping.constant.Constant.baseUrl
import me.zhaoweihao.shopping.litepal.UserInfo
import org.litepal.crud.DataSupport

/**
 * Created by Administrator on 2018/2/8.
 */
class UserFragment : Fragment() {

    val TAG = "UserFragment"

    companion object {

        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_avatar.setOnClickListener {
            val intent = Intent(activity,LoginActivity::class.java)
            activity.startActivity(intent)

        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume")
        val find = DataSupport.find(UserInfo::class.java, 1)
        if (find != null) {
            tv_username.text = find.userName
            Log.d(TAG, find.userName)
            tv_location.text = find.userAddressMore
            tv_phone.text = find.userPhone
            tv_usercoin.text = find.userCoin.toString()
            val imageUrl = find.userAvator
            Picasso.with(activity).load(baseUrl+imageUrl)
                    .resize(100, 100)
                    .centerCrop()
                    .into(iv_avatar)
            if (find.userAuthenticated == 1) {
                tv_userauthenticated.text = "已实名"
            } else {
                tv_userauthenticated.text = "未实名"
            }
        } else {
            Log.d(TAG,"bad")
        }


    }

}