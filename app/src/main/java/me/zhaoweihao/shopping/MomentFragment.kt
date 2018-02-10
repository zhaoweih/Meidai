package me.zhaoweihao.hnuplus


import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_moment.*
import me.zhaoweihao.shopping.*


/**
 * Created by ZhaoWeihao on 2017/11/9.
 */

class MomentFragment : Fragment() {

    private var mPagerAdapter: MomentFragmentPagerAdapter? = null

    companion object {

        fun newInstance(): MomentFragment {
            return MomentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_moment,
                container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPagerAdapter = MomentFragmentPagerAdapter(fragmentManager, activity)

        viewpager.adapter = mPagerAdapter
        sliding_tabs.setupWithViewPager(viewpager)
        sliding_tabs.tabMode = TabLayout.MODE_FIXED
    }

    private class MomentFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

        internal val PAGE_COUNT = 3
        private val tabTitles = arrayOf(context.getText(R.string.dynamic), context.getText(R.string.score), context.getText(R.string.location))

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> DynamicFragment.newInstance()
                1 -> ScoreFragment.newInstance()
                2 -> LocationFragment.newInstance()
                else -> {
                    DynamicFragment.newInstance()
                }
            }

        }

        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabTitles[position]
        }

    }

}
