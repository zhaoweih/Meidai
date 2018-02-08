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

class MomentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val userLayout = inflater!!.inflate(R.layout.fragment_moment,
                container, false)
        return userLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
