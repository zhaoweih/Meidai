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

        // select 0 tab when first run
        setTabSelection(0)
    }

    private fun bindListener() {
        contact_layout!!.setOnClickListener{setTabSelection(0)}
        more_layout!!.setOnClickListener{setTabSelection(1)}
        user_layout!!.setOnClickListener{setTabSelection(2)}
        extra_layout!!.setOnClickListener { setTabSelection(3) }
    }

    private fun setTabSelection(index: Int) {
        // Clear the last selected state before each selection
        clearSelection()
        // Open a Fragment transaction
        val transaction = fragmentManager!!.beginTransaction()
        // Hide all the Fragment first to prevent multiple Fragment displayed on the screen
        hideFragments(transaction)
        when (index) {
            0 -> {
                contact_image!!.setImageResource(R.mipmap.ic_launcher)
                contact_text!!.setTextColor(Color.WHITE)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    transaction.add(R.id.content, mHomeFragment)
                } else {
                    transaction.show(mHomeFragment)
                }
            }
            1 -> {
                more_image!!.setImageResource(R.mipmap.ic_launcher)
                more_text!!.setTextColor(Color.WHITE)
                if (mContactFragment == null) {
                    mContactFragment = ContactFragment()
                    transaction.add(R.id.content, mContactFragment)
                } else {
                    transaction.show(mContactFragment)
                }
            }
            2 -> {
                user_image!!.setImageResource(R.mipmap.ic_launcher)
                user_text!!.setTextColor(Color.WHITE)
                if (mMomentFragment == null) {
                    mMomentFragment = MomentFragment()
                    transaction.add(R.id.content, mMomentFragment)
                } else {
                    transaction.show(mMomentFragment)
                }
            }
            3 -> {
                extra_image!!.setImageResource(R.mipmap.ic_launcher)
                extra_text!!.setTextColor(Color.WHITE)
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
        contact_image!!.setImageResource(R.mipmap.ic_launcher)
        contact_text!!.setTextColor(Color.parseColor("#82858b"))
        more_image!!.setImageResource(R.mipmap.ic_launcher)
        more_text!!.setTextColor(Color.parseColor("#82858b"))
        user_image!!.setImageResource(R.mipmap.ic_launcher)
        user_text!!.setTextColor(Color.parseColor("#82858b"))
        extra_image!!.setImageResource(R.mipmap.ic_launcher)
        extra_text!!.setTextColor(Color.parseColor("#82858b"))
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
