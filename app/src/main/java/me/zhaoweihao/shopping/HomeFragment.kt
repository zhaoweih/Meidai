package me.zhaoweihao.hnuplus

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.zhaoweihao.shopping.R


/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val contactLayout = inflater!!.inflate(R.layout.fragment_home,
                container, false)
        return contactLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}