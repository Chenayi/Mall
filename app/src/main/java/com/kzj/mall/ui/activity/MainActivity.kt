package com.kzj.mall.ui.activity

import android.support.v4.app.Fragment
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMainBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.CartFragment
import com.kzj.mall.ui.fragment.ClassifyFragment
import com.kzj.mall.ui.fragment.home.HomeFragment
import com.kzj.mall.ui.fragment.MineFragment
import com.kzj.mall.widget.HomeBottomTabBar

class MainActivity : BaseActivity<IPresenter, ActivityMainBinding>() {

    private var vpAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        initViewPager()
        initBottomBar()
    }

    private fun initViewPager() {
        fragments = ArrayList()
        fragments?.let {
            it.add(HomeFragment.newInstance())
            it.add(ClassifyFragment.newInstance())
            it.add(CartFragment.newInstance())
            it.add(MineFragment.newInstance())

            vpAdapter = CommomViewPagerAdapter(supportFragmentManager, it)
            mBinding?.vpMain?.adapter = vpAdapter
            mBinding?.vpMain?.offscreenPageLimit = it.size
        }
    }

    private fun initBottomBar() {
        mBinding?.homeTabBar?.setOnTabChooseListener(object : HomeBottomTabBar.OnTabChooseListener {
            override fun onTabChoose(tab: Int) {
                mBinding?.vpMain?.setCurrentItem(tab, false)
            }
        })
    }
}
