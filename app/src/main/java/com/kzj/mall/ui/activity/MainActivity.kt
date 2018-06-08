package com.kzj.mall.ui.activity

import android.support.v4.app.Fragment
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMainBinding

class MainActivity : BaseActivity<IPresenter, ActivityMainBinding>() {
    private var vpAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initViewPager()
        initBottomBar()
    }

    private fun initViewPager() {
        fragments = ArrayList()
        fragments?.let {
            vpAdapter = CommomViewPagerAdapter(supportFragmentManager, it)
            mBinding?.vpMain?.adapter = vpAdapter
            mBinding?.vpMain?.offscreenPageLimit = it.size
        }
    }

    private fun initBottomBar() {
        mBinding?.bnve?.enableAnimation(true)
        mBinding?.bnve?.enableItemShiftingMode(false)
        mBinding?.bnve?.enableShiftingMode(false)
        mBinding?.bnve?.setTextSize(10f)
        mBinding?.bnve?.setupWithViewPager(mBinding?.vpMain, false)
    }
}
