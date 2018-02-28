package me.zhaoweihao.shopping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
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

    private var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        bindListener()

        setTabSelection(0)
    }

    private fun bindListener() {
        home_layout!!.setOnClickListener { setTabSelection(0) }
        contact_layout!!.setOnClickListener { setTabSelection(1) }
        moment_layout!!.setOnClickListener { setTabSelection(2) }
        user_layout!!.setOnClickListener { setTabSelection(3) }
    }

    private fun setTabSelection(index: Int) {

        clearSelection()

        fragmentManager = supportFragmentManager

        val transaction = fragmentManager!!.beginTransaction()

        hideFragments(transaction)
        when (index) {
            0 -> {
                home_image!!.setImageResource(R.drawable.home)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance()
                    transaction.add(R.id.content, mHomeFragment)
                } else {
                    transaction.show(mHomeFragment)
                }
            }
            1 -> {
                contact_image!!.setImageResource(R.drawable.contact)
                if (mContactFragment == null) {
                    mContactFragment = ContactFragment.newInstance()
                    transaction.add(R.id.content, mContactFragment)
                } else {
                    transaction.show(mContactFragment)
                }
            }
            2 -> {
                moment_image!!.setImageResource(R.drawable.moment)
                if (mMomentFragment == null) {
                    mMomentFragment = MomentFragment.newInstance()
                    transaction.add(R.id.content, mMomentFragment)
                } else {
                    transaction.show(mMomentFragment)
                }
            }
            3 -> {
                user_image!!.setImageResource(R.mipmap.ic_launcher)
                if (mUserFragment == null) {
                    mUserFragment = UserFragment.newInstance()
                    transaction.add(R.id.content, mUserFragment)
                } else {
                    transaction.show(mUserFragment)
                }
            }
        }
        transaction.commit()
    }

    private fun clearSelection() {
        home_image!!.setImageResource(R.drawable.home)
        contact_image!!.setImageResource(R.drawable.contact)
        moment_image!!.setImageResource(R.drawable.moment)
        user_image!!.setImageResource(R.mipmap.ic_launcher)
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
