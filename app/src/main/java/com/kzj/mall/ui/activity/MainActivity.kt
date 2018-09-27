package com.kzj.mall.ui.activity

import android.os.Handler
import android.support.v4.app.Fragment
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMainBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.event.BackCartEvent
import com.kzj.mall.event.BackClassifyEvent
import com.kzj.mall.event.BackHomeEvent
import com.kzj.mall.event.BackMinetEvent
import com.kzj.mall.ui.fragment.CartFragment
import com.kzj.mall.ui.fragment.ClassifyFragment
import com.kzj.mall.ui.fragment.home.HomeFragment
import com.kzj.mall.ui.fragment.MineFragment
import com.kzj.mall.widget.HomeBottomTabBar
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity<IPresenter, ActivityMainBinding>() {

    private var vpAdapter: CommomViewPagerAdapter? = null
    private var fragments: MutableList<Fragment>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                ?.init()
    }

    override fun initData() {
        mBinding?.vpMain?.setNoScroll(true)
        initViewPager()
        initBottomBar()
    }

    override fun enableEventBusCloseActivity(): Boolean {
        return false
    }

    private fun initViewPager() {
        fragments = ArrayList<Fragment>()
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

                if (tab == 0) {
                    (fragments?.get(0) as HomeFragment)?.changeBackgroundColor()
                }
            }
        })
    }

    @Subscribe
    fun backHomeEvent(backHomeEvent: BackHomeEvent) {
        if (mBinding?.vpMain?.currentItem != HomeBottomTabBar.TAB_HOME) {
            Handler().postDelayed({
                mBinding?.homeTabBar?.switchHome()
            },300)
        }
    }

    @Subscribe
    fun backCartEvent(backCartEvent: BackCartEvent) {
        if (mBinding?.vpMain?.currentItem != HomeBottomTabBar.TAB_CART) {
            Handler().postDelayed({
                mBinding?.homeTabBar?.switchCart()
            }, 300)
        }
    }

    @Subscribe
    fun backClassifyEvent(backClassifyEvent: BackClassifyEvent) {
        if (mBinding?.vpMain?.currentItem != HomeBottomTabBar.TAB_CLASSIFT) {
            Handler().postDelayed({
                mBinding?.homeTabBar?.switchClassify()
            }, 300)
        }
    }

    @Subscribe
    fun backMinetEvent(backMinetEvent: BackMinetEvent) {
        if (mBinding?.vpMain?.currentItem != HomeBottomTabBar.TAB_MINE) {
            Handler().postDelayed({
                mBinding?.homeTabBar?.switchMine()
            },300)
        }
    }
}
