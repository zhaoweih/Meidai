package me.zhaoweihao.shopping

import android.app.FragmentTransaction
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*
import me.zhaoweihao.hnuplus.HomeFragment
import me.zhaoweihao.hnuplus.ContactFragment
import me.zhaoweihao.hnuplus.MomentFragment

class MainActivity : AppCompatActivity() {

    private var mHomeFragment: HomeFragment? = null
    private var mContactFragment: ContactFragment? = null
    private var mMomentFragment: MomentFragment? = null
    private var mUserFragment: UserFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        title="HNUPlus(Preview Version)"

        bindListener()

        setTabSelection(0)
    }

    private fun bindListener() {
        home_layout!!.setOnClickListener{setTabSelection(0)}
        contact_layout!!.setOnClickListener{setTabSelection(1)}
        moment_layout!!.setOnClickListener{setTabSelection(2)}
        user_layout!!.setOnClickListener { setTabSelection(3) }
    }

    private fun setTabSelection(index: Int) {

        clearSelection()

        val transaction = fragmentManager!!.beginTransaction()

        hideFragments(transaction)
        when (index) {
            0 -> {
                home_image!!.setImageResource(R.mipmap.ic_launcher)
                home_text!!.setTextColor(Color.WHITE)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    transaction.add(R.id.content, mHomeFragment)
                } else {
                    transaction.show(mHomeFragment)
                }
            }
            1 -> {
                contact_image!!.setImageResource(R.mipmap.ic_launcher)
                contact_text!!.setTextColor(Color.WHITE)
                if (mContactFragment == null) {
                    mContactFragment = ContactFragment()
                    transaction.add(R.id.content, mContactFragment)
                } else {
                    transaction.show(mContactFragment)
                }
            }
            2 -> {
                moment_image!!.setImageResource(R.mipmap.ic_launcher)
                moment_text!!.setTextColor(Color.WHITE)
                if (mMomentFragment == null) {
                    mMomentFragment = MomentFragment()
                    transaction.add(R.id.content, mMomentFragment)
                } else {
                    transaction.show(mMomentFragment)
                }
            }
            3 -> {
                user_image!!.setImageResource(R.mipmap.ic_launcher)
                user_text!!.setTextColor(Color.WHITE)
                if (mUserFragment == null) {
                    mUserFragment = UserFragment()
                    transaction.add(R.id.content, mUserFragment)
                } else {
                    transaction.show(mUserFragment)
                }
            }
        }
        transaction.commit()
    }

    private fun clearSelection() {
        home_image!!.setImageResource(R.mipmap.ic_launcher)
        home_text!!.setTextColor(Color.parseColor("#82858b"))
        contact_image!!.setImageResource(R.mipmap.ic_launcher)
        contact_text!!.setTextColor(Color.parseColor("#82858b"))
        moment_image!!.setImageResource(R.mipmap.ic_launcher)
        moment_text!!.setTextColor(Color.parseColor("#82858b"))
        user_image!!.setImageResource(R.mipmap.ic_launcher)
        user_text!!.setTextColor(Color.parseColor("#82858b"))
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment)
        }
        if (mContactFragment != null) {
            transaction.hide(mContactFragment)
        }
        if (mMomentFragment != null) {
            transaction.hide(mMomentFragment)
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment)
        }
    }


}
