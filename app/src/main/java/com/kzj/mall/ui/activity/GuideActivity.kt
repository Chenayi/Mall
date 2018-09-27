package com.kzj.mall.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.blankj.utilcode.util.SPUtils
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGuideBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.fragment.GuideFragment1
import com.kzj.mall.ui.fragment.GuideFragment2
import com.kzj.mall.ui.fragment.GuideFragment3

class GuideActivity : BaseActivity<IPresenter, ActivityGuideBinding>() {
    private var fragments: MutableList<Fragment>? = null
    private var commomViewPagerAdapter: CommomViewPagerAdapter? = null

    private var prePosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_guide

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
    }

    override fun initData() {
        SPUtils.getInstance().put(C.IS_GUIDED, true)

        fragments = ArrayList<Fragment>()
        fragments?.add(GuideFragment1.newInstance())
        fragments?.add(GuideFragment2.newInstance())
        fragments?.add(GuideFragment3.newInstance())

        mBinding?.indicator?.setNoSelRes(R.drawable.indicator_gray)
        mBinding?.indicator?.setIndicatorsSize(fragments?.size!!)
        commomViewPagerAdapter = CommomViewPagerAdapter(supportFragmentManager, fragments!!)
        mBinding?.vpGuide?.adapter = commomViewPagerAdapter
        mBinding?.vpGuide?.offscreenPageLimit = 3
        mBinding?.vpGuide?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBinding?.indicator?.setSelectIndex(position)
                when (position) {
                    0 -> {
                        mBinding?.ivGuideBg?.setImageResource(R.mipmap.guide_bg1)
                    }
                    1 -> {
                        mBinding?.ivGuideBg?.setImageResource(R.mipmap.guide_bg2)
                    }
                    2 -> {
                        mBinding?.ivGuideBg?.setImageResource(R.mipmap.guide_bg3)
                    }
                }
                prePosition = position
            }
        })
    }
}