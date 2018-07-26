package com.kzj.mall.ui.activity

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityLoginBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.login.LoginCodeFragment
import com.kzj.mall.ui.fragment.login.LoginPasswordFragment
import com.kzj.mall.widget.RootLayout

class LoginActivity : BaseActivity<IPresenter, ActivityLoginBinding>(), View.OnClickListener {
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun initData() {
        fragments = ArrayList()
        fragments?.add(LoginCodeFragment.newInstance())
        fragments?.add(LoginPasswordFragment.newInstance())
        mBinding?.vpLogin?.setNoScroll(true)
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpLogin?.adapter = commomViewPagerAdapter

        mBinding?.tvCode?.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        mBinding?.llCode?.setOnClickListener(this)
        mBinding?.llPassword?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_code -> {
                val currentItem = mBinding?.vpLogin?.currentItem
                if (currentItem != 0) {
                    mBinding?.tvPassword?.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                    mBinding?.tvCode?.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    mBinding?.viewPassword?.visibility = View.INVISIBLE
                    mBinding?.viewCode?.visibility = View.VISIBLE
                    mBinding?.vpLogin?.setCurrentItem(0,false)
                }

            }
            R.id.ll_password -> {
                val currentItem = mBinding?.vpLogin?.currentItem
                if (currentItem != 1) {
                    mBinding?.tvCode?.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                    mBinding?.tvPassword?.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    mBinding?.viewCode?.visibility = View.INVISIBLE
                    mBinding?.viewPassword?.visibility = View.VISIBLE
                    mBinding?.vpLogin?.setCurrentItem(1,false)
                }
            }
        }
    }
}