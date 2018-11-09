package com.kzj.mall.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeBinding
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.adapter.HomeNavigatorTitleView
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.home.HomeTabEntity
import com.kzj.mall.ui.activity.MessageActivity
import com.kzj.mall.ui.activity.SearchActivity
import com.kzj.mall.ui.dialog.HomeTabClassifyPop
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 *  首页模块
 */
class HomeFragment : BaseFragment<IPresenter, FragmentHomeBinding>(), View.OnClickListener {
    private var mCommomViewPagerAdapter: CommomViewPagerAdapter? = null
    private val mTitles: Array<String> = arrayOf("首页", "男科")
    private var mFragments: MutableList<Fragment>? = null

    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindowsInt(false, ContextCompat.getColor(context!!, R.color.tran))
                ?.statusBarDarkFont(false)
                ?.init()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        val statusBarHeight = BarUtils.getStatusBarHeight()
        mBinding?.llTopSearch?.setPadding(SizeUtils.dp2px(12f), SizeUtils.dp2px(12f) + statusBarHeight,
                SizeUtils.dp2px(12f), 0)
        mBinding?.rlSearch?.alpha = 0.9f
        mFragments = ArrayList()
        mBinding?.vpHome?.offscreenPageLimit = mTitles?.size - 1
        mFragments?.let {
            val homeChildFragment = HomeChildFragment.newInstance()
            val andrologyFragment = AndrologyFragment.newInstance()
            it?.add(homeChildFragment)
            it?.add(andrologyFragment)
            mCommomViewPagerAdapter = CommomViewPagerAdapter(childFragmentManager, it)
            mBinding?.vpHome?.adapter = mCommomViewPagerAdapter
            mBinding?.vpHome?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    val baseHomeChildListFragment = mFragments?.get(position) as BaseHomeChildListFragment
                    val barPrimaryColor = baseHomeChildListFragment.isBarPrimaryColor()
                    if (barPrimaryColor) {
                        setTopBackGroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                    } else {
                        setTopBackGroundColor(baseHomeChildListFragment.bannerColorRes())
                    }
                }
            })
        }


        var commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = HomeNavigatorTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.WHITE
                colorTransitionPagerTitleView.selectedColor = Color.WHITE
                colorTransitionPagerTitleView.textSize = 15f
                colorTransitionPagerTitleView.setPadding(SizeUtils.dp2px(20f), 0, SizeUtils.dp2px(20f), 0)
                colorTransitionPagerTitleView.setText(mTitles[index])
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        mBinding?.vpHome?.setCurrentItem(index, false)
                    }

                })
                return colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return mTitles?.size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.setColors(Color.WHITE)
                indicator.roundRadius = 9999f
                indicator.lineWidth = SizeUtils.dp2px(17f).toFloat()
                return indicator
            }
        }

        mBinding?.magicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding?.magicIndicator, mBinding?.vpHome);



        mBinding?.ivClassify?.setOnClickListener(this)
        mBinding?.rlSearch?.setOnClickListener(this)
        mBinding?.llScan?.setOnClickListener(this)
        mBinding?.llMsg?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_classify -> {
                showHomeTabClassifyPop()
            }
            R.id.rl_search -> {
                val intent = Intent(context, SearchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.ll_scan -> {
                ToastUtils.showShort("敬请期待")
            }
            R.id.ll_msg -> {
                jumpActivity(MessageActivity().javaClass)
            }
        }
    }

    fun showHomeTabClassifyPop() {
        val homeTabClassifyPop = HomeTabClassifyPop(activity!!)
        homeTabClassifyPop?.offsetY = SizeUtils.dp2px(8f)
        homeTabClassifyPop?.setOnTabChooseLinstener(object : HomeTabClassifyPop.OnTabChooseLinstener {
            override fun onTabChoose(p: Int?, homeTabEntity: HomeTabEntity?) {
                mBinding?.vpHome?.setCurrentItem(p!!, false)
            }
        })
        homeTabClassifyPop?.showPopupWindow(mBinding?.llTopSearch)
    }

    fun setTopBackGroundColor(colorRes: Int?) {
        colorRes?.let {
            mBinding?.llTopSearch?.setBackgroundColor(it)
            mBinding?.llTab?.setBackgroundColor(it)
        }
    }

    fun pauseBanner() {
        val currentItem = mBinding?.vpHome?.currentItem!!
        val fragment = mFragments?.get(currentItem)
        (fragment as BaseHomeChildListFragment).pauseBanner()
    }
}